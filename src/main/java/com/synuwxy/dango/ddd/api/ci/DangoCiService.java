package com.synuwxy.dango.ddd.api.ci;

import com.synuwxy.dango.ddd.api.ci.model.DockerCiParam;
import com.synuwxy.dango.ddd.api.ci.model.DockerCustomCiParam;

/**
 * @author wxy
 */
public interface DangoCiService {

    /**
     * 使用Docker进行构建，产出一个docker镜像
     * @param dockerCiParam docker编译参数
     */
    void build(DockerCiParam dockerCiParam);

    /**
     * 使用Docker进行自定义构建，产出一个docker镜像
     * @param dockerCustomCiParam docker自定义构建参数
     */
    void customBuild(DockerCustomCiParam dockerCustomCiParam);
}
