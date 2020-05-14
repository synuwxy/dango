package com.synuwxy.dango.service.ci;

import com.synuwxy.dango.service.git.model.GitCloneParam;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
     * @throws IOException IO
     * @throws InterruptedException Interrupted
     */
    File build(GitCloneParam gitCloneParam, String workspace) throws IOException, InterruptedException;

    /**
     * 编译代码，将产出物copy到目标目录并删除编译目录
     * @param gitCloneParam git clone 所需入参
     * @param target 目标目录
     * @throws IOException IO
     * @throws InterruptedException Interrupted
     */
    void cleanBuild(GitCloneParam gitCloneParam, String target) throws IOException, InterruptedException;

    /**
     * 自定义构建
     * @param gitCloneParam git clone 所需入参
     * @param command 构建命令
     * @param productPath 构建物相对路径
     * @param workspace 工作目录
     * @return 构建物
     * @throws IOException IO
     * @throws InterruptedException Interrupted
     */
    File customBuild(GitCloneParam gitCloneParam, String command, String productPath, String workspace) throws IOException, InterruptedException;

    /**
     * 自定义编译代码，将产出物copy到目标目录并删除编译目录
     * @param gitCloneParam git clone 所需入参
     * @param command 构建命令
     * @param productPaths 构建物相对路径
     * @param extraPath 额外的文件路径
     * @param target 目标目录
     * @throws IOException IO
     * @throws InterruptedException Interrupted
     */
    void customCleanBuild(GitCloneParam gitCloneParam, String command, String productPaths, List<String> extraPath, String target) throws IOException, InterruptedException;
}
