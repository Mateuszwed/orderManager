package mateuszwed.orderManager.service;

import mateuszwed.orderManager.dto.*;
import mateuszwed.orderManager.exception.ShippingMethodNoExistsException;
import mateuszwed.orderManager.mapper.ProductMapper;
import mateuszwed.orderManager.model.*;
import mateuszwed.orderManager.repository.ShippingMethodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductMapper productMapper;
    @Mock
    ShippingMethodRepository shippingMethodRepository;
    @InjectMocks
    ProductService productService;

    @Test
    void testAddProductSuccessfully() {
        //given
        var productDto = createSimpleProductDto();
        var product = createSimpleProduct();
        var shippingMethod = createSimpleShippingMethod();
        shippingMethod.setProducts(createSimpleProductList());
        when(shippingMethodRepository.findById(any())).thenReturn(Optional.of(shippingMethod));
        when(productMapper.toEntity(productDto)).thenReturn(product);

        //when
        productService.addProduct(productDto, 1);

        //then
        verify(shippingMethodRepository).save(shippingMethod);
        assertTrue(shippingMethod.getProducts().contains(product));
    }

    @Test
    void whenShippingMethodNotExistShouldBeThrowException() {
        //given
        var productDto = createSimpleProductDto();
        when(shippingMethodRepository.findById(any())).thenReturn(Optional.empty());

        //when,then
        assertThrows(ShippingMethodNoExistsException.class, () -> {
            productService.addProduct(productDto, 3);
        });
    }

    private List<Product> createSimpleProductList() {
        var productList = new ArrayList<Product>();
        productList.add(createSimpleProduct());
        productList.add(createSimpleProduct2());
        return productList;
    }

    private Product createSimpleProduct() {
        return Product.builder()
                .id(1)
                .name("test product")
                .productPackaging(createSimpleProductPackaging())
                .build();
    }

    private Product createSimpleProduct2() {
        return Product.builder()
                .id(2)
                .name("test product2")
                .productPackaging(createSimpleProductPackaging())
                .build();
    }

    private ProductPackaging createSimpleProductPackaging() {
        return ProductPackaging.builder()
                .bags(createSimpleBag())
                .boxes(createSimpleBox())
                .brands(createSimpleBrand())
                .build();
    }

    private List<Box> createSimpleBox() {
        var box1 = Box.builder()
                .boxType("small box")
                .quantity(2)
                .build();
        var box2 = Box.builder()
                .boxType("medium box")
                .quantity(1)
                .build();
        var box3 = Box.builder()
                .boxType("big box")
                .quantity(3)
                .build();
        var boxes = new ArrayList<Box>();
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        return boxes;
    }

    private List<Bag> createSimpleBag() {
        var bag1 = Bag.builder()
                .bagType("small bag")
                .quantity(2)
                .build();
        var bag2 = Bag.builder()
                .bagType("medium bag")
                .quantity(1)
                .build();
        var bag3 = Bag.builder()
                .bagType("big bag")
                .quantity(3)
                .build();
        var bags = new ArrayList<Bag>();
        bags.add(bag1);
        bags.add(bag2);
        bags.add(bag3);
        return bags;
    }

    private List<Brand> createSimpleBrand() {
        var brand1 = Brand.builder()
                .quantity(2)
                .build();
        var brand2 = Brand.builder()
                .quantity(1)
                .build();
        var brand3 = Brand.builder()
                .quantity(3)
                .build();
        var brands = new ArrayList<Brand>();
        brands.add(brand1);
        brands.add(brand2);
        brands.add(brand3);
        return brands;
    }

    private ProductDto createSimpleProductDto() {
        return ProductDto.builder()
                .name("test product")
                .productPackaging(createSimpleProductPackagingDto())
                .build();
    }

    private ProductPackagingDto createSimpleProductPackagingDto() {
        ProductPackagingDto productPackagingDto = ProductPackagingDto.builder()
                .bags(createSimpleBagDto())
                .boxes(createSimpleBoxDto())
                .brands(createSimpleBrandDto())
                .build();
        return productPackagingDto;
    }

    private List<BoxDto> createSimpleBoxDto() {
        var box1 = BoxDto.builder()
                .boxType("small box")
                .quantity(2)
                .build();
        var box2 = BoxDto.builder()
                .boxType("medium box")
                .quantity(1)
                .build();
        var box3 = BoxDto.builder()
                .boxType("big box")
                .quantity(3)
                .build();
        var boxes = new ArrayList<BoxDto>();
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        return boxes;
    }

    private List<BagDto> createSimpleBagDto() {
        var bag1 = BagDto.builder()
                .bagType("small bag")
                .quantity(2)
                .build();
        var bag2 = BagDto.builder()
                .bagType("medium bag")
                .quantity(1)
                .build();
        var bag3 = BagDto.builder()
                .bagType("big bag")
                .quantity(3)
                .build();
        var bags = new ArrayList<BagDto>();
        bags.add(bag1);
        bags.add(bag2);
        bags.add(bag3);
        return bags;
    }

    private List<BrandDto> createSimpleBrandDto() {
        var brand1 = BrandDto.builder()
                .quantity(2)
                .build();
        var brand2 = BrandDto.builder()
                .quantity(1)
                .build();
        var brand3 = BrandDto.builder()
                .quantity(3)
                .build();
        var brands = new ArrayList<BrandDto>();
        brands.add(brand1);
        brands.add(brand2);
        brands.add(brand3);
        return brands;
    }

    private ShippingMethod createSimpleShippingMethod() {
        return ShippingMethod.builder()
                .id(1)
                .name("GLS")
                .build();
    }
}