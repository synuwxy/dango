package com.synuwxy.dango.common.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DockerUtil {

    public final static String TYPE_JAVA = "java";
    public final static String TYPE_GO = "go";
    public final static String TYPE_NODEJS = "nodejs";
    public final static String TYPE_PYTHON = "python";

    private final static int VERSION = 1;

    public static void generatorDockerfile(String workspace, String type) {
        File target = new File(workspace + "/Dockerfile");
        String url = "static/dockerfile/" + type + "/Dockerfile";
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource resources = resolver.getResource(url);

        try {
            ClassPathResource resource = new ClassPathResource(url);
            InputStream inputStream = resource.getInputStream();
            FileCopyUtils.copy(inputStream, new FileOutputStream(target));
//            FileCopyUtils.copy(resources.getFile(), target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
