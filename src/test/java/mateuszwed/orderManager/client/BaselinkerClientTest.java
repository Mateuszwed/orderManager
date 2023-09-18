package mateuszwed.orderManager.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mateuszwed.orderManager.dto.OrderDetailsDto;
import mateuszwed.orderManager.dto.OrderDto;
import mateuszwed.orderManager.dto.OrderProductDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BaselinkerClientTest {
    @Mock
    RestTemplate restTemplate;
    @Mock
    ObjectMapper objectMapper;
    @InjectMocks
    BaselinkerClient baselinkerClient;

    @Test
    void  methodGetResponseFromBaselinkerShouldBeReturnListOfOrdersAndStatusCode200() throws JsonProcessingException {
        //given
        var orderDto = createSimpleOrderDto();
        var responseEntity = new ResponseEntity<>(orderDto, HttpStatus.OK);
        when(objectMapper.writeValueAsString(anyString())).thenReturn()
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(OrderDto.class)))
                .thenReturn(responseEntity);

        //when
        var result = baselinkerClient.getOrders(1, "test").getBody();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("GLS");
        //assertThat(result.get(0).getMid()).isEqualTo(new BigDecimal("3.5"));
        //verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(OrderDto.class));
    }


    private OrderDto createSimpleOrderDto() {
        return OrderDto.builder()
                .status("GLS")
                .orders(createSimpleOrderDetailDtoList())
                .build();
    }

    private List<OrderDetailsDto> createSimpleOrderDetailDtoList() {
        var orderDetailsDtoList = new ArrayList<OrderDetailsDto>();
        var orderDetailsDto = OrderDetailsDto.builder()
                .orderId(1)
                .boxField("(k - 1 / k - 2)")
                .delivery_country("Poland")
                .userComment("comment")
                .emailAddress("email@email.pl")
                .customFields(createSimpleCustomFields())
                .products(createSimpleOrderProductDtoList())
                .build();
        var orderDetailsDto2 = OrderDetailsDto.builder()
                .orderId(2)
                .boxField("(k - 0 / k - 1)")
                .delivery_country("German")
                .userComment("comment2")
                .emailAddress("email@email2.pl")
                .customFields(createSimpleCustomFields())
                .products(createSimpleOrderProductDtoList())
                .build();
        orderDetailsDtoList.add(orderDetailsDto);
        orderDetailsDtoList.add(orderDetailsDto2);
        return orderDetailsDtoList;
    }

    private Map<String, String> createSimpleCustomFields() {
        var customFields = new HashMap<String, String>();
        customFields.put("Bags", "(W0 - 1 / W2- 2)");
        customFields.put("Brand", "COMPANY1 - 0 / COMPANY2 - 1");
        return customFields;
    }

    private List<OrderProductDto> createSimpleOrderProductDtoList() {
        var productList = new ArrayList<OrderProductDto>();
        var orderProductDto = OrderProductDto.builder()
                .name("Ball pit")
                .quantity(1)
                .sku("an223")
                .build();
        var orderProductDto2 = OrderProductDto.builder()
                .name("Mat")
                .quantity(2)
                .sku("an233")
                .build();
        productList.add(orderProductDto);
        productList.add(orderProductDto2);
        return productList;
    }
}