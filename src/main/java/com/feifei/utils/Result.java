package com.qding.bigdata.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author yanpf
 * @date 2019/6/12 10:49
 * @description
 */

@Data
@ApiModel(value = "返回结果")
public class Result<T> {

    @ApiModelProperty(name = "错误码", example = "200")
    private int code;

    @ApiModelProperty(name = "结果信息")
    private T data;

    @ApiModelProperty(name = "描述信息")
    private String msg;

    private Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> Result<T> fail(HttpStatus code, String msg){
        return new Result<>(code.value(), null, msg);
    }


    public static <T> Result<T> success(T data){
        return new Result<>(HttpStatus.OK.value(), data, "success");
    }

    public static Result<String> success(){
        return new Result<>(HttpStatus.OK.value(), null, "success");
    }
}
