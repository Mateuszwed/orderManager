package mateuszwed.orderManager.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mateuszwed.orderManager.client.BaselinkerClient;
import mateuszwed.orderManager.dto.FieldDto;
import mateuszwed.orderManager.dto.OrderDto;
import mateuszwed.orderManager.dto.ShippingMethodDto;
import mateuszwed.orderManager.mapper.ShippingMapper;
import mateuszwed.orderManager.repository.ShippingMethodRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/orders")
public class OrderManagerController {
    BaselinkerClient baselinkerClient;
    ShippingMethodRepository shippingMethodRepository;
    ShippingMapper shippingMapper;

    @ApiOperation("Get orders from status")
    @GetMapping("/{statusId}")
    public ResponseEntity<OrderDto> getOrders(@RequestHeader("X-BLToken") String token, @PathVariable int statusId){
        return baselinkerClient.getOrders(statusId, token);
    }

    @ApiOperation("Set fields in orders")
    @PostMapping("/fields")
    public ResponseEntity<FieldDto> setField(@RequestHeader("X-BLToken") String token, @RequestBody FieldDto field){
        return baselinkerClient.setField(field, token);
    }


    @GetMapping("/products")
    public ResponseEntity<List<ShippingMethodDto>> getAllProducts() {
        List<ShippingMethodDto> products = shippingMapper.toDto(shippingMethodRepository.findAll());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}