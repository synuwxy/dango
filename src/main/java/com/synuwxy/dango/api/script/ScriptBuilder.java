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
}
