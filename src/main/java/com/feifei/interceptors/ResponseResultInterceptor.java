package com.qding.bigdata.interceptors;

import com.qding.bigdata.annotations.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author yanpf
 * @date 2019/6/27 18:49
 * @description
 */

@Slf4j
public class ResponseResultInterceptor implements HandlerInterceptor {

    public static final String RESPONSE_RESULT_ANN = "$$RESPONSE-RESULT-ANN$$";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> controller = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            if(controller.isAnnotationPresent(ResponseResult.class) || method.isAnnotationPresent(ResponseResult.class)){
                request.setAttribute(RESPONSE_RESULT_ANN, "true");
            }
        }

        return true;
    }
}
