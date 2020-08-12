package com.ecommerce.customer.exception;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    private static final String ATTRIBUTES_ERROR_KEY = "org.springframework.boot.web.reactive.error.DefaultErrorAttributes.ERROR";

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                          ResourceProperties resourceProperties,
                                          ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
    }

    private Mono<ServerResponse> formatErrorResponse(ServerRequest serverRequest){

        Map<String, Object> errorAttributes = getErrorAttributes(serverRequest, ErrorAttributeOptions.defaults());
        int status = (int) Optional.ofNullable(errorAttributes.get("status")).orElse(500);
        errorAttributes.put("errorMessage", extractMessageFromWebExchangeBindEx(serverRequest));
        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorAttributes));
    }

    private List<String> extractMessageFromWebExchangeBindEx(ServerRequest request){
        List<String> errorMessages = new ArrayList<>();
        Map<String, Object> attributes = request.exchange().getAttributes();
        WebExchangeBindException ex = (WebExchangeBindException) attributes.get(ATTRIBUTES_ERROR_KEY);
        List<ObjectError> allErrors = ex.getAllErrors();
        for(ObjectError er : allErrors){
            errorMessages.add(er.getDefaultMessage());
        }
        return errorMessages;
    }
}
