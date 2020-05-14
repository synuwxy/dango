package com.synuwxy.dango.service.code;

import com.synuwxy.dango.service.code.model.CodeType;

import java.io.File;

/**
 * @author wxy
 */
public interface CodeTypeHandler {

    /**
     * 是否符合当前类型
     * @param file 判断文件
     * @return 如果文件符合当前类型的判断条件返回 true
     */
    boolean confirm(File file);

    /**
     * 生成代码类型数据
     * @param codePath 代码根目录
     * @return 代码类型模型
     */
    CodeType generatorType(String codePath);
}
