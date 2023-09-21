package mateuszwed.orderManager.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mateuszwed.orderManager.client.BaselinkerClient;
import mateuszwed.orderManager.dto.FieldDto;
import mateuszwed.orderManager.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/orders")
public class OrderManagerController {
    BaselinkerClient baselinkerClient;

    @ApiOperation("Get orders from status")
    @GetMapping("/{statusId}")
    public ResponseEntity<OrderDto> getOrders(@RequestHeader("X-BLToken") String token, @PathVariable int statusId){
        return ResponseEntity.ok(baselinkerClient.getOrders(statusId, token));
    }
    @ApiOperation("Set fields in orders")
    @PostMapping("/fields")
    public ResponseEntity<FieldDto> setField(@RequestHeader("X-BLToken") String token, @RequestBody FieldDto field){
        return ResponseEntity.ok(baselinkerClient.setField(field, token));
    }
}