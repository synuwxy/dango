package com.synuwxy.dango.common.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxy
 */
@Configuration
public class DockerConfig {

    @Value("${docker.host}")
    private String dockerHost;
    @Value("${docker.port}")
    private String dockerPort;

    @Bean
    public DockerClient dockerClientBean() {
        String serverUrl = "tcp://" + dockerHost + ":" + dockerPort;
        return DockerClientBuilder
                .getInstance(serverUrl).build();
    }
}
