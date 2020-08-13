package com.synuwxy.dango.aggreate.code;

/**
 * @author wxy
 */
public interface CodeScanner {

    /**
     * 分析代码
     * @param path 代码根目录
     * @return 代码分析结果
     */
    CodeAnalysisResult analysis(String path);
}
