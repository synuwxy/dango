package com.synuwxy.dango.api.ci.model;

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
public class DockerCustomCiParam {

    @Valid
    private GitCloneParam gitCloneParam;

    @NotBlank(message = "镜像全称不能为空")
    private String imageFullName;

    @NotBlank(message = "dockerfile类型不能为空")
    private String dockerfileType;

    @NotBlank(message = "构建脚本不能为空")
    private String command;

    @NotBlank(message = "构建物相对路径不能为空")
    private String productPath;

    private List<String> extraPaths = new ArrayList<>();

    private List<DockerClientMachine> machines = new ArrayList<>();
}
