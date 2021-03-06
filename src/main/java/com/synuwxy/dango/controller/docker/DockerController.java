package com.synuwxy.dango.controller.docker;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.synuwxy.dango.service.docker.DockerService;
import com.synuwxy.dango.service.docker.model.PullImageParam;
import com.synuwxy.dango.service.docker.model.PushImageParam;
import com.synuwxy.dango.service.docker.model.SearchContainerParam;
import com.synuwxy.dango.service.docker.model.SearchImageParam;
import com.synuwxy.dango.common.ResultObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wxy
 */
@RestController
@RequestMapping("/docker")
public class DockerController {

    private final DockerService dockerService;

    public DockerController(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @GetMapping("/search/container")
    public ResultObject searchContainer(@RequestParam("name") String name) {
        Container container = dockerService.searchContainer(name);
        return ResultObject.success(container);
    }

    @GetMapping("/search/containers")
    public ResultObject searchContainers(SearchContainerParam searchContainerParam) {
        List<Container> images = dockerService.searchContainers(searchContainerParam);
        return ResultObject.success(images);
    }

    @GetMapping("/search/image")
    public ResultObject searchImage(@RequestParam("tag") String tag) {
        Image image = dockerService.searchImage(tag);
        return ResultObject.success(image);
    }

    @GetMapping("/search/images")
    public ResultObject searchImages(SearchImageParam searchImageParam) {
        List<Image> images = dockerService.searchImages(searchImageParam);
        return ResultObject.success(images);
    }

    @PostMapping("/pull/image")
    public ResultObject pullImage(@RequestBody @Validated PullImageParam pullImageParam) {
        dockerService.pull(pullImageParam.getTag());
        return ResultObject.success();
    }

    @PostMapping("/push/image")
    public ResultObject pushImage(@RequestBody @Validated PushImageParam pushImageParam) {
        dockerService.push(pushImageParam.getTag());
        return ResultObject.success();
    }
}
