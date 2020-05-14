package com.synuwxy.dango.api.ci.model;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.aggreate.DockerClientMachine;
import com.synuwxy.dango.service.git.model.GitCloneParam;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
@Data
public class DockerCustomCiCommand {

    private GitCloneParam gitCloneParam;

    private String imageFullName;

    private String dockerfileType;

    private String command;

    private String productPath;

    private List<String> extraPaths = new ArrayList<>();

    private DockerClient dockerClient;

}
