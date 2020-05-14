package com.synuwxy.dango.api.ci.model;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.service.git.model.GitCloneParam;
import lombok.Data;

/**
 * @author wxy
 */
@Data
public class DockerCiCommand {

    private GitCloneParam gitCloneParam;

    private String imageFullName;

    private String dockerfileType;

    private DockerClient dockerClient;

}
