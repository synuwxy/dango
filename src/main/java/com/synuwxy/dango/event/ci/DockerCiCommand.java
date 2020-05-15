package com.synuwxy.dango.event.ci;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.service.git.model.GitCloneParam;
import lombok.Data;

/**
 * @author wxy
 */
@Data
public class DockerCiCommand {

    private GitCloneParam gitCloneParam;

    private String imageName;

    private String type;

    private DockerClient dockerClient;

}
