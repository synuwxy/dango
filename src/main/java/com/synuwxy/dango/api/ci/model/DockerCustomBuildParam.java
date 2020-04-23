package com.synuwxy.dango.api.ci.model;

import com.synuwxy.dango.api.git.model.GitCloneParam;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author wxy
 */
@Data
public class DockerCustomBuildParam {

    @Valid
    private GitCloneParam gitCloneParam;

    @NotBlank(message = "镜像名称不能为空")
    private String dockerTag;

    @NotBlank(message = "dockerfile类型不能为空")
    private String type;

    @NotBlank(message = "构建脚本不能为空")
    private String command;

    @NotBlank(message = "构建物名称不能为空")
    private String productName;

    @NotBlank(message = "构建物相对路径不能为空")
    private String productPath;
}
