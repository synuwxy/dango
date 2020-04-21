package com.synuwxy.dango.api.ci.model;

import com.synuwxy.dango.api.git.model.GitCloneParam;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wxy
 */
@Data
public class DockerBuildParam {

    @NotNull
    @Valid
    private GitCloneParam gitCloneParam;

    private String dockerTag;

    @NotBlank(message = "代码类型不能为空")
    private String type;
}
