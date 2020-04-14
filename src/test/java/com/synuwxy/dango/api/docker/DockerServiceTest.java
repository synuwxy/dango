package com.synuwxy.dango.api.docker;

import com.github.dockerjava.api.model.Container;
import com.synuwxy.dango.api.docker.model.ContainerState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DockerServiceTest {

    @Autowired
    private DockerService dockerService;

    @Test
    public void tagImageTest() {
        String imageName = "event-bus:v1";
        String imageNameWithRepository = "event-bus";
        String tag = "v2";
        dockerService.tagImage(imageName, imageNameWithRepository, tag);
    }

    @Test
    public void searchContainerTest() {
        String containerName = "test";
        Container container = dockerService.searchContainer(containerName, ContainerState.RUNNING);
        System.out.println(container.getStatus());
    }
}
