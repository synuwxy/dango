package com.synuwxy.dango.aggreate.task.docker.dockerfile;

import com.synuwxy.dango.aggreate.task.Task;
import com.synuwxy.dango.common.utils.FileUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxy
 */
@Slf4j
@Builder
public class CreateDockerfileTask implements Task {

    private final String workspace;
    private final String type;
    private final String context;

    @Override
    public void run() {
        String dockerfileParentPath = workspace + "/" + type;
        FileUtil.mkdir(dockerfileParentPath);
        FileUtil.writeFile(dockerfileParentPath + "/Dockerfile", context);
    }
}
