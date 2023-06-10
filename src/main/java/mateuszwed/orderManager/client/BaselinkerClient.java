package mateuszwed.orderManager.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mateuszwed.orderManager.dto.CustomExtraFieldDto;
import mateuszwed.orderManager.dto.OrderDto;
import mateuszwed.orderManager.exception.HttpClientException;
import mateuszwed.orderManager.exception.HttpServerException;
import mateuszwed.orderManager.exception.JsonProcessingFailureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BaselinkerClient {
    final RestTemplate restTemplate;
    final ObjectMapper objectMapper;
    @Value("${baselinker.api.token}")
    String baselinkerToken;
    @Value("${baselinker.api.url}")
    String baselinkerUrl;

    public ResponseEntity<OrderDto> getOrders(int statusId) {
        ResponseEntity<OrderDto> response;
        Map<String, Object> methodParams = new HashMap<>();
        methodParams.put("status_id", statusId);
        String jsonParams = "";
        try {
            jsonParams = convertMapToString(methodParams);
        } catch (JsonProcessingFailureException e) {
            e.printStackTrace();
        }
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("method", "getOrders");
        body.add("parameters", jsonParams);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, getHttpHeaders());
        try {
            response = restTemplate.exchange(baselinkerUrl, HttpMethod.POST, requestEntity, OrderDto.class);
        } catch (HttpServerErrorException s) {
            throw new HttpServerException(s.getStatusCode(), "Problem with call to Baselinker API");
        } catch (HttpClientErrorException c){
            throw new HttpClientException(c.getStatusCode(), "Wrong request to Baselinker API");
        }
        return ResponseEntity.ok(response.getBody());
    }

    public ResponseEntity<CustomExtraFieldDto> setCustomExtraField(CustomExtraFieldDto customExtraFieldDto){
        ResponseEntity<CustomExtraFieldDto> response;
        Map<String, Object> methodParams = new HashMap<>();
        Map<Integer, String> field = new HashMap<>();
        field.put(customExtraFieldDto.getCustomFieldId(), customExtraFieldDto.getContent());
        methodParams.put("order_id", customExtraFieldDto.getOrderId());
        methodParams.put("custom_extra_fields", field);
        String jsonParams = "";
        try {
            jsonParams = convertMapToString(methodParams);
        } catch (JsonProcessingFailureException e) {
            e.printStackTrace();
        }
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("method", "setOrderFields");
        body.add("parameters", jsonParams);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, getHttpHeaders());
        try {
            response = restTemplate.exchange(baselinkerUrl, HttpMethod.POST, requestEntity, CustomExtraFieldDto.class);
        } catch (HttpServerErrorException s) {
            throw new HttpServerException(s.getStatusCode(), "Problem with call to Baselinker API");
        } catch (HttpClientErrorException c){
            throw new HttpClientException(c.getStatusCode(), "Wrong request to Baselinker API");
        }
        return ResponseEntity.ok(response.getBody());
    }
    
    private String convertMapToString(Map<String,Object> params) throws JsonProcessingFailureException {
        String jsonParams;
        try {
            jsonParams = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new JsonProcessingFailureException("Object Mapper cannot convert value as string");
        }
        return jsonParams;
    }
    
    private HttpHeaders getHttpHeaders() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-BLToken", baselinkerToken);
        return headers;
    }
}
