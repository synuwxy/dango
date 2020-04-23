package com.synuwxy.dango.api.script;

import com.synuwxy.dango.api.script.model.ScriptExecuteParam;

import java.io.IOException;

/**
 * @author wxy
 */
public interface ScriptService {

    /**
     * 执行
     * @param scriptExecuteParam 命令
     * @return 执行结果
     * @throws IOException IO
     */
    boolean exec(ScriptExecuteParam scriptExecuteParam) throws IOException;
}
