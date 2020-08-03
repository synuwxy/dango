package com.synuwxy.dango.controller.cd;

import com.synuwxy.dango.service.cd.DockerCdService;
import com.synuwxy.dango.service.cd.model.DockerDeployServiceParam;
import com.synuwxy.dango.common.ResultObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxy
 */
@RestController
@RequestMapping("/cd/docker")
public class DockerCdController {
    private final DockerCdService dockerCdService;

    public DockerCdController(DockerCdService dockerCdService) {
        this.dockerCdService = dockerCdService;
    }

    @PostMapping("/deploy")
    public ResultObject deploy(@Validated @RequestBody DockerDeployServiceParam dockerDeployServiceParam) {
        dockerCdService.deploy(dockerDeployServiceParam);
        return ResultObject.success();
    }

    @PostMapping("/slideDeploy")
    public ResultObject slideDeploy(@Validated @RequestBody DockerDeployServiceParam dockerDeployServiceParam) {
        dockerCdService.slideDeploy(dockerDeployServiceParam);
        return ResultObject.success();
    }
}
