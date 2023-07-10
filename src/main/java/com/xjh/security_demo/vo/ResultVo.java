package com.xjh.security_demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo<T> {
    private static final long serialVersionUID = -2927933772896063509L;
    private int code;

    private String message;

    private Object data;

    public static  <T> ResultVo<T> ok(){
        return new ResultVo<>(200,"success",null);
    }

    public static  <T> ResultVo<T> ok(T data){
        return new ResultVo<>(200,"success",data);
    }

    public static  <T> ResultVo<T> err(String errMsg){
        return new ResultVo<>(-1, errMsg , null);
    }

    public static  <T> ResultVo<T> err(int code, String errMsg){
        return new ResultVo<>(code, errMsg , null);
    }
}
