package com.synuwxy.dango.api.ci.model;

import com.synuwxy.dango.api.git.model.GitCloneParam;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author wxy
 */
@Data
public class DockerBuildParam {

    @Valid
    private GitCloneParam gitCloneParam;

    @NotBlank
    private String dockerTag;

    @NotBlank(message = "代码类型不能为空")
    private String type;
}
