package de.ait_tr.g_36_shop.service;

import de.ait_tr.g_36_shop.domain.dto.ProductDto;
import de.ait_tr.g_36_shop.domain.entity.Product;
import de.ait_tr.g_36_shop.repository.ProductRepository;
import de.ait_tr.g_36_shop.service.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductDto save(ProductDto product) {
        return repository.save(product);
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
        return repository.findAll()
                .stream()
                .filter(Product::isActive)
                .toList();
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = repository.findById(id).orElse(null);
        if(product != null && product.isActive()){
            return product;
        }
        return null;
    }

    @Override
    public ProductDto update(ProductDto product) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByTitle(String title) {

    }

    @Override
    public void restoreById(Long id) {

    }

    @Override
    public long getAllActiveProductsQuantity() {
        return 0;
    }

    @Override
    public BigDecimal getAllActiveProductsTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAllActiveProductsAveragePrice() {
        return null;
    }
}
