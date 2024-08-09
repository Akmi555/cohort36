package de.ait_tr.g_36.service;

import de.ait_tr.g_36.domain.entity.Product;
import de.ait_tr.g_36.service.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Product save(Product product) {
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public Product update(Product product) {
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
