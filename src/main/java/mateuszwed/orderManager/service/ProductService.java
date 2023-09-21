package mateuszwed.orderManager.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mateuszwed.orderManager.dto.ProductDto;
import mateuszwed.orderManager.dto.ShippingMethodDto;
import mateuszwed.orderManager.exception.ShippingMethodNoExistsException;
import mateuszwed.orderManager.mapper.ProductMapper;
import mateuszwed.orderManager.mapper.ShippingMapper;
import mateuszwed.orderManager.repository.ShippingMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductService {
    ProductMapper productMapper;
    ShippingMethodRepository shippingMethodRepository;
    ShippingMapper shippingMapper;

    @Transactional
    public ProductDto addProduct(ProductDto productDto, int id){
        var shippingMethod = shippingMethodRepository.findById(id).orElseThrow(() -> new ShippingMethodNoExistsException("Shipping method with given ID not found"));
        var product = productMapper.toEntity(productDto);
        shippingMethod.getProducts().add(product);
        shippingMethodRepository.save(shippingMethod);
        return productDto;
    }

    @Transactional
    public List<ShippingMethodDto> getAllProducts(){
        return shippingMapper.toDto(shippingMethodRepository.findAll());
    }
}