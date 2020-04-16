package com.synuwxy.dango.api.docker;

import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
@Service
public class DockerfileServiceImpl implements DockerfileService {

    private final String workspace;

    public DockerfileServiceImpl(CommonConfig commonConfig) {
        this.workspace = commonConfig.getDockerfileWorkspace();
    }

    @Override
    public void generatorDockerfile(String directory, String type) throws IOException {
        String filePath = workspace + "/" + type + "/Dockerfile";
        File dockerfile = new File(filePath);
        if (!dockerfile.exists()) {
            throw new RuntimeException("Dockerfile文件不存在 path: " + filePath);
        }
        FileUtil.mkdir(directory);
        File targetDockerfile = new File(directory + "/Dockerfile");
        FileCopyUtils.copy(dockerfile, targetDockerfile);
    }

    @Override
    public List<String> getDockerfileType() {
        List<String> types = new ArrayList<>();
        List<File> directories = FileUtil.getDirectories(workspace);
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
}
