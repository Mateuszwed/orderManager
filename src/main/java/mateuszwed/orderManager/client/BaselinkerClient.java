package mateuszwed.orderManager.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mateuszwed.orderManager.dto.FieldDto;
import mateuszwed.orderManager.dto.OrderDto;
import mateuszwed.orderManager.exception.HttpClientException;
import mateuszwed.orderManager.exception.HttpServerException;
import mateuszwed.orderManager.exception.JsonProcessingFailureException;
import mateuszwed.orderManager.exception.RestResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BaselinkerClient {
    final RestTemplate restTemplate;
    final ObjectMapper objectMapper;
    @Value("${baselinker.api.url}")
    String baselinkerUrl;

    public ResponseEntity<OrderDto> getOrders(int statusId, String baselinkerToken) {
        ResponseEntity<OrderDto> response;
        Map<String, Object> methodParams = new HashMap<>();
        methodParams.put("status_id", statusId);
        methodParams.put("include_custom_extra_fields", true);
        String jsonParams = "";
        try {
            jsonParams = convertMapToString(methodParams);
        } catch (JsonProcessingFailureException e) {
            e.printStackTrace();
        }
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("method", "getOrders");
        body.add("parameters", jsonParams);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, getHttpHeaders(baselinkerToken));
        try {
            response = restTemplate.exchange(baselinkerUrl, HttpMethod.POST, requestEntity, OrderDto.class);
        } catch (HttpServerErrorException s) {
            throw new HttpServerException(s.getStatusCode(), "Problem with call to Baselinker API");
        } catch (HttpClientErrorException c){
            throw new HttpClientException(c.getStatusCode(), "Wrong request to Baselinker API");
        } catch (RestClientException r) {
            throw new RestResponseException("Server returned an HTML error page");
        }
        return ResponseEntity.ok(response.getBody());
    }

    public ResponseEntity<FieldDto> setField(FieldDto fieldDto, String baselinkerToken){
        ResponseEntity<FieldDto> response;
        Map<String, Object> methodParams = new HashMap<>();
        methodParams.put("order_id", fieldDto.getOrderId());
        methodParams.put("admin_comments", fieldDto.getAdminComment());
        methodParams.put("extra_field_1", fieldDto.getFirstExtraField());
        methodParams.put("extra_field_2", fieldDto.getSecondExtraField());
        methodParams.put("custom_extra_fields", fieldDto.getCustomExtraFields());
        String jsonParams = "";
        try {
            jsonParams = convertMapToString(methodParams);
        } catch (JsonProcessingFailureException e) {
            e.printStackTrace();
        }
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("method", "setOrderFields");
        body.add("parameters", jsonParams);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, getHttpHeaders(baselinkerToken));
        try {
            response = restTemplate.exchange(baselinkerUrl, HttpMethod.POST, requestEntity, FieldDto.class);
        } catch (HttpServerErrorException s) {
            throw new HttpServerException(s.getStatusCode(), "Problem with call to Baselinker API");
        } catch (HttpClientErrorException c){
            throw new HttpClientException(c.getStatusCode(), "Wrong request to Baselinker API");
        } catch (RestClientException r) {
            throw new RestResponseException("Server returned an HTML error page");
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
    
    private HttpHeaders getHttpHeaders(String baselinkerToken) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-BLToken", baselinkerToken);
        return headers;
    }

}
