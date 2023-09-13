package mateuszwed.orderManager.mapper;

import mateuszwed.orderManager.dto.ProductDto;
import mateuszwed.orderManager.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDto productDto);
}
