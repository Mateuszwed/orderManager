package mateuszwed.orderManager.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import mateuszwed.orderManager.dto.FieldDto;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
        var orderDto = createSimpleOrderDto();
        var responseEntity = new ResponseEntity<>(orderDto, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(OrderDto.class)))
                .thenReturn(responseEntity);
        ReflectionTestUtils.setField(baselinkerClient, "baselinkerUrl", "http://url.com");

        //when
        var response = baselinkerClient.getOrders(1, "testToken");

        //then
        assertNotNull(response);
        assertEquals(orderDto, response);
        assertThat(response).isEqualTo(orderDto);
        assertThat(response.getOrders().get(0).getDelivery_country()).isEqualTo("Poland");
        assertThat(response.getOrders().get(1).getDelivery_country()).isEqualTo("German");
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(OrderDto.class));
    }

    @Test
    void wrongUrlToClientInGetOrdersShouldBeReturnThrowHttpExceptionWithStatusCode404() {
        // given
        var httpClientErrorException = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(OrderDto.class)))
                .thenThrow(httpClientErrorException);
        ReflectionTestUtils.setField(baselinkerClient, "baselinkerUrl", "http://url.com");

        // when, then
        assertThrows(HttpClientErrorException.class, () -> baselinkerClient.getOrders(1,"token"));
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(OrderDto.class));
    }

    @Test
    void wrongDataFromServerInGetOrdersShouldBeReturnThrowHttpExceptionWithStatusCode500() {
        // given
        var httpServerErrorException = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(OrderDto.class)))
                .thenThrow(httpServerErrorException);
        ReflectionTestUtils.setField(baselinkerClient, "baselinkerUrl", "http://url.com");

        // when, then
        assertThrows(HttpServerErrorException.class, () -> baselinkerClient.getOrders(1,"token"));
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(OrderDto.class));
    }

    @Test
    void methodShouldBePostRequestToBaselinkerAndShouldBeReturnStatusCode200() {
        //given
        var fieldDto = createSimpleFieldDto();
        var responseEntity = new ResponseEntity<>(fieldDto, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(FieldDto.class)))
                .thenReturn(responseEntity);
        ReflectionTestUtils.setField(baselinkerClient, "baselinkerUrl", "http://url.com");

        //when
        var response = baselinkerClient.setField(fieldDto, "testToken");

        //then
        assertNotNull(response);
        assertEquals(fieldDto, response);
        assertThat(response).isEqualTo(fieldDto);
        assertThat(response.getAdminComment()).isEqualTo("admin comments");
        assertThat(response.getOrderId()).isEqualTo(1);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(FieldDto.class));
    }

    @Test
    void wrongUrlToClientInSetFieldShouldBeReturnThrowHttpExceptionWithStatusCode404() {
        // given
        var fieldDto = createSimpleFieldDto();
        var httpClientErrorException = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(FieldDto.class)))
                .thenThrow(httpClientErrorException);
        ReflectionTestUtils.setField(baselinkerClient, "baselinkerUrl", "http://url.com");

        // when, then
        assertThrows(HttpClientErrorException.class, () -> baselinkerClient.setField(fieldDto,"token"));
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(FieldDto.class));
    }

    @Test
    void wrongDataFromServerInSetFieldShouldBeReturnThrowHttpExceptionWithStatusCode500() {
        // given
        var fieldDto = new FieldDto();
        var httpServerErrorException = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(FieldDto.class)))
                .thenThrow(httpServerErrorException);
        ReflectionTestUtils.setField(baselinkerClient, "baselinkerUrl", "http://url.com");

        // when, then
        assertThrows(HttpServerErrorException.class, () -> baselinkerClient.setField(fieldDto,"token"));
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(FieldDto.class));
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

    private FieldDto createSimpleFieldDto() {
        return FieldDto.builder()
                .orderId(1)
                .adminComment("admin comments")
                .firstExtraField("(k0 - 0 / k1 - 0 / k2 - 0 / k3 - 0 / k4 - 1)")
                .secondExtraField("size")
                .customExtraFields(createSimpleCustomExtraFieldDto())
                .build();
    }

    private HashMap<String, String> createSimpleCustomExtraFieldDto() {
        return new HashMap<>() {
            {
                put("38352", "COMPANY1 - 1 - COMPANY2 - 0 - COMPANY3 - 0");
                put("38872", "(W1 - 1 - W2 - 0 - W3 - 0)");
            }};
    }
}