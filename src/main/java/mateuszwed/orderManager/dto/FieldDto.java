package mateuszwed.orderManager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldDto {
    @JsonProperty("order_id")
    String orderId;
    @JsonProperty("admin_comments")
    String adminComment;
    @JsonProperty("extra_field_1")
    String firstExtraField;
    @JsonProperty("custom_extra_fields")
    List<CustomExtraFieldDto> customExtraFields;
}
