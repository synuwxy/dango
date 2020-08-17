package com.synuwxy.dango.aggreate.compile;

import com.synuwxy.dango.aggreate.code.Language;

/**
 * @author wxy
 */
public interface Compiler {

    /**
     * 是否属于某种语言
     * @param language 语言
     * @return 是否符合
     */
    boolean belongs(Language language);

    /**
     * 是否符合当前编译对象
     * @param root 代码根目录
     * @return 是否符合
     */
    boolean confirm(String root);

    /**
     * 编译
     */
    void compile();
}
