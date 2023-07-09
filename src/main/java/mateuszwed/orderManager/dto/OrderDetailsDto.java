package mateuszwed.orderManager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetailsDto {
    @JsonProperty("order_id")
    int orderId;
    @JsonProperty("email")
    String emailAddress;
    @JsonProperty("delivery_country")
    String delivery_country;
    @JsonProperty("user_comments")
    String userComment;
    @JsonProperty("extra_field_1")
    String boxField;
    @JsonProperty("custom_extra_fields")
    Map<String,String> customFields;
    List<OrderProductDto> products;
}