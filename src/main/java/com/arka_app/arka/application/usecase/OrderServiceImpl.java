package com.arka_app.arka.application.usecase;

import com.arka_app.arka.domain.model.Order;
import com.arka_app.arka.domain.model.OrderItem;
import com.arka_app.arka.domain.model.OrderStatus;
import com.arka_app.arka.domain.model.Product;
import com.arka_app.arka.domain.model.StockHistory;
import com.arka_app.arka.domain.repository.OrderRepositoryPort;
import com.arka_app.arka.domain.repository.ProductRepositoryPort;
import com.arka_app.arka.domain.service.OrderServicePort;
import com.arka_app.arka.domain.service.StockHistoryServicePort;
import com.arka_app.arka.shared.exeptio.InsufficientStockException;
import com.arka_app.arka.shared.exeptio.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderServicePort {

    private final OrderRepositoryPort orderRepository;
    private final ProductRepositoryPort productRepository;
    private final StockHistoryServicePort stockHistoryService; // ✅ Integración

    // ------------------------------------------------------------
    // CREAR ORDEN
    // ------------------------------------------------------------
    @Override
    @Transactional
    public Order createOrder(Order order) {

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }

        // 1️ Validar stock de cada producto
        for (OrderItem item : order.getItems()) {
            Product p = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + item.getProductId()));

            if (p.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product " + p.getId());
            }
        }

        // 2️ Descontar stock y registrar movimientos
        for (OrderItem item : order.getItems()) {
            Product p = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + item.getProductId()));

            int previousStock = p.getStock();
            int newStock = previousStock - item.getQuantity();

            p.setStock(newStock);
            productRepository.save(p);

            // ✅ Registrar en StockHistory
            StockHistory history = StockHistory.builder()
                    .productId(p.getId())
                    .previousStock(previousStock)
                    .newStock(newStock)
                    .reason("order_created")
                    .changedBy("order:pending") // se puede actualizar luego con ID real
                    .changedAt(OffsetDateTime.now())
                    .build();
            stockHistoryService.record(history);
        }

        // 3️⃣ Calcular total y persistir orden
        BigDecimal total = order.getItems().stream()
                .map(i -> i.getPrice() != null
                        ? i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity()))
                        : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total);
        order.setCreatedAt(OffsetDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    // ------------------------------------------------------------
    // ACTUALIZAR ORDEN
    // ------------------------------------------------------------
    @Override
    @Transactional
    public Order updateOrder(Long orderId, Order order) {

        Order existing = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

        if (existing.getStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException("Only PENDING orders can be modified");
        }

        // 1️⃣ Devolver stock de ítems anteriores
        for (OrderItem oldItem : existing.getItems()) {
            Product p = productRepository.findById(oldItem.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + oldItem.getProductId()));

            int previousStock = p.getStock();
            int newStock = previousStock + oldItem.getQuantity();

            p.setStock(newStock);
            productRepository.save(p);

            // ✅ Registrar devolución de stock
            StockHistory history = StockHistory.builder()
                    .productId(p.getId())
                    .previousStock(previousStock)
                    .newStock(newStock)
                    .reason("order_update_return")
                    .changedBy("order:" + orderId)
                    .changedAt(OffsetDateTime.now())
                    .build();
            stockHistoryService.record(history);
        }

        // 2️⃣ Validar nuevo stock disponible
        for (OrderItem newItem : order.getItems()) {
            Product p = productRepository.findById(newItem.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + newItem.getProductId()));

            if (p.getStock() < newItem.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product " + p.getId());
            }
        }

        // 3️⃣ Descontar stock de nuevos ítems
        for (OrderItem newItem : order.getItems()) {
            Product p = productRepository.findById(newItem.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + newItem.getProductId()));

            int previousStock = p.getStock();
            int newStock = previousStock - newItem.getQuantity();

            p.setStock(newStock);
            productRepository.save(p);

            // ✅ Registrar descuento en StockHistory
            StockHistory history = StockHistory.builder()
                    .productId(p.getId())
                    .previousStock(previousStock)
                    .newStock(newStock)
                    .reason("order_update_decrease")
                    .changedBy("order:" + orderId)
                    .changedAt(OffsetDateTime.now())
                    .build();
            stockHistoryService.record(history);
        }

        // 4️⃣ Actualizar datos de la orden
        existing.setItems(order.getItems());
        BigDecimal total = order.getItems().stream()
                .map(i -> i.getPrice() != null
                        ? i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity()))
                        : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        existing.setTotal(total);

        return orderRepository.save(existing);
    }

    // ------------------------------------------------------------
    // OBTENER ORDEN POR ID
    // ------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
    }

    // ------------------------------------------------------------
    // LISTAR ÓRDENES DE UN CLIENTE
    // ------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public List<Order> listByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // ------------------------------------------------------------
    // CAMBIAR ESTADO DE UNA ORDEN
    // ------------------------------------------------------------
    @Override
    @Transactional
    public Order changeStatus(Long orderId, String status) {
        Order existing = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(status);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        existing.setStatus(newStatus);
        return orderRepository.save(existing);
    }
}
