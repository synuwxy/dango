package com.synuwxy.dango.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wxy
 */
@Data
public class ResultObject<T> implements Serializable {

    private String status;

    private String message;

    private T data;

    public final static String ERROR = "1";

    public final static String SUCCESS = "0";

    private ResultObject() {
    }

    private ResultObject(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public static <T> ResultObject<T> instance(String status, String message, T data) {
        return new ResultObject<>(status, message, data);
    }

    public static ResultObject<Object> error(String message) {
        ResultObject<Object> resultObject = new ResultObject<>();
        resultObject.setStatus(ERROR);
        resultObject.setMessage(message);
        return resultObject;
    }

    public static ResultObject<Object> success() {
        ResultObject<Object> resultObject = new ResultObject<>();
        resultObject.setStatus(SUCCESS);
        return resultObject;
    }
}