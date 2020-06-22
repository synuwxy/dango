package com.synuwxy.dango.service.cd;

import com.synuwxy.dango.service.cd.model.DockerDeployServiceParam;

/**
 * @author wxy
 */
public interface DockerCdService {

    /**
     * 使用docker方式部署容器
     * @param dockerDeployServiceParam docker 部署参数
     */
    void deploy(DockerDeployServiceParam dockerDeployServiceParam);

    /**
     * 滑动更新，如果之前有容器再运行就停止容器，重新部署
     * @param dockerDeployServiceParam docker 部署参数
     */
    void slideDeploy(DockerDeployServiceParam dockerDeployServiceParam);
}
