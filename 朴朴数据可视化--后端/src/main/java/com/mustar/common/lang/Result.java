package com.mustar.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private Integer code;
    private String msg;
    private Object data;
    public static Result success(String msg){
        Result result=new Result();
        result.setCode(200);
        result.setMsg(msg);
        return result;
    }
    public static Result success(Object data){
        Result result=new Result();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }
    public static Result error(Integer code, String msg){
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    public static Result error(Integer code, Object data){
        Result result=new Result();
        result.setCode(code);
        result.setData(data);
        return result;
    }

}
