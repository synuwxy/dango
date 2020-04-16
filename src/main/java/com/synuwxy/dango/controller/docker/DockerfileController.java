package com.synuwxy.dango.controller.docker;

import com.synuwxy.dango.api.docker.DockerfileService;
import com.synuwxy.dango.common.ResultObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
