package mateuszwed.orderManager.mapper;

import mateuszwed.orderManager.dto.ProductDto;
import mateuszwed.orderManager.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDto productDto);
}
