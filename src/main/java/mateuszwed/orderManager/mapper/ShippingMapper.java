package mateuszwed.orderManager.mapper;

import mateuszwed.orderManager.dto.ShippingMethodDto;
import mateuszwed.orderManager.model.ShippingMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShippingMapper {
    @Mapping(target = "id", ignore = true)
    List<ShippingMethodDto> toDto(List<ShippingMethod> shippingMethods);
}

