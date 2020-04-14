package com.synuwxy.dango.common.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wxy
 */
public class DockerUtil {

    public final static String TYPE_MAVEN = "maven";

    public static void generatorDockerfile(String workspace, String type) {
        File target = new File(workspace + "/Dockerfile");
        String url = "static/dockerfile/" + type + "/Dockerfile";
        try {
            ClassPathResource resource = new ClassPathResource(url);
            InputStream inputStream = resource.getInputStream();
            FileCopyUtils.copy(inputStream, new FileOutputStream(target));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
