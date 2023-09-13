package mateuszwed.orderManager.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductPackagingDto {
    List<BoxDto> boxes;
    List<BagDto> bags;
    List<BrandDto> brands;
}
