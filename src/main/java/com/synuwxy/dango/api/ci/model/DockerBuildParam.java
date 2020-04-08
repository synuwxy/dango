package com.synuwxy.dango.api.ci.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wxy
 */
@Data
public class DockerBuildParam {

    @NotBlank(message = "代码仓库不能为空")
    private String repository;

    private String dockerTag;

    @NotBlank(message = "代码类型不能为空")
    private String type;

    private String branch;

}
