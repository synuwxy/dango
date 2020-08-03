package com.synuwxy.dango.controller.ci;

import com.synuwxy.dango.common.ResultObject;
import com.synuwxy.dango.ddd.api.ci.DangoCiService;
import com.synuwxy.dango.ddd.api.ci.model.DockerCiParam;
import com.synuwxy.dango.ddd.api.ci.model.DockerCustomCiParam;
import com.synuwxy.dango.service.ci.DockerCiService;
import com.synuwxy.dango.service.ci.model.DockerBuildParam;
import com.synuwxy.dango.service.ci.model.DockerCustomBuildParam;
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
    private final DangoCiService dangoCiService;

    public DockerCiController(DockerCiService dockerCiService, DangoCiService dangoCiService) {
        this.dockerCiService = dockerCiService;
        this.dangoCiService = dangoCiService;
    }

    @PostMapping("/build")
    public ResultObject build(@Validated @RequestBody DockerBuildParam dockerBuildParam) throws IOException, InterruptedException {
        dockerCiService.build(dockerBuildParam);
        return ResultObject.success();
    }

    @PostMapping("/customBuild")
    public ResultObject customBuild(@Validated @RequestBody DockerCustomBuildParam dockerCustomBuildParam) throws IOException, InterruptedException {
        dockerCiService.customBuild(dockerCustomBuildParam);
        return ResultObject.success();
    }

    @PostMapping("/event/build")
    public ResultObject eventBuild(@Validated @RequestBody DockerCiParam dockerCiParam) {
        dangoCiService.build(dockerCiParam);
        return ResultObject.success();
    }

    @PostMapping("/event/customBuild")
    public ResultObject eventCustomBuild(@Validated @RequestBody DockerCustomCiParam dockerCustomCiParam) {
        dangoCiService.customBuild(dockerCustomCiParam);
        return ResultObject.success();
    }
}
