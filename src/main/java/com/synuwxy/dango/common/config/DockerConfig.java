package com.synuwxy.dango.common.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.synuwxy.dango.common.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
@Configuration
public class DockerConfig {

    @Value("${docker.host}")
    private String dockerHost;
    @Value("${docker.port}")
    private String dockerPort;

    private final CommonConfig commonConfig;

    public DockerConfig(CommonConfig commonConfig) {
        this.commonConfig = commonConfig;
    }

    @PostConstruct
    public void init() {
        FileUtil.mkdir(commonConfig.getDockerfileWorkspace());
        List<String> defaultDockerfileList = getDefaultDockerfileList();
        try {
            for (String defaultDockerfileType : defaultDockerfileList) {
                String url = "static/dockerfile/" + defaultDockerfileType + "/Dockerfile";
                ClassPathResource resource = new ClassPathResource(url);
                String path = commonConfig.getDockerfileWorkspace() + "/" + defaultDockerfileType;
                FileUtil.mkdir(path);
                File dockerfile = new File(path + "/Dockerfile");
                FileCopyUtils.copy(resource.getInputStream(), new FileOutputStream(dockerfile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getDefaultDockerfileList() {
        List<String> defaultDockerfileList = new ArrayList<>();

        defaultDockerfileList.add("maven");

        return defaultDockerfileList;
    }

    @Bean
    public DockerClient dockerClientBean() {
        String serverUrl = "tcp://" + dockerHost + ":" + dockerPort;
        return DockerClientBuilder
                .getInstance(serverUrl).build();
    }
}
