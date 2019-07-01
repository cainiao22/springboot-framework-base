package com.qding.bigdata.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;


/**
 * @author yanpf
 * @date 2019/6/28 11:11
 * @description
 */

@Data
@AllArgsConstructor
public class ErrorResultException extends Exception {

    private HttpStatus code;

    private String errorMsg;

    public ErrorResultException(HttpStatus code, Exception ex){
        super(ex);
        this.code = code;
    }
}
