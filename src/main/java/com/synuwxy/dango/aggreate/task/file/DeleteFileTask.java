package com.synuwxy.dango.aggreate.task.file;

import com.synuwxy.dango.aggreate.task.Task;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author wxy
 */
@Slf4j
@Builder
public class DeleteFileTask implements Task {

    private final String path;

    @Override
    public void run() throws Exception {
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
}
