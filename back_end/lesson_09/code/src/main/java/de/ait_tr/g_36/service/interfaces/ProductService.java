package de.ait_tr.g_36.service.interfaces;

import de.ait_tr.g_36.domain.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Product save (Product product);
    List<Product> getAllProducts();
    Product getById(Long id);
    Product update (Product product);
    void deleteById(Long id);
    void deleteByTitle(String title);
    void restoreById(Long id);
    long getAllActiveProductsQuantity();
    BigDecimal getAllActiveProductsTotalPrice();
    BigDecimal getAllActiveProductsAveragePrice();

}
