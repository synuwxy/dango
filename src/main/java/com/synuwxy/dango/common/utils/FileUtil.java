package com.synuwxy.dango.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

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
            if (!dir.setWritable(true, false)) {
                throw new RuntimeException("设置文件夹权限失败");
            }
        }
    }

    public static boolean assertionExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                FileUtil.mkdir(file.getParent());
                if (!file.createNewFile()) {
                    return false;
                }
            } catch (Exception e) {
                log.error("文件创建失败 path: {}, message: {}", path, e.getMessage());
                return false;
            }
        }
        return true;
    }

    public static void delete(String path) throws IOException {
        File file = new File(path);
        FileUtils.deleteDirectory(file);
    }
}
