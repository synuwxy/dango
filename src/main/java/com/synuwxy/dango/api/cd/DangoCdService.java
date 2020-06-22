package com.synuwxy.dango.api.cd;

import com.synuwxy.dango.api.cd.model.DockerDeployParam;

/**
 * @author wxy
 */
public interface DangoCdService {
    /**
     * 使用docker方式部署容器
     * @param dockerDeployParam docker 部署参数
     */
    void deploy(DockerDeployParam dockerDeployParam);
}
