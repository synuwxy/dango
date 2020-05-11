package com.synuwxy.dango.aggreate.docker.dockerfile;

import com.synuwxy.dango.common.utils.FileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author wxy
 */
@Data
@Builder
@Slf4j
public class Dockerfile {

    private String context;
    private String name;

    public void generatorDockerfile(String workspace, String targetPath) throws IOException {
        String filePath = workspace + "/" + name + "/Dockerfile";
        File dockerfile = new File(filePath);
        if (!dockerfile.exists()) {
            throw new RuntimeException("Dockerfile文件不存在 path: " + filePath);
        }
        FileUtil.mkdir(targetPath);
        File targetDockerfile = new File(targetPath + "/Dockerfile");
        FileCopyUtils.copy(dockerfile, targetDockerfile);
    }
}
