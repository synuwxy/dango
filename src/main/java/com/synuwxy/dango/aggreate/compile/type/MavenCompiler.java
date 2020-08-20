package com.synuwxy.dango.aggreate.compile.type;

import com.synuwxy.dango.aggreate.code.Language;
import com.synuwxy.dango.aggreate.compile.Compiler;

import java.io.File;

/**
 * @author wxy
 */
public class MavenCompiler implements Compiler {

    @Override
    public boolean belongs(Language language) {
        return language.equals(Language.JAVA);
    }

    @Override
    public boolean confirm(String root) {
        File dir = new File(root);
        if (!dir.isDirectory()) {
            return false;
        }
        File[] listFiles = dir.listFiles();
        if (null == listFiles) {
            return false;
        }

        String keyName = "pom.xml";
        for (File file : listFiles) {
            if (file.isDirectory()) {
                continue;
            }
            if (keyName.equals(file.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void compile() {

    }
}
