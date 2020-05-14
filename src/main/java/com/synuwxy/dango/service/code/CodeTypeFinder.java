package com.synuwxy.dango.service.code;

import com.synuwxy.dango.service.code.model.CodeType;

/**
 * @author wxy
 */
public interface CodeTypeFinder {

    /**
     * 尝试寻找代码的类型
     * @param codePath 代码根目录
     * @return 代码类型模型
     */
    CodeType findCodeType(String codePath);
}
