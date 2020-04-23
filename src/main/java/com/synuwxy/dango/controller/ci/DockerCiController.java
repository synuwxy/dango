package com.synuwxy.dango.controller.ci;

import com.synuwxy.dango.api.ci.DockerCiService;
import com.synuwxy.dango.api.ci.model.DockerCustomBuildParam;
import com.synuwxy.dango.api.ci.model.DockerBuildParam;
import com.synuwxy.dango.common.ResultObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    public ResultObject<?> build(@Validated @RequestBody DockerBuildParam dockerBuildParam) throws IOException, InterruptedException {
        dockerCiService.build(dockerBuildParam);
        return ResultObject.success();
    }

    @PostMapping("/customBuild")
    public ResultObject<?> customBuild(@Validated @RequestBody DockerCustomBuildParam dockerCustomBuildParam) throws IOException, InterruptedException {
        dockerCiService.customBuild(dockerCustomBuildParam);
        return ResultObject.success();
    }
}
