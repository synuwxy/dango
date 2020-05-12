package com.synuwxy.dango.pipeline.task.docker;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.aggreate.docker.dockerfile.Dockerfile;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.pipeline.PipelineTask;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxy
 */
@Slf4j
@AllArgsConstructor
public class DockerfileGeneratorTask implements PipelineTask {

    private final Dockerfile dockerfile;
    private final String workspace;
    private final String targetPath;

    @Override
    public void process() throws Exception {
        dockerfile.generatorDockerfile(workspace, targetPath);
    }

    @Override
    public void before() {
        FileUtil.mkdir(workspace);
        log.info("[DockerfileGeneratorTask] 开始执行");
    }

    public static DockerfileGeneratorTask create(String name, String workspace, String targetPath) {
        Dockerfile dockerfile = Dockerfile.builder().name(name).build();
        return new DockerfileGeneratorTask(dockerfile, workspace, targetPath);
    }
}
