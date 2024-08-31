package de.ait_tr.g_36_shop.service;

import de.ait_tr.g_36_shop.domain.dto.ProductDto;
import de.ait_tr.g_36_shop.domain.entity.Product;
import de.ait_tr.g_36_shop.exception_handling.exceptions.ProductNotFoundException;
import de.ait_tr.g_36_shop.repository.ProductRepository;
import de.ait_tr.g_36_shop.service.interfaces.ProductService;
import de.ait_tr.g_36_shop.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        repository.save(entity);
        return mappingService.mapEntityToDto(entity);
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
        return repository.findAll()
                .stream()
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = repository.findById(id).orElse(null);
        if( product != null && product.isActive()){
            return mappingService.mapEntityToDto(product);
        }
        return null;
    }

    @Override
    public ProductDto update(ProductDto product) {
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Product product = repository.findById(id).orElse(null);

        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        product.setActive(false);
    }

    @Override
    @Transactional
    public void deleteByTitle(String title) {
        Product product = repository.findByTitle(title).orElse(null);

        if (product == null) {
            throw new ProductNotFoundException(title);
        }

        product.setActive(false);
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
