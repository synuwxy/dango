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
     * @param directory 目标目录
     * @param type 类型
     * @throws IOException IO异常
     */
    void generatorDockerfile(String directory, String type) throws IOException;

    /**
     * 获取现在可以使用的 dockerfile 类型
     * @return dockerfile 类型列表
     */
    List<String> getDockerfileType();
}
