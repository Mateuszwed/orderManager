package mateuszwed.orderManager.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mateuszwed.orderManager.dto.ProductDto;
import mateuszwed.orderManager.dto.ShippingMethodDto;
import mateuszwed.orderManager.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/products")
public class ProductController {
    ProductService productService;

    @ApiOperation("Display all shipping method with products")
    @GetMapping()
    public ResponseEntity<List<ShippingMethodDto>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @ApiOperation("Add products to shipping method")
    @PostMapping("/{id}")
    public ResponseEntity<ProductDto> addProduct(@PathVariable int id, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.addProduct(productDto,id));
    }
}