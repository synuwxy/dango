package com.synuwxy.dango.api.script;

import java.io.File;

/**
 * 脚本构建，通过实现此接口获取使用脚本进行构建的能力
 * @author wxy
 */
public interface ScriptBuilder {

    /**
     * 脚本编译，通过执行脚本的方式进行编译
     * @param workspace 工作目录
     * @return 编译产出物
     */
    File build(String workspace);

    /**
     * 自定义构建
     * @param command 命令
     * @param productName 构建物名称
     * @param productPath 构建物相对路径
     * @param workspace 工作路径
     * @return 构建物
     */
    File customBuild(String command, String productName, String productPath, String workspace);
}
