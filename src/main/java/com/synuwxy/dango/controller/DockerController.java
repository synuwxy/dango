package com.synuwxy.dango.controller;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.synuwxy.dango.api.docker.DockerService;
import com.synuwxy.dango.api.docker.model.PullImageParam;
import com.synuwxy.dango.api.docker.model.PushImageParam;
import com.synuwxy.dango.api.docker.model.SearchContainerParam;
import com.synuwxy.dango.api.docker.model.SearchImageParam;
import com.synuwxy.dango.common.ResultObject;
import com.synuwxy.dango.common.utils.ParamValidUtils;
import org.springframework.validation.BindingResult;
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
    public ResultObject<Container> searchContainer(@RequestParam("name") String name) {
        Container container = dockerService.searchContainer(name);
        return ResultObject.success(container);
    }

    @GetMapping("/search/containers")
    public ResultObject<List<Container>> searchContainers(SearchContainerParam searchContainerParam) {
        List<Container> images = dockerService.searchContainers(searchContainerParam);
        return ResultObject.success(images);
    }

    @GetMapping("/search/image")
    public ResultObject<Image> searchImage(@RequestParam("tag") String tag) {
        Image image = dockerService.searchImage(tag);
        return ResultObject.success(image);
    }

    @GetMapping("/search/images")
    public ResultObject<List<Image>> searchImages(SearchImageParam searchImageParam) {
        List<Image> images = dockerService.searchImages(searchImageParam);
        return ResultObject.success(images);
    }

    @PostMapping("/pull/image")
    public ResultObject<?> pullImage(@RequestBody @Validated PullImageParam pullImageParam, BindingResult result) {
        ParamValidUtils.dealBindingResult(result);
        dockerService.pull(pullImageParam.getTag());
        return ResultObject.success();
    }

    @PostMapping("/push/image")
    public ResultObject<?> pushImage(@RequestBody @Validated PushImageParam pushImageParam, BindingResult result) {
        ParamValidUtils.dealBindingResult(result);
        dockerService.pull(pushImageParam.getTag());
        return ResultObject.success();
    }
}
