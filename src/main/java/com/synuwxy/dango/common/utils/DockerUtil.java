package com.synuwxy.dango.common.utils;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.ddd.aggreate.DockerClientMachine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
public class DockerUtil {

    public final static String TYPE_MAVEN = "maven";

    public static List<DockerClient> extractClients(List<DockerClientMachine> machines) {
        List<DockerClient> clients = new ArrayList<>();
        for (DockerClientMachine machine : machines) {
            clients.add(machine.createTcpClient());
        }
        return clients;
    }

}
