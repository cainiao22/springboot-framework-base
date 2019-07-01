package com.qding.bigdata.global;

import com.qding.bigdata.exceptions.ErrorResultException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;


/**
 * @author yanpf
 * @date 2019/6/12 15:30
 * @description
 */

@ControllerAdvice
@Component
public class GlobalExceptionHandler {


    /**
     * 外部参数错误处理
     * @param exception
     * @return
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResultException handleValidException(Exception exception) {
        FieldError fieldError;
        if(exception instanceof BindException) {
            fieldError = ((BindException)exception).getBindingResult().getFieldError();
        }else if(exception instanceof MethodArgumentNotValidException){
            fieldError = ((MethodArgumentNotValidException)exception).getBindingResult().getFieldError();
        }else {
            return new ErrorResultException(HttpStatus.BAD_REQUEST, exception);
        }

        return new ErrorResultException(HttpStatus.BAD_REQUEST, fieldError.getField() + "(" + fieldError.getDefaultMessage() + ")");
    }

    /**
     * 系统错误处理
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResultException handleGlobalException(Exception exception) {
        if(exception instanceof ErrorResultException){
            return (ErrorResultException) exception;
        }

        return new ErrorResultException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }
}
