package com.synuwxy.dango.common.exception;

import com.synuwxy.dango.common.ResultObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author wxy
 */
@ControllerAdvice
public class VerifyExceptionHandler {

    @ExceptionHandler(VerifyException.class)
    public ResultObject<?> handle(Exception e) {
        return ResultObject.error(e.getMessage());
    }
}
