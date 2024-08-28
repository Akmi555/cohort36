package de.ait_tr.lesson_15_validation_error_handling.product.service;

import de.ait_tr.lesson_15_validation_error_handling.exception_handling.exceptions.NoActiveProductsException;
import de.ait_tr.lesson_15_validation_error_handling.exception_handling.exceptions.ProductNotFoundException;
import de.ait_tr.lesson_15_validation_error_handling.exception_handling.exceptions.SavingProductException;
import de.ait_tr.lesson_15_validation_error_handling.product.Product;
import de.ait_tr.lesson_15_validation_error_handling.product.ProductDto;
import de.ait_tr.lesson_15_validation_error_handling.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMappingService mappingService;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public ProductDto save(ProductDto dto) {
        Product entity = mappingService.mapDtoToEntity(dto);

        // опасная операция, оборачиваем в try...catch
        try {
            repository.save(entity);
        } catch (Exception e) {
            // максимально подробно и ясно что и с чем произошло
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

        // когда все продукты в базе имеют active = false
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

        // было return null;
        // вместо возврата null выбрасываем исключение
        throw new ProductNotFoundException(id);
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
    public long getActiveProductsQuantity() {
        return 0;
    }

    @Override
    public BigDecimal getActiveProductsTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getActiveProductsAveragePrice() {
        return null;
    }
}