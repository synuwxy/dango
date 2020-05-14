package com.synuwxy.dango.service.code;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
@Component
public class CodeTypeRegister {

    private final List<CodeTypeHandler> codeTypeHandlers = new ArrayList<>();

    public void add(CodeTypeHandler codeTypeHandler) {
        codeTypeHandlers.add(codeTypeHandler);
    }

    public List<CodeTypeHandler> getCodeTypeHandlers() {
        return codeTypeHandlers;
    }
}
