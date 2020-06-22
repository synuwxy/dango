package com.synuwxy.dango.common.utils;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.aggreate.DockerClientMachine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
