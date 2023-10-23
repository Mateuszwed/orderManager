package mateuszwed.orderManager.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mateuszwed.orderManager.dto.*;
import mateuszwed.orderManager.exception.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

    public OrderDto getOrders(int statusId, String baselinkerToken) {
        ResponseEntity<OrderDto> response;
        var jsonParams = "";
        var methodParams = createOrderRequestMethodParam(statusId);
        try {
            jsonParams = convertMapToString(methodParams);
        } catch (JsonProcessingFailureException e) {
            e.printStackTrace();
        }
        var body = createRequestBody("getOrders", jsonParams);
        var requestEntity = new HttpEntity<>(body, getHttpHeaders(baselinkerToken));
        try {
            response = restTemplate.exchange(baselinkerUrl, HttpMethod.POST, requestEntity, OrderDto.class);
        } catch (HttpServerErrorException s) {
            throw new HttpServerException(s.getStatusCode(), "Problem with call to Baselinker API");
        } catch (HttpClientErrorException c) {
            throw new HttpClientException(c.getStatusCode(), "Wrong request to Baselinker API");
        } catch (RestClientException r) {
            throw new RestResponseException("Server returned an HTML error page");
        }
        return response.getBody();
    }

    public BaselinkerResponse setField(FieldDto fieldDto, String baselinkerToken){
        ResponseEntity<BaselinkerResponse> response;
        var jsonParams = "";
        var methodParams = createOrderFieldRequestMethodParam(fieldDto);
        try {
            jsonParams = convertMapToString(methodParams);
        } catch (JsonProcessingFailureException e) {
            e.printStackTrace();
        }
        var body = createRequestBody("setOrderFields", jsonParams);
        var requestEntity = new HttpEntity<MultiValueMap<String, Object>>(body, getHttpHeaders(baselinkerToken));
        try {
            response = restTemplate.exchange(baselinkerUrl, HttpMethod.POST, requestEntity, BaselinkerResponse.class);
        } catch (HttpServerErrorException s) {
            throw new HttpServerException(s.getStatusCode(), "Problem with call to Baselinker API");
        } catch (HttpClientErrorException c){
            throw new HttpClientException(c.getStatusCode(), "Wrong request to Baselinker API");
        } catch (RestClientException r) {
            throw new RestResponseException("Server returned an HTML error page");
        }
        return response.getBody();
    }

    public BaselinkerResponse setDelivery(DeliveryDto deliveryDto, String baselinkerToken) {
        var headers = getHttpHeaders(baselinkerToken);
        var body = new LinkedMultiValueMap<String, Object>();
        body.add("method", "createPackage");
        var methodParams = new HashMap<String, Object>();
        methodParams.put("order_id", deliveryDto.getOrderId());
        methodParams.put("courier_code", deliveryDto.getCourierCode());
        var fields = new ArrayList<Map<String, Object>>();
        var packages = new ArrayList<Map<String, Object>>();
        if ("pocztapolska".equalsIgnoreCase(deliveryDto.getCourierCode())) {
            for (Map.Entry<String, Object> entry : deliveryDto.getCustomFields().entrySet()) {
                fields.add(createDeliveryField(entry.getKey(), entry.getValue()));
            }
            packages.add(deliveryDto.getPackageDetails());
        } else if ("gls".equalsIgnoreCase(deliveryDto.getCourierCode())) {
            for (Map.Entry<String, Object> entry : deliveryDto.getCustomFields().entrySet()) {
                fields.add(Map.of("id", entry.getKey(), "value", entry.getValue().toString()));
            }
            packages.add(deliveryDto.getPackageDetails());
        } else {
            throw new InvalidCourierException("Unknown courier: " + deliveryDto.getCourierCode());
        }
        methodParams.put("fields", fields);
        methodParams.put("packages", packages);
        var jsonParams = "";
        try {
            jsonParams = convertMapToString(methodParams);
        } catch (JsonProcessingFailureException e) {
            e.printStackTrace();
        }
        body.add("parameters", jsonParams);
        try {
            var requestEntity = new HttpEntity<MultiValueMap<String, Object>>(body, headers);
            var responseEntity = restTemplate.exchange(baselinkerUrl, HttpMethod.POST, requestEntity, BaselinkerResponse.class);
            return responseEntity.getBody();
        } catch (HttpServerErrorException s) {
            throw new HttpServerException(s.getStatusCode(), "Problem with call to Baselinker API");
        } catch (HttpClientErrorException c){
            throw new HttpClientException(c.getStatusCode(), "Wrong request to Baselinker API");
        } catch (RestClientException r) {
            throw new RestResponseException("Server returned: " + r);
        }
    }

    private String convertMapToString(Map<String,Object> params) throws JsonProcessingFailureException {
        var jsonParams = "";
        try {
            jsonParams = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new JsonProcessingFailureException("Object Mapper cannot convert value as string");
        }
        return jsonParams;
    }
    
    private HttpHeaders getHttpHeaders(String baselinkerToken) {
        return new HttpHeaders() {
            {
                setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                set("X-BLToken", baselinkerToken);
            }};
    }

    private HashMap<String, Object> createOrderRequestMethodParam(int statusId){
        return new HashMap<>() {
            {
                put("status_id", statusId);
                put("include_custom_extra_fields", true);
            }};
    }

    private LinkedMultiValueMap<String, Object> createRequestBody(String method ,String jsonParams) {
        return new LinkedMultiValueMap<>() {
            {
                add("method", method);
                add("parameters", jsonParams);
            }};
    }

    private Map<String, Object> createDeliveryField(String id, Object value) {
        return new HashMap<>() {
            {
                put("id", id);
                put("value", value);
            }};
    }

    private HashMap<String, Object> createOrderFieldRequestMethodParam(FieldDto fieldDto) {
        var customExtraFieldsMap = new HashMap<String, String>();
        for (CustomExtraFieldDto customField : fieldDto.getCustomExtraFields()) {
            customExtraFieldsMap.put(customField.getFieldId(), customField.getFieldContent());
        }

        return new HashMap<>() {
            {
                put("order_id", fieldDto.getOrderId());
                put("admin_comments", fieldDto.getAdminComment());
                put("extra_field_1", fieldDto.getFirstExtraField());
                put("custom_extra_fields", customExtraFieldsMap);
            }};
    }
}
