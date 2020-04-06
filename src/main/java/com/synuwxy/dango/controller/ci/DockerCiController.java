package com.synuwxy.dango.controller.ci;

import com.synuwxy.dango.api.ci.DockerCiService;
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
@RequestMapping("/ci/docker")
public class DockerCiController {

    private final DockerCiService dockerCiService;

    public DockerCiController(DockerCiService dockerCiService) {
        this.dockerCiService = dockerCiService;
    }

    @PostMapping("/build")
    public void build(@Validated @RequestBody DockerBuildParam dockerBuildParam, BindingResult result) {
        ParamValidUtils.dealBindingResult(result);
        dockerCiService.build(dockerBuildParam);
    }
}
