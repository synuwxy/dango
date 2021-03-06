package com.synuwxy.dango.service.docker.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wxy
 */
@Data
public class PullImageParam {
    @NotBlank(message = "镜像tag不能为空")
    private String tag;
}
