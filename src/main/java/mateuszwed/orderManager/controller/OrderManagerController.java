package mateuszwed.orderManager.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mateuszwed.orderManager.client.BaselinkerClient;
import mateuszwed.orderManager.dto.CustomExtraFieldDto;
import mateuszwed.orderManager.dto.FieldDto;
import mateuszwed.orderManager.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderManagerController {
    BaselinkerClient baselinkerClient;

    @ApiOperation("Get orders from status")
    @GetMapping("/orders/{statusId}")
    public ResponseEntity<OrderDto> getOrders(@PathVariable int statusId){
        return baselinkerClient.getOrders(statusId);
    }

    @ApiOperation("Set fields in orders")
    @PostMapping("/fields")
    public ResponseEntity<FieldDto> setField(@RequestBody FieldDto field){
        return ResponseEntity.ok(new FieldDto());
    }

    @ApiOperation("Set custom extra fields in orders")
    @PostMapping("/customExtraFields")
    public ResponseEntity<CustomExtraFieldDto> setCustomExtraField(@RequestBody CustomExtraFieldDto customExtraField){
        return baselinkerClient.setCustomExtraField(customExtraField);
    }
}