package com.synuwxy.dango.aggreate.docker.dockerfile;

import com.synuwxy.dango.common.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
@Slf4j
public class DockerfileRepository {

    private final String path;

    public DockerfileRepository(String path) {
        this.path = path;
    }

    public void createDockerfile(Dockerfile dockerfile) {
        String dockerfileParentPath = path + "/" + dockerfile.getType();
        FileUtil.mkdir(dockerfileParentPath);
        FileUtil.writeFile(dockerfileParentPath + "/Dockerfile", dockerfile.getContext());
    }
}
