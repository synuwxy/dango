package com.synuwxy.dango.aggreate.docker.container;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.model.Container;
import com.synuwxy.dango.api.docker.model.SearchContainerParam;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * @author wxy
 */
@Slf4j
public class DockerContainerRepository {

    private final DockerClient dockerClient;

    public DockerContainerRepository(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public Container searchContainer(String name) {
        log.info("查询容器 name: {}", name);
        List<Container> containers = dockerClient.listContainersCmd()
                .withNameFilter(Collections.singleton(name))
                .exec();
        if (containers.isEmpty()) {
            log.info("查询结果为空");
            return null;
        }
        return containers.get(0);
    }

    public List<Container> searchContainers(SearchContainerParam searchContainerParam) {
        log.info("查询容器列表 searchContainerParam: {}", JSONObject.toJSONString(searchContainerParam));
        ListContainersCmd listContainersCmd = dockerClient.listContainersCmd();

        if (null != searchContainerParam.getContainerId()) {
            listContainersCmd.withIdFilter(Collections.singleton(searchContainerParam.getContainerId()));
        }
        if (null != searchContainerParam.getContainerName()) {
            listContainersCmd.withNameFilter(Collections.singleton(searchContainerParam.getContainerName()));
        }
        if (null != searchContainerParam.getLabels() && searchContainerParam.getLabels().size() > 0) {
            listContainersCmd.withLabelFilter(searchContainerParam.getLabels());
        }
        if (null != searchContainerParam.getStatus() && searchContainerParam.getStatus().size() > 0) {
            listContainersCmd.withStatusFilter(searchContainerParam.getStatus());
        }

        return listContainersCmd.exec();
    }
}
