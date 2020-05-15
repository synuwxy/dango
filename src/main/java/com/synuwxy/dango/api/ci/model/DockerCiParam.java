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
public class DockerCiParam {

    @Valid
    private GitCloneParam gitCloneParam;

    @NotBlank(message = "镜像全称不能为空")
    private String imageName;

    @NotBlank(message = "dockerfile类型不能为空")
    private String type;

    private List<DockerClientMachine> machines = new ArrayList<>();
}
