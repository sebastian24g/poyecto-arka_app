package com.arka_app.arka.application.usecase;

import com.arka_app.arka.domain.model.Product;
import com.arka_app.arka.domain.model.StockHistory;
import com.arka_app.arka.domain.repository.ProductRepositoryPort;
import com.arka_app.arka.domain.service.ProductServicePort;
import com.arka_app.arka.domain.service.StockHistoryServicePort;
import com.arka_app.arka.shared.exeptio.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServicePort {

    private final ProductRepositoryPort productRepositoryPort;
    private final StockHistoryServicePort stockHistoryService;

    @Override
    @Transactional
    public Product createProduct(Product product) {
        // Validar SKU único
        if (product.getSku() != null) {
            var existing = productRepositoryPort.findBySku(product.getSku());
            if (existing.isPresent()) {
                throw new IllegalArgumentException("SKU already exists");
            }
        }

        Product toSave = Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock() == null ? 0 : product.getStock())
                .sku(product.getSku())
                .categoryId(product.getCategoryId())
                .build();

        return productRepositoryPort.save(toSave);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product existing = productRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        existing.setName(product.getName() != null ? product.getName() : existing.getName());
        existing.setDescription(product.getDescription() != null ? product.getDescription() : existing.getDescription());
        existing.setPrice(product.getPrice() != null ? product.getPrice() : existing.getPrice());
        existing.setStock(product.getStock() != null ? product.getStock() : existing.getStock());
        existing.setSku(product.getSku() != null ? product.getSku() : existing.getSku());
        existing.setCategoryId(product.getCategoryId() != null ? product.getCategoryId() : existing.getCategoryId());

        return productRepositoryPort.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(Long id) {
        return productRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> listProducts(int page, int size) {
        return productRepositoryPort.findAll(page, size);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productRepositoryPort.deleteById(id);
    }

    @Override
    @Transactional
    public Product updateStock(Long productId, Integer newStock, String reason, String changedBy) { // ✅ Integer
        Product product = productRepositoryPort.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));

        int previousStock = product.getStock();
        product.setStock(newStock);
        Product saved = productRepositoryPort.save(product);

        // Registrar movimiento en StockHistory
        StockHistory history = StockHistory.builder()
                .productId(productId)
                .previousStock(previousStock)
                .newStock(newStock)
                .reason(reason != null ? reason : "manual_adjustment")
                .changedBy(changedBy != null ? changedBy : "system")
                .build();

        stockHistoryService.record(history);

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findLowStock(int threshold) {
        return productRepositoryPort.findLowStock(threshold);
    }
}
