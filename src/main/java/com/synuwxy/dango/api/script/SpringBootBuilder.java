package com.synuwxy.dango.api.script;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.api.code.CodeTypeFinder;
import com.synuwxy.dango.api.code.model.CodeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author wxy
 */
@Service
@Slf4j
public class SpringBootBuilder implements ScriptBuilder {

    private final ScriptHandler scriptHandler;

    private final CodeTypeFinder codeTypeFinder;

    public SpringBootBuilder(ScriptHandler scriptHandler, CodeTypeFinder codeTypeFinder) {
        this.scriptHandler = scriptHandler;
        this.codeTypeFinder = codeTypeFinder;
    }

    @Override
    public File build(String workspace) {
        log.info("开始构建 type: StringBoot");
        String command = "mvn clean package -Dmaven.test.skip=true";
        if (!scriptHandler.run(workspace, command)) {
            throw new RuntimeException("构建失败 type: SpringBoot");
        }
        return findProduct(workspace);
    }

    @Override
    public File customBuild(String command, String productName, String productPath, String workspace) {
        log.info("自定义构建");
        if (!scriptHandler.run(workspace, command)) {
            throw new RuntimeException("构建命令执行失败");
        }
        String absolutePath = workspace + "/" + productPath + "/" + productName;
        File product = new File(absolutePath);
        if (!product.exists()) {
            throw new RuntimeException("构建物不存在，绝对路径: " + absolutePath);
        }
        return product;
    }

    private File findProduct(String src) {
        CodeType codeType = codeTypeFinder.findCodeType(src);
        log.info("产出物 codeType: {}", JSONObject.toJSONString(codeType));
        String productParentPath = codeType.getProductParentPath();
        String productName = codeType.getProductName();
        File product = new File(productParentPath + "/" + productName);
        if (!product.exists()) {
            throw new RuntimeException("编译产出物不存在");
        }
        return product;
    }
}
