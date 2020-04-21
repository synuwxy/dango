package com.synuwxy.dango.api.ci;

import com.synuwxy.dango.api.git.model.GitCloneParam;

import java.io.File;

/**
 * 从源码进行构建的接口
 * @author wxy
 */
public interface CodeBuilder {

    /**
     * 编译
     * @param gitCloneParam git clone 所需入参
     * @param workspace 工作目录
     * @return 构建产物
     * @throws Exception 异常
     */
    File build(GitCloneParam gitCloneParam, String workspace) throws Exception;

    /**
     * 编译代码，将产出物copy到目标目录并删除编译目录
     * @param gitCloneParam git clone 所需入参
     * @param type 类型
     * @param target 目标目录
     * @throws Exception 异常
     */
    void cleanBuild(GitCloneParam gitCloneParam, String type, String target) throws Exception;
}
