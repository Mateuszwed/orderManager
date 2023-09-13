package mateuszwed.orderManager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldDto {
    int orderId;
    String adminComment;
    String firstExtraField;
    String secondExtraField;
    Map<String, String> customExtraFields;
}
