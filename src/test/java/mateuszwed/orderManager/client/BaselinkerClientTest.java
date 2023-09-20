package mateuszwed.orderManager.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import mateuszwed.orderManager.dto.OrderDetailsDto;
import mateuszwed.orderManager.dto.OrderDto;
import mateuszwed.orderManager.dto.OrderProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaselinkerClientTest {
    @Mock()
    RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    BaselinkerClient baselinkerClient;

    @Test
    void methodGetResponseFromBaselinkerShouldBeReturnListOfOrdersAndStatusCode200() {
        //given
        OrderDto orderDto = createSimpleOrderDto();
        var responseEntity = new ResponseEntity<>(orderDto, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(OrderDto.class)))
                .thenReturn(responseEntity);
        ReflectionTestUtils.setField(baselinkerClient, "baselinkerUrl", "https://mock-url.com");

        //when
        OrderDto response = baselinkerClient.getOrders(1, "testToken");

        //then
        assertNotNull(response);
        assertEquals(orderDto, response);
        assertThat(response).isEqualTo(orderDto);
        assertThat(response.getOrders().get(0).getDelivery_country()).isEqualTo("Poland");
        assertThat(response.getOrders().get(1).getDelivery_country()).isEqualTo("German");
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(OrderDto.class));
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