package com.synuwxy.dango.api.git.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wxy
 */
@Data
public class GitCloneParam {

    @NotBlank(message = "代码仓库不能为空")
    private String repository;

    private String branch;

    private String username;

    private String password;
}
