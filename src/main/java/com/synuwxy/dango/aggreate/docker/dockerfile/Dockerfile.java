package com.synuwxy.dango.aggreate.docker.dockerfile;

import com.synuwxy.dango.common.utils.FileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
@Data
@Builder
@Slf4j
public class Dockerfile {

    private String context;
    private String type;

    public void generatorDockerfile(String workspace, String targetPath) throws IOException {
        String filePath = workspace + "/" + type + "/Dockerfile";
        File dockerfile = new File(filePath);
        if (!dockerfile.exists()) {
            throw new RuntimeException("Dockerfile文件不存在 path: " + filePath);
        }
        FileUtil.mkdir(targetPath);
        File targetDockerfile = new File(targetPath + "/Dockerfile");
        FileCopyUtils.copy(dockerfile, targetDockerfile);
    }

    public List<String> getDockerfileType(String workspace) {
        List<File> directories = FileUtil.getDirectories(workspace);
        if (directories.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> types = new ArrayList<>();
        for (File directory : directories) {
            File dockerfile = new File(directory.getPath() + "/Dockerfile");
            if (dockerfile.exists()) {
                types.add(directory.getName());
            }
        }
        return types;
    }

    public void createDockerfile(String workspace) {
        String dockerfileParentPath = workspace + "/" + type;
        FileUtil.mkdir(dockerfileParentPath);
        FileUtil.writeFile(dockerfileParentPath + "/Dockerfile", context);
    }
}
