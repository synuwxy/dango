package com.synuwxy.dango.service.code.maven;

import com.synuwxy.dango.service.code.CodeTypeHandler;
import com.synuwxy.dango.service.code.model.CodeType;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author wxy
 */
@Slf4j
public class MavenCodeHandler implements CodeTypeHandler {

    private String root;

    @Override
    public boolean confirm(File file) {
        String target = "pom.xml";
        if (target.equals(file.getName())) {
            root = file.getParent();
            return true;
        }

        return false;
    }

    @Override
    public CodeType generatorType(String codePath) {
        File file = new File(codePath + "/pom.xml");
        if (!file.exists() && null != root) {
            // 防一手所谓的代码根目录下没有pom的情况，如果 confirm 方法判断了的话pom一定存在
            file = new File(root + "/pom.xml");
        }

        if (!file.exists()) {
            throw new RuntimeException("pom文件不存在");
        }

        try {
            Model pom = getPomModel(file);
            String name = getName();
            String productParentPath = getProductParentPath(codePath);
            String productName = getProductName(pom);
            String productExtensionName = getProductExtensionName(pom);
            String buildCommand = "mvn clean package -Dmaven.test.skip=true";
            return new CodeType(name, productParentPath, productName, productExtensionName, buildCommand);
        } catch (IOException | XmlPullParserException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private Model getPomModel(File pom) throws IOException, XmlPullParserException {
        FileInputStream fis = new FileInputStream(pom);
        MavenXpp3Reader reader = new MavenXpp3Reader();
        return reader.read(fis);
    }

    private String getName() {
        return "maven";
    }

    private String getProductParentPath(String codePath) {
        return codePath + "/target";
    }

    private String getProductName(Model pom) {
        Build build = pom.getBuild();
        if (null != build) {
            if (null != build.getFinalName()) {
                return build.getFinalName() + "." + getProductExtensionName(pom);
            }
        }
        String artifactId = pom.getArtifactId();
        String version = pom.getVersion();
        String packaging = getProductExtensionName(pom);

        return artifactId + "-" + version + "." + packaging;
    }

    private String getProductExtensionName(Model pom) {
        // 不支持其他打包类型，只做最基础的war包和jar包，复杂类型放到别的地方
        return "war".equals(pom.getPackaging()) ? "war" : "jar";
    }
}