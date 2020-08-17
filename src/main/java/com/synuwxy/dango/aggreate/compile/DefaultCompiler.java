package com.synuwxy.dango.aggreate.compile;

import com.synuwxy.dango.aggreate.code.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
public class DefaultCompiler {

    private Language language;

    private String root;

    private final List<Compiler> compilers = new ArrayList<>();

    public void register(Compiler compiler) {
        if (compiler.belongs(language)) {
            compilers.add(compiler);
        }
    }

    public void compile() {
        for (Compiler compiler : compilers) {
            if (compiler.confirm(root)) {
                compiler.compile();
                break;
            }
        }
    }
}
