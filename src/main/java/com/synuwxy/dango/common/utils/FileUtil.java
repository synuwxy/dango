package com.synuwxy.dango.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wxy
 */
@Slf4j
public class FileUtil {

    public static void mkdir(String path) {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("创建执行文件夹失败");
            }
        }
    }

    public static void mkdirs(String... paths) {
        Arrays.asList(paths).forEach(path -> {
            File dir = new File(path);
            if (!dir.exists() || !dir.isDirectory()) {
                if (!dir.mkdirs()) {
                    throw new RuntimeException("创建执行文件夹失败");
                }
            }
        });
    }

    /**
     * 获取路径下所有的目录
     * @param path 路径
     * @return 目录列表
     */
    public static List<File> getDirectories(String path) {
        List<File> directories = new ArrayList<>();
        File source = new File(path);
        if (!source.exists() || !source.isDirectory()) {
            return directories;
        }
        File[] files = source.listFiles();
        if (null == files) {
            return directories;
        }
        for (File file: files) {
            if (file.isDirectory()) {
                directories.add(file);
            }
        }
        return directories;
    }

    public static boolean assertionExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                FileUtil.mkdir(file.getParent());
                if (!file.createNewFile()) {
                    return true;
                }
            } catch (Exception e) {
                log.error("文件创建失败 path: {}, message: {}", path, e.getMessage());
                return false;
            }
        }
        return false;
    }

    public static void delete(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            FileUtils.deleteDirectory(file);
        } else {
            file.deleteOnExit();
        }
    }

    public static boolean writeFile(String filePath, String context) {
        File file = new File(filePath);
        log.info("写入文件 [path: {}, context: {}]", filePath, context);
        if (FileUtil.assertionExists(filePath)) {
            log.error("文件校验失败 file name {}", file.getName());
            return false;
        }

        try (PrintWriter printWriter = new PrintWriter(file)){
            if (null == context) {
                context = "";
            }
            printWriter.print(context);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
