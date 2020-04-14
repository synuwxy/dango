package com.synuwxy.dango.api.cd;

import com.synuwxy.dango.api.cd.model.DockerDeployParam;

/**
 * @author wxy
 */
public interface DockerCdService {

    /**
     * 使用docker方式部署容器
     * @param dockerDeployParam docker 部署参数
     */
    void deploy(DockerDeployParam dockerDeployParam);

    /**
     * 滑动更新，如果之前有容器再运行就停止容器，重新部署
     * @param dockerDeployParam docker 部署参数
     */
    void slideDeploy(DockerDeployParam dockerDeployParam);
}
