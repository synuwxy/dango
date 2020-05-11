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

    public List<String> getDockerfileType() {
        List<String> types = new ArrayList<>();
        List<File> directories = FileUtil.getDirectories(path);
        if (directories.isEmpty()) {
            return types;
        }
        for (File directory : directories) {
            File dockerfile = new File(directory.getPath() + "/Dockerfile");
            if (dockerfile.exists()) {
                types.add(directory.getName());
            }
        }
        return types;
    }

    public void createDockerfile(Dockerfile dockerfile) {
        String dockerfileParentPath = path + "/" + dockerfile.getName();
        FileUtil.mkdir(dockerfileParentPath);
        FileUtil.writeFile(dockerfileParentPath + "/Dockerfile", dockerfile.getContext());
    }
}
