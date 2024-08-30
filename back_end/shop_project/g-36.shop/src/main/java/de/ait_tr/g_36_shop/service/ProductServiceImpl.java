package de.ait_tr.g_36_shop.service;

import de.ait_tr.g_36_shop.domain.dto.ProductDto;
import de.ait_tr.g_36_shop.domain.entity.Product;
import de.ait_tr.g_36_shop.exception_handling.exceptions.NoActiveProductsException;
import de.ait_tr.g_36_shop.exception_handling.exceptions.ProductNotFoundException;
import de.ait_tr.g_36_shop.exception_handling.exceptions.SavingProductException;
import de.ait_tr.g_36_shop.repository.ProductRepository;
import de.ait_tr.g_36_shop.service.interfaces.ProductService;
import de.ait_tr.g_36_shop.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;
    private ProductMappingService mappingService;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public ProductDto save(ProductDto dto) {
        Product entity = mappingService.mapDtoToEntity(dto);

        // exception when saving product
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw new SavingProductException(String.format("Error while saving product: %s", entity), e);
        }

        return mappingService.mapEntityToDto(entity);
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
        List<ProductDto> products = repository.findAll()
                .stream()
                .filter(Product::isActive)
                .map(mappingService::mapEntityToDto)
                .toList();

        // no active products in the database
        if (products.isEmpty()) {
            throw new NoActiveProductsException("There are no active products in the database");
        }

        return products;
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product != null && product.isActive()) {
            return mappingService.mapEntityToDto(product);
        }
        //return null;

        // product not found exception
        throw new ProductNotFoundException(id);
    }

    @Override
    public ProductDto update(ProductDto product) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
    // product not found exception - ???
    @Override
    public void deleteByTitle(String title) {

    }
    // product not found exception - ???
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
