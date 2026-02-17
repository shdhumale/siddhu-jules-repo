package com.example.dogapiconsumer.advice;

import com.example.dogapiconsumer.model.ApiResponse;
import com.example.dogapiconsumer.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class UnifiedResponseAdvice implements ResponseBodyAdvice<Object> {

    private final DogService dogService;

    @Autowired
    public UnifiedResponseAdvice(DogService dogService) {
        this.dogService = dogService;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof ApiResponse) {
            return body;
        }

        int status = 200;
        if (response instanceof ServletServerHttpResponse servletResponse) {
            status = servletResponse.getServletResponse().getStatus();
        }

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setResponse(body);
        apiResponse.setImage(dogService.getHttpDogImage(status));

        return apiResponse;
    }
}
