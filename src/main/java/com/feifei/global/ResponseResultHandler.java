package com.qding.bigdata.global;

import com.qding.bigdata.exceptions.ErrorResultException;
import com.qding.bigdata.interceptors.ResponseResultInterceptor;
import com.qding.bigdata.utils.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yanpf
 * @date 2019/6/27 18:42
 * @description
 */

@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        if("true".equals(request.getAttribute(ResponseResultInterceptor.RESPONSE_RESULT_ANN))){
            return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof ErrorResultException){
            ErrorResultException errorResult = (ErrorResultException) body;
            return Result.fail(errorResult.getCode(), errorResult.getErrorMsg());
        }else if(body instanceof Result){
            return body;
        }

        return Result.success(body);
    }
}
