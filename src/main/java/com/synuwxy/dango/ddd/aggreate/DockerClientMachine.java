package com.synuwxy.dango.ddd.aggreate;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxy
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DockerClientMachine {

    private String host;
    private int port = 2375;

    public DockerClient createTcpClient() {
        String serverUrl = "tcp://" + host + ":" + port;
        return DockerClientBuilder
                .getInstance(serverUrl).build();
    }

    public static DockerClientMachine create(String host, int port) {
        return new DockerClientMachine(host, port);
    }
}
