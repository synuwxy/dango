package com.synuwxy.dango.pipeline.task.code;

import com.synuwxy.dango.api.code.CodeTypeFinder;
import com.synuwxy.dango.api.code.model.CodeType;
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
public class MoveProductTask implements PipelineTask {
    private final CodeTypeFinder codeTypeFinder;
    private final String sourcePath;
    private final String targetPath;
    @Override
    public void process() throws IOException {
        CodeType codeType = codeTypeFinder.findCodeType(sourcePath);
        String productParentPath = codeType.getProductParentPath();
        String productName = codeType.getProductName();
        File src = new File(productParentPath + "/" + productName);
        File target = new File(targetPath + "/" + productName);
        FileUtils.copyFile(src, target);
    }

    public static MoveProductTask create(CodeTypeFinder codeTypeFinder, String sourcePath, String targetPath) {
        return new MoveProductTask(codeTypeFinder, sourcePath, targetPath);
    }
}
