package mateuszwed.orderManager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetailsDto {
    @JsonProperty("order_id")
    String orderId;
    @JsonProperty("email")
    String emailAddress;
    @JsonProperty("delivery_country")
    String delivery_country;
    @JsonProperty("user_comments")
    String userComments;
    List<OrderProductDto> products;
}