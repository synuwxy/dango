package com.synuwxy.dango.common.utils;

import com.synuwxy.dango.common.exception.VerifyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;


/**
 * @author wxy
 */
public class ParamValidUtils {

    public static void dealBindingResult(BindingResult result) {
        if (null == result) {
            return;
        }
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            if (list.size() == 0) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError error : list) {
                stringBuilder.append(error.getDefaultMessage());
                stringBuilder.append("; ");
            }
            throw new VerifyException(stringBuilder.toString());
        }
    }
}
