package com.synuwxy.dango.pipeline.task.docker;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.aggreate.docker.image.DockerImage;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.pipeline.PipelineTask;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxy
 */
@AllArgsConstructor
@Slf4j
public class DockerImageBuildTask implements PipelineTask {

    private final DockerImage dockerImage;
    private final String workspace;

    @Override
    public void process() {
        dockerImage.build(workspace);
    }

    @Override
    public void before() {
        FileUtil.mkdir(workspace);
        log.info("[DockerImageBuildTask] 开始执行");
    }

    public static DockerImageBuildTask create(DockerClient dockerClient, String imageFullName, String workspace) {
        DockerImage dockerImage = DockerImage.builder().dockerClient(dockerClient).imageFullName(imageFullName).build();
        return new DockerImageBuildTask(dockerImage, workspace);
    }
}
