package mateuszwed.orderManager.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderManagerController {

    @GetMapping("/orders/{statusId}")
    public ResponseEntity<String> getOrders(@PathVariable int statusId){
        return ResponseEntity.ok("");
    }

    @PostMapping("/extraFields")
    public ResponseEntity<String> setExtraField(@RequestBody String body){
        return ResponseEntity.ok("");
    }
}
