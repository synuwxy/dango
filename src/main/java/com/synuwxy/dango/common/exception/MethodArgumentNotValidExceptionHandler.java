package com.synuwxy.dango.common.exception;

import com.synuwxy.dango.common.ResultObject;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author wxy
 */
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultObject<?> handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError error : list) {
                stringBuilder.append(error.getDefaultMessage());
                stringBuilder.append("; ");
            }
            return ResultObject.error(stringBuilder.toString());
        }

        throw new RuntimeException("bindingResult 不存在 error");
    }
}
