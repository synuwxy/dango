package com.synuwxy.dango.api.ci;

import com.synuwxy.dango.api.ci.model.DockerCustomBuildParam;
import com.synuwxy.dango.api.ci.model.DockerBuildParam;

import java.io.IOException;

/**
 * 使用docker的方式进行 CI 的接口
 * @author wxy
 */
public interface DockerCiService {

    /**
     * 使用Docker进行构建，产出一个docker镜像
     * @param dockerBuildParam docker编译参数
     * @throws IOException IO
     * @throws InterruptedException Interrupted
     */
    void build(DockerBuildParam dockerBuildParam) throws IOException, InterruptedException;

    /**
     * 使用Docker进行自定义构建，产出一个docker镜像
     * @param dockerCustomBuildParam docker自定义构建参数
     * @throws IOException IO
     * @throws InterruptedException Interrupted
     */
    void customBuild(DockerCustomBuildParam dockerCustomBuildParam) throws IOException, InterruptedException;
}
