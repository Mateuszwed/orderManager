package mateuszwed.orderManager.mapper;

import mateuszwed.orderManager.dto.ShippingMethodDto;
import mateuszwed.orderManager.model.ShippingMethod;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShippingMapper {
    List<ShippingMethodDto> toDto(List<ShippingMethod> shippingMethods);
}

