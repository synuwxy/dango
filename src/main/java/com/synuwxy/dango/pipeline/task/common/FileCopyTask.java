package com.synuwxy.dango.pipeline.task.common;

import com.synuwxy.dango.pipeline.PipelineParameterVerification;
import com.synuwxy.dango.pipeline.PipelineTask;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author wxy
 */
@Slf4j
@AllArgsConstructor
public class FileCopyTask implements PipelineTask {

    private final String sourcePath;
    private final String targetPath;

    @Override
    public void process() throws IOException {
        File src = new File(sourcePath);
        if (src.isDirectory()) {
            File target = new File(targetPath);
            FileUtils.copyDirectoryToDirectory(src, target);
        } else {
            File target = new File(targetPath + "/" + src.getName());
            FileUtils.copyFile(src, target);
        }
    }

    @Override
    public void before() {
        PipelineParameterVerification pipelineParameterVerification = PipelineParameterVerification.create();
        if (null == sourcePath) {
            pipelineParameterVerification.add("[FileCopyTask 参数校验] sourcePath: null");
        }
        if (null == targetPath) {
            pipelineParameterVerification.add("[FileCopyTask 参数校验] targetPath: null");
        }
        pipelineParameterVerification.verify();
        log.info("[FileCopyTask] 开始执行");
    }

    public static FileCopyTask create(String sourcePath, String targetPath) {
        return new FileCopyTask(sourcePath, targetPath);
    }

}
