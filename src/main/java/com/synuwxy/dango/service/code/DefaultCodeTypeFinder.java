package com.synuwxy.dango.service.code;

import com.synuwxy.dango.service.code.maven.MavenCodeHandler;
import com.synuwxy.dango.service.code.model.CodeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author wxy
 */
@Component
@Slf4j
public class DefaultCodeTypeFinder implements CodeTypeFinder {

    private final CodeTypeRegister codeTypeRegister;

    public DefaultCodeTypeFinder(CodeTypeRegister codeTypeRegister) {
        this.codeTypeRegister = codeTypeRegister;
        codeTypeRegister.add(new MavenCodeHandler());
    }

    @Override
    public CodeType findCodeType(String codePath) {
        log.info("搜索代码类型");
        File root = new File(codePath);
        if (!root.exists() || !root.isDirectory()) {
            throw new RuntimeException("代码根目录不正确");
        }

        List<CodeTypeHandler> codeTypeHandlers =  codeTypeRegister.getCodeTypeHandlers();
        CodeTypeHandler codeTypeHandler = getCodeTypeHandler(root, codeTypeHandlers);
        if (null == codeTypeHandler) {
            return null;
        }
        return codeTypeHandler.generatorType(codePath);
    }

    private CodeTypeHandler getCodeTypeHandler(File file, List<CodeTypeHandler> codeTypeHandlers) {
        File[]  files = file.listFiles();
        if (null == files) {
            return null;
        }
        for (File codeFile:files) {
            if (codeFile.isFile()) {
                if (".".equals(codeFile.getName().substring(0,1))) {
                    // 默认跳过 "." 开头的文件
                    continue;
                }
                for (CodeTypeHandler codeTypeHandler : codeTypeHandlers) {
                    if (codeTypeHandler.confirm(codeFile)) {
                        return codeTypeHandler;
                    }
                }
            }
            else {
                if (".".equals(codeFile.getName().substring(0,1))) {
                    // 默认跳过 "." 开头的文件夹
                    continue;
                }
                // 搜索文件夹，若搜不到继续循环
                CodeTypeHandler codeTypeHandler = getCodeTypeHandler(codeFile, codeTypeHandlers);
                if (null != codeTypeHandler) {
                    // 如果搜到了，就直接跳出递归
                    return codeTypeHandler;
                }
            }
        }
        return null;
    }

}
