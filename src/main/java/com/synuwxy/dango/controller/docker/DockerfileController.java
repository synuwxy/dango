package com.synuwxy.dango.controller.docker;

import com.synuwxy.dango.service.docker.DockerfileService;
import com.synuwxy.dango.service.docker.model.CreateDockerfileParam;
import com.synuwxy.dango.common.ResultObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wxy
 */
@RestController
@RequestMapping("dockerfile")
public class DockerfileController {

    private final DockerfileService dockerfileService;

    public DockerfileController(DockerfileService dockerfileService) {
        this.dockerfileService = dockerfileService;
    }

    @GetMapping("/getDockerfileType")
    public ResultObject<List<String>> getDockerfileType() {
        List<String> types = dockerfileService.getDockerfileType();
        return ResultObject.success(types);
    }

    @PostMapping("/createDockerfile")
    public ResultObject<?> createDockerfile(@Validated @RequestBody CreateDockerfileParam createDockerfileParam) {
        dockerfileService.createDockerfile(createDockerfileParam.getType(), createDockerfileParam.getContext());
        return ResultObject.success();
    }
}
