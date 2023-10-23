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
public class OrderDetailsDto {
    @JsonProperty("order_id")
    String orderId;
    @JsonProperty("user_login")
    String userLogin;
    @JsonProperty("email")
    String emailAddress;
    @JsonProperty("delivery_country")
    String deliveryCountry;
    @JsonProperty("user_comments")
    String userComment;
    @JsonProperty("admin_comments")
    String adminComments;
    @JsonProperty("extra_field_1")
    String boxField;
    @JsonProperty("custom_extra_fields")
    Map<String, String> customFields;
    List<OrderProductDto> products;
}