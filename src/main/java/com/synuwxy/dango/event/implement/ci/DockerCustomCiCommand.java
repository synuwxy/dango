package com.synuwxy.dango.event.implement.ci;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.service.git.model.GitCloneParam;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
@Data
public class DockerCustomCiCommand {

    private GitCloneParam gitCloneParam;

    private String imageName;

    private String type;

    private String command;

    private String productPath;

    private List<String> extraPaths = new ArrayList<>();

    private DockerClient dockerClient;

}
