package mateuszwed.orderManager.service;

import mateuszwed.orderManager.dto.*;
import mateuszwed.orderManager.mapper.ProductMapper;
import mateuszwed.orderManager.mapper.ShippingMapper;
import mateuszwed.orderManager.model.*;
import mateuszwed.orderManager.repository.ProductRepository;
import mateuszwed.orderManager.repository.ShippingMethodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ShippingMapper shippingMapper;
    @Mock
    ProductMapper productMapper;
    @Mock
    ProductRepository productRepository;
    @Mock
    ShippingMethodRepository shippingMethodRepository;
    @InjectMocks
    ProductService productService;

    @Test
    void testAddProductSuccessfully() {
        //given
        ProductDto productDto = createSimpleProductDto();
        ShippingMethod shippingMethod = new ShippingMethod();
        Product product = createSimpleProduct();
        when(shippingMethodRepository.findById(any())).thenReturn(Optional.of(shippingMethod));
        when(productMapper.toEntity(productDto)).thenReturn(product);

        productService.addProduct(productDto, 1);

        verify(shippingMethodRepository).save(shippingMethod);
        assertTrue(shippingMethod.getProducts().contains(product));
    }

    private Product createSimpleProduct() {
        Product product = Product.builder()
                .id(1)
                .name("test product")
                .productPackaging(createSimpleProductPackaging())
                .build();
        return product;
    }

    private ProductPackaging createSimpleProductPackaging() {
        ProductPackaging productPackaging = ProductPackaging.builder()
                .bags(createSimpleBag())
                .boxes(createSimpleBox())
                .brands(createSimpleBrand())
                .build();
        return productPackaging;
    }

    private List<Box> createSimpleBox() {
        Box box1 = Box.builder()
                .boxType("small box")
                .quantity(2)
                .build();
        Box box2 = Box.builder()
                .boxType("medium box")
                .quantity(1)
                .build();
        Box box3 = Box.builder()
                .boxType("big box")
                .quantity(3)
                .build();
        List<Box> boxes = new ArrayList<>();
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        return boxes;
    }

    private List<Bag> createSimpleBag() {
        Bag bag1 = Bag.builder()
                .bagType("small bag")
                .quantity(2)
                .build();
        Bag bag2 = Bag.builder()
                .bagType("medium bag")
                .quantity(1)
                .build();
        Bag bag3 = Bag.builder()
                .bagType("big bag")
                .quantity(3)
                .build();
        List<Bag> bags = new ArrayList<>();
        bags.add(bag1);
        bags.add(bag2);
        bags.add(bag3);
        return bags;
    }

    private List<Brand> createSimpleBrand() {
        Brand brand1 = Brand.builder()
                .quantity(2)
                .build();
        Brand brand2 = Brand.builder()
                .quantity(1)
                .build();
        Brand brand3 = Brand.builder()
                .quantity(3)
                .build();
        List<Brand> brands = new ArrayList<>();
        brands.add(brand1);
        brands.add(brand2);
        brands.add(brand3);
        return brands;
    }

    private ProductDto createSimpleProductDto() {
        ProductDto productDto = ProductDto.builder()
                .name("test product")
                .productPackaging(createSimpleProductPackagingDto())
                .build();
        return  productDto;
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
        BoxDto box1 = BoxDto.builder()
                .boxType("small box")
                .quantity(2)
                .build();
        BoxDto box2 = BoxDto.builder()
                .boxType("medium box")
                .quantity(1)
                .build();
        BoxDto box3 = BoxDto.builder()
                .boxType("big box")
                .quantity(3)
                .build();
        List<BoxDto> boxes = new ArrayList<>();
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        return boxes;
    }

    private List<BagDto> createSimpleBagDto() {
        BagDto bag1 = BagDto.builder()
                .bagType("small bag")
                .quantity(2)
                .build();
        BagDto bag2 = BagDto.builder()
                .bagType("medium bag")
                .quantity(1)
                .build();
        BagDto bag3 = BagDto.builder()
                .bagType("big bag")
                .quantity(3)
                .build();
        List<BagDto> bags = new ArrayList<>();
        bags.add(bag1);
        bags.add(bag2);
        bags.add(bag3);
        return bags;
    }

    private List<BrandDto> createSimpleBrandDto() {
        BrandDto brand1 = BrandDto.builder()
                .quantity(2)
                .build();
        BrandDto brand2 = BrandDto.builder()
                .quantity(1)
                .build();
        BrandDto brand3 = BrandDto.builder()
                .quantity(3)
                .build();
        List<BrandDto> brands = new ArrayList<>();
        brands.add(brand1);
        brands.add(brand2);
        brands.add(brand3);
        return brands;
    }
}