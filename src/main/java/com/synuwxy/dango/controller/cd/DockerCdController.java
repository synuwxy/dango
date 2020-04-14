package com.synuwxy.dango.controller.cd;

import com.synuwxy.dango.api.cd.DockerCdService;
import com.synuwxy.dango.api.cd.model.DockerDeployParam;
import com.synuwxy.dango.api.ci.model.DockerBuildParam;
import com.synuwxy.dango.common.ResultObject;
import com.synuwxy.dango.common.utils.ParamValidUtils;
import org.springframework.validation.BindingResult;
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
    public ResultObject<?> deploy(@Validated @RequestBody DockerDeployParam dockerDeployParam, BindingResult result) {
        ParamValidUtils.dealBindingResult(result);
        dockerCdService.deploy(dockerDeployParam);
        return ResultObject.success();
    }

    @PostMapping("/slideDeploy")
    public ResultObject<?> slideDeploy(@Validated @RequestBody DockerDeployParam dockerDeployParam, BindingResult result) {
        ParamValidUtils.dealBindingResult(result);
        dockerCdService.slideDeploy(dockerDeployParam);
        return ResultObject.success();
    }
}
