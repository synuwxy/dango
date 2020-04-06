package com.synuwxy.dango.api.ci;

import com.synuwxy.dango.controller.ci.DockerBuildParam;

/**
 * 使用docker的方式进行 CI 的接口
 * @author wxy
 */
public interface DockerCiService {

    /**
     * 使用Docker进行构建，产出一个docker镜像
     * @param dockerBuildParam docker编译参数
     */
    void build(DockerBuildParam dockerBuildParam);
}
