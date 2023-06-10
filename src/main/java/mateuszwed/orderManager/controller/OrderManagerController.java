package mateuszwed.orderManager.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mateuszwed.orderManager.dto.CustomExtraFieldDto;
import mateuszwed.orderManager.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderManagerController {

    @GetMapping("/orders/{statusId}")
    public ResponseEntity<OrderDto> getOrders(@PathVariable int statusId){
        return ResponseEntity.ok(new OrderDto());
    }

    @PostMapping("/extraFields")
    public ResponseEntity<CustomExtraFieldDto> setExtraField(@RequestBody CustomExtraFieldDto extraField){
        return ResponseEntity.ok(new CustomExtraFieldDto());
    }
}