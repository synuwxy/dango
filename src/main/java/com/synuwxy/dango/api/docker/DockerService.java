package com.synuwxy.dango.api.docker;

/**
 * docker服务接口
 * @author wxy
 */
public interface DockerService {

    /**
     * 构建，产出一个 docker 镜像
     * @param workspace 构建工作目录
     * @param type 类型
     * @param tag 镜像tag
     */
    void build(String workspace, String type, String tag);

    void run();

    void push();

    void update();
}
