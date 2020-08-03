package com.synuwxy.dango.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wxy
 */
@Data
public class ResultObject implements Serializable {

    private String status;

    private String message;

    private Object data;

    public final static String ERROR = "-1";

    public final static String SUCCESS = "0";

    private ResultObject() {
    }

    public static ResultObject error(String message) {
        ResultObject resultObject = new ResultObject();
        resultObject.setStatus(ERROR);
        resultObject.setMessage(message);
        return resultObject;
    }

    public static ResultObject success() {
        ResultObject resultObject = new ResultObject();
        resultObject.setStatus(SUCCESS);
        return resultObject;
    }

    public static ResultObject success(Object data) {
        ResultObject resultObject = new ResultObject();
        resultObject.setStatus(SUCCESS);
        resultObject.setData(data);
        return resultObject;
    }
}