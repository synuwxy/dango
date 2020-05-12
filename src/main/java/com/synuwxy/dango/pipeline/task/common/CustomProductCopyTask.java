package com.synuwxy.dango.pipeline.task.common;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.pipeline.PipelineParameterVerification;
import com.synuwxy.dango.pipeline.PipelineTask;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
@Slf4j
@AllArgsConstructor
public class CustomProductCopyTask implements PipelineTask {

    private final String sourceName;
    private final String workspace;
    private final String productPath;
    private final List<String> extraPaths;
    private final String targetPath;

    @Override
    public void process() throws IOException {
        String productName = productPath.substring(productPath.lastIndexOf("/") == -1?0:productPath.lastIndexOf("/"));
        FileCopyUtils.copy(new File(workspace + "/" + productPath), new File(targetPath + "/" + productName));
        for (String extraPath : extraPaths) {
            String name = extraPath.substring(extraPath.lastIndexOf("/") == -1 ? 0:extraPath.lastIndexOf("/"));
            File src = new File(workspace + "/" + sourceName + "/" + extraPath);
            if (!src.exists()) {
                continue;
            }
            if (src.isDirectory()) {
                FileUtils.copyDirectoryToDirectory(src, new File(targetPath));
            } else {
                FileUtils.copyFile(src, new File(targetPath + "/" + name));
            }
        }
    }

    @Override
    public void before() {
        FileUtil.mkdir(targetPath);
        log.info("[FileCopyTask] 开始执行");
    }

    public static CustomProductCopyTask create(String sourceName, String workspace, String productPath, List<String> extraPaths, String targetPath) {
        if (null == extraPaths) {
            extraPaths = new ArrayList<>();
        }
        return new CustomProductCopyTask(sourceName, workspace, productPath, extraPaths, targetPath);
    }

}
