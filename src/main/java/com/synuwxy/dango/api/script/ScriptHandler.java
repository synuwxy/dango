package com.synuwxy.dango.api.script;

/**
 * 脚本执行，通过实现此接口获取执行脚本的能力
 * @author wxy
 */
public interface ScriptHandler {

    /**
     * 执行脚本命令
     * @param workspace 工作目录
     * @param command 命令
     * @return 执行结果
     */
    boolean run(String workspace, String command);
}
