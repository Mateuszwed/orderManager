package mateuszwed.orderManager.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductPackagingDto {
    List<BoxDto> boxes;
    List<BagDto> bags;
    List<BrandDto> brands;
}
