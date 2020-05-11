package com.synuwxy.dango.api.docker;

import java.io.IOException;
import java.util.List;

/**
 * @author wxy
 */
public interface DockerfileService {

    /**
     * 生成dockerfile
     * 读取相应类型的dockerfile，copy到指定目录下
     * @param target 目标目录
     * @param type 类型
     * @throws IOException IO异常
     */
    void generatorDockerfile(String target, String type) throws IOException;

    /**
     * 获取现在可以使用的 dockerfile 类型
     * @return dockerfile 类型列表
     */
    List<String> getDockerfileType();

    /**
     * 创建dockerfile
     * @param type 类型
     * @param context 内容
     */
    void createDockerfile(String type, String context);
}
