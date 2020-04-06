package com.synuwxy.dango.api.script;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author wxy
 */
@Service
@Slf4j
public class SpringBootBuilder implements ScriptBuilder {

    private final ScriptHandler scriptHandler;

    public SpringBootBuilder(ScriptHandler scriptHandler) {
        this.scriptHandler = scriptHandler;
    }

    @Override
    public File build(String workspace) {
        log.info("开始构建 type: StringBoot");
        String command = "mvn clean package -Dmaven.test.skip=true";
        if (!scriptHandler.run(workspace, command)) {
            throw new RuntimeException("构建失败 type: SpringBoot");
        }
        return findJar(workspace);
    }

    private File findJar(String src) {
        src +=  "/target";
        log.info("搜索jar包 workspace: {}", src);
        Collection<File> collection = FileUtils.listFiles(new File(src), new String[] {"jar"}, false);
        if (collection.isEmpty()) {
            throw new RuntimeException("构建失败 type: SpringBoot, message: jar包不存在");
        }
        return new ArrayList<>(collection).get(0);
    }
}
