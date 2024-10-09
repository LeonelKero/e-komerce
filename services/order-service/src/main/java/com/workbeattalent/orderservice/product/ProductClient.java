package com.workbeattalent.orderservice.product;

import com.workbeattalent.orderservice.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductClient {

    @Value(value = "${application.config.product-url}")
    private String productURL;

    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProductPurchaseResponse> getPurchases(final List<ProductPurchaseRequest> requestBody) {
        final var headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        // Note: Possibility to pass other headers parameters like API key and security stuff
        final var requestEntity = new HttpEntity<>(requestBody, headers);
        final var responseType = new ParameterizedTypeReference<List<ProductPurchaseResponse>>() {
        };

        final var responseEntity = restTemplate.exchange(
                productURL + "/purchases",
                HttpMethod.POST,
                requestEntity,
                responseType
        );

        // Check if an error has occurred
        if (responseEntity.getStatusCode().isError())
            throw new BusinessException("An error occurred when performing purchases, with status: " + responseEntity.getStatusCode().value());

        return responseEntity.getBody();
    }
}
