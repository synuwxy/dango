package com.synuwxy.dango.pipeline.task.code;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.aggreate.script.Script;
import com.synuwxy.dango.api.code.CodeTypeFinder;
import com.synuwxy.dango.api.code.model.CodeType;
import com.synuwxy.dango.common.utils.FileUtil;
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
public class DefaultBuildProductTask implements PipelineTask {
    private final CodeTypeFinder codeTypeFinder;
    private final String sourcePath;
    private final String targetPath;
    @Override
    public void process() throws IOException {
        CodeType codeType = codeTypeFinder.findCodeType(sourcePath);
        Script script = Script.builder().command(codeType.getBuildCommand()).build();
        if (!script.run(sourcePath)) {
            throw new RuntimeException("[DefaultBuildProductTask] 编译失败");
        }

        String productParentPath = codeType.getProductParentPath();
        String productName = codeType.getProductName();
        File src = new File(productParentPath + "/" + productName);
        File target = new File(targetPath + "/" + productName);
        FileUtils.copyFile(src, target);
    }

    @Override
    public void before() {
        FileUtil.mkdir(targetPath);
        log.info("[DefaultBuildProductTask] 开始执行");
    }

    public static DefaultBuildProductTask create(CodeTypeFinder codeTypeFinder, String sourcePath, String targetPath) {
        return new DefaultBuildProductTask(codeTypeFinder, sourcePath, targetPath);
    }
}
