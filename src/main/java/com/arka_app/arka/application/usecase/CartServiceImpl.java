package com.arka_app.arka.application.usecase;

import com.arka_app.arka.domain.model.*;
import com.arka_app.arka.domain.repository.*;
import com.arka_app.arka.domain.service.CartServicePort;
import com.arka_app.arka.domain.service.OrderServicePort;
import com.arka_app.arka.domain.service.StockHistoryServicePort;
import com.arka_app.arka.shared.exeptio.ResourceNotFoundException;
import com.arka_app.arka.shared.exeptio.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartServicePort {

    private final CartRepositoryPort cartRepository;
    private final CartItemRepositoryPort itemRepository;
    private final CartHistoryRepositoryPort historyRepository;
    private final ProductRepositoryPort productRepository;
    private final OrderServicePort orderService; // integración con órdenes
    private final StockHistoryServicePort stockHistoryService; // registrar movimientos

    // ========================================================
    // 1️⃣ Obtener o crear carrito activo
    // ========================================================
    @Override
    @Transactional
    public Cart getOrCreateCart(Long customerId) {
        return cartRepository.findActiveCartByCustomer(customerId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .customerId(customerId)
                            .createdAt(OffsetDateTime.now())
                            .updatedAt(OffsetDateTime.now())
                            .active(true)
                            .build();

                    Cart saved = cartRepository.save(newCart);

                    historyRepository.save(CartHistory.builder()
                            .cartId(saved.getId())
                            .eventType("CART_CREATED")
                            .eventAt(OffsetDateTime.now())
                            .build());

                    return saved;
                });
    }


    // ========================================================
    // 2️⃣ Agregar un producto al carrito
    // ========================================================
    @Override
    @Transactional
    public Cart addItem(Long customerId, Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // Obtener o crear carrito
        Cart cart = getOrCreateCart(customerId);

        // Buscar producto
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));

        // Verificar si ya está en el carrito
        CartItem item = itemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + quantity);
                    existing.setAddedAt(OffsetDateTime.now());
                    return itemRepository.save(existing);
                })
                .orElseGet(() -> {
                    CartItem newItem = CartItem.builder()
                            .cartId(cart.getId())
                            .productId(productId)
                            .productName(product.getName())
                            .price(product.getPrice())
                            .quantity(quantity)
                            .addedAt(OffsetDateTime.now())
                            .build();
                    return itemRepository.save(newItem);
                });

        // Registrar evento en historial
        historyRepository.save(CartHistory.builder()
                .cartId(cart.getId())
                .productId(productId)
                .quantity(quantity)
                .eventType("ITEM_ADDED")
                .eventAt(OffsetDateTime.now())
                .build());

        // Actualizar fecha del carrito
        cart.setUpdatedAt(OffsetDateTime.now());
        cartRepository.save(cart);

        return cart;
    }

    // ========================================================
    // 3️⃣ Eliminar un producto del carrito
    // ========================================================
    @Override
    @Transactional
    public Cart removeItem(Long customerId, Long productId) {
        Cart cart = cartRepository.findActiveCartByCustomer(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("No active cart for customer"));

        CartItem item = itemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not in cart"));

        itemRepository.deleteById(item.getId());

        historyRepository.save(CartHistory.builder()
                .cartId(cart.getId())
                .productId(productId)
                .quantity(item.getQuantity())
                .eventType("ITEM_REMOVED")
                .eventAt(OffsetDateTime.now())
                .build());

        cart.setUpdatedAt(OffsetDateTime.now());
        cartRepository.save(cart);

        return cart;
    }

    // ========================================================
    // 4️⃣ Vaciar el carrito
    // ========================================================
    @Override
    @Transactional
    public void clearCart(Long customerId) {
        Cart cart = cartRepository.findActiveCartByCustomer(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("No active cart for customer"));

        itemRepository.deleteAllByCartId(cart.getId());

        historyRepository.save(CartHistory.builder()
                .cartId(cart.getId())
                .eventType("CART_CLEARED")
                .eventAt(OffsetDateTime.now())
                .build());

        cart.setUpdatedAt(OffsetDateTime.now());
        cartRepository.save(cart);
    }

    // ========================================================
    // 5️⃣ Marcar carrito como abandonado
    // ========================================================
    @Override
    @Transactional
    public void markCartAsAbandoned(Long customerId) {
        Cart cart = cartRepository.findActiveCartByCustomer(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("No active cart for customer"));

        historyRepository.save(CartHistory.builder()
                .cartId(cart.getId())
                .eventType("CART_ABANDONED")
                .eventAt(OffsetDateTime.now())
                .build());

        cart.setActive(true); // el carrito sigue activo, solo se marca como abandonado
        cart.setUpdatedAt(OffsetDateTime.now());
        cartRepository.save(cart);
    }

    // ========================================================
    // 6️⃣ Checkout completo
    // ========================================================
    @Override
    @Transactional
    public void checkoutCart(Long customerId) {
        Cart cart = cartRepository.findActiveCartByCustomer(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("No active cart for customer"));

        List<CartItem> items = itemRepository.findByCartId(cart.getId());
        if (items.isEmpty()) {
            throw new IllegalStateException("Cart is empty, cannot checkout");
        }

        // 1️⃣ Validar stock disponible
        for (CartItem item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + item.getProductId()));
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Not enough stock for product: " + product.getName());
            }
        }

        // 2️⃣ Crear orden desde el carrito
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setCreatedAt(OffsetDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = items.stream()
                .map(i -> OrderItem.builder()
                        .productId(i.getProductId())
                        .price(i.getPrice())
                        .quantity(i.getQuantity())
                        .build())
                .collect(Collectors.toList());

        order.setItems(orderItems);
        BigDecimal total = orderItems.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);

        // Guardar orden usando OrderService
        Order savedOrder = orderService.createOrder(order);

        // 3️⃣ Registrar movimientos de stock
        for (CartItem item : items) {
            Product product = productRepository.findById(item.getProductId()).get();
            int previousStock = product.getStock();
            int newStock = previousStock - item.getQuantity();

            product.setStock(newStock);
            productRepository.save(product);

            stockHistoryService.record(StockHistory.builder()
                    .productId(product.getId())
                    .previousStock(previousStock)
                    .newStock(newStock)
                    .reason("checkout_order:" + savedOrder.getId())
                    .changedBy("customer:" + customerId)
                    .changedAt(OffsetDateTime.now())
                    .build());
        }

        // 4️⃣ Registrar evento en historial del carrito
        historyRepository.save(CartHistory.builder()
                .cartId(cart.getId())
                .eventType("CHECKOUT")
                .eventAt(OffsetDateTime.now())
                .build());

        // 5️⃣ Marcar carrito como cerrado
        cart.setActive(false);
        cart.setUpdatedAt(OffsetDateTime.now());
        cartRepository.save(cart);

        // 6️⃣ Vaciar carrito
        itemRepository.deleteAllByCartId(cart.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> getItems(Long customerId) {
        // Buscar carrito activo del cliente
        Cart cart = cartRepository.findActiveCartByCustomer(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("No active cart for customer"));

        // Obtener todos los ítems de ese carrito
        return itemRepository.findByCartId(cart.getId());
    }

}
