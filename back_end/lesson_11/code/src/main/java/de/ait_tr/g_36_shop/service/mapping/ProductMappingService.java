package de.ait_tr.g_36_shop.service.mapping;

import de.ait_tr.g_36_shop.domain.dto.ProductDto;
import de.ait_tr.g_36_shop.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Service
@Mapper (componentModel = "spring")
public interface ProductMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Product mapDtoToEntity(ProductDto dto);

    ProductDto mapEntityToDto(Product entity);

//    public Product mapDtoToEntry(ProductDto dto){
//        Product entity = new Product();
//        entity.setTitle(dto.getTitle());
//        entity.setPrice(dto.getPrice());
//        entity.setActive(true);
//        return entity;
//    }
//
//    public ProductDto mapEntryToDto(Product entity){
//        ProductDto dto = new ProductDto();
//        dto.setId(entity.getId());
//        dto.setTitle(entity.getTitle());
//        dto.setPrice(entity.getPrice());
//        return dto;
//    }

}
