package com.synuwxy.dango.api.docker.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wxy
 */
@Data
public class CreateDockerfileParam {

    @NotBlank
    private String type;

    @NotBlank
    private String context;
}
