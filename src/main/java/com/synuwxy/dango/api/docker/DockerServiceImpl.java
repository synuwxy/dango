package com.synuwxy.dango.api.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.synuwxy.dango.api.docker.model.ContainerModel;
import com.synuwxy.dango.common.utils.DockerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wxy
 */
@Slf4j
@Service
public class DockerServiceImpl implements DockerService {

    private final DockerClient dockerClient;

    public DockerServiceImpl(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Override
    public void build(String workspace, String type, String tag) {
        log.info("生成Dockerfile workspace: {}, type: {}, tag: {}", workspace, type, tag);
        DockerUtil.generatorDockerfile(workspace, type);
        File dir = new File(workspace);
//        BuildImageResultCallback callback = new BuildImageResultCallback() {
//            @Override
//            public void onNext(BuildResponseItem item) {
//                log.info("[docker log] {}", item);
//                super.onNext(item);
//            }
//        };
        BuildImageResultCallback callback = new BuildImageResultCallback();
        Set<String> tags = new HashSet<>();
        tags.add(tag);
        log.info("构建镜像");
        BuildImageCmd buildImageCmd = dockerClient.buildImageCmd(dir);
        buildImageCmd.withTags(tags).exec(callback).awaitImageId();
    }

    @Override
    public void run(ContainerModel containerModel) {
        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(containerModel.getImageName());
        createContainerCmd.withName(containerModel.getContainerName());

        if (!containerModel.getPortBindings().isEmpty()) {
//            createContainerCmd.withHostConfig(newHostConfig().withPortBindings(containerModel.getPortBindings()).);
            Ports ports = new Ports();
            ports.bind(ExposedPort.tcp(10011), new Ports.Binding("0.0.0.0", "8080"));
            createContainerCmd.withHostConfig(HostConfig.newHostConfig().withPortBindings(ports));
        }

        if (!containerModel.getVolumes().isEmpty()) {
            createContainerCmd.withVolumes(containerModel.getVolumes());
        }

        if (!containerModel.getEnvs().isEmpty()) {
            createContainerCmd.withEnv(containerModel.getEnvs());
        }

        String containerId = createContainerCmd.exec().getId();
        dockerClient.startContainerCmd(containerId).exec();
    }

    @Override
    public void push() {

    }

    @Override
    public void update() {

    }
}
