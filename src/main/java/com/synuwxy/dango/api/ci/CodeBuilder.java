package com.synuwxy.dango.api.ci;

import java.io.File;

/**
 * 从源码进行构建的接口
 * @author wxy
 */
public interface CodeBuilder {

    /**
     * 编译
     * @param repo git repo 地址
     * @param branch 分支
     * @param type 类型
     * @return 构建产物
     * @throws Exception 异常
     */
    File build(String repo, String branch, String type, String workspace) throws Exception;

    /**
     * 编译代码，将产出物copy到目标目录并删除编译目录
     * @param repo git repo 地址
     * @param branch 分支
     * @param type 类型
     * @param target 目标目录
     * @throws Exception 异常
     */
    void cleanBuild(String repo, String branch, String type, String target) throws Exception;
}
