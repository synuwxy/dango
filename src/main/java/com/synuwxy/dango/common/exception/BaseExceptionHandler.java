package com.synuwxy.dango.common.exception;

import com.synuwxy.dango.common.ResultObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author wxy
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ResponseBody
    @ExceptionHandler({IOException.class, InterruptedException.class, RuntimeException.class})
    public ResultObject handle(Exception e) {
        e.printStackTrace();
        return ResultObject.error(e.getMessage());
    }

}
