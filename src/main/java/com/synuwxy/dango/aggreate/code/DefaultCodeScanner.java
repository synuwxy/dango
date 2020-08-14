package com.synuwxy.dango.aggreate.code;

import org.apache.commons.codec.language.bm.Lang;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wxy
 */
public class DefaultCodeScanner implements CodeScanner {
    /**
     * 已注册的代码语言类型
     */
    private final Map<Language, String> languageMap = new HashMap<>();
    /**
     * 扩展名集合
     */
    private final Map<String, Integer> extensionMap = new HashMap<>();

    public void register(Language language, String extension) {
        languageMap.put(language, extension);
    }

    @Override
    public CodeAnalysisResult analysis(String path) {
        CodeAnalysisResult codeAnalysisResult = new CodeAnalysisResult();
        File root = new File(path);
        collectExtension(root);
        Language lang = Language.OTHER;
        if (languageMap.isEmpty() || extensionMap.isEmpty()) {
            return codeAnalysisResult;
        }

        // 以扩展名最多的文件判断代码的类型
        for (Map.Entry<String, Integer> extensionEntry : extensionMap.entrySet()) {
            int sum = -1;
            for (Map.Entry<Language, String> languageEntry : languageMap.entrySet()) {
                if (extensionEntry.getKey().equals(languageEntry.getValue())) {
                    if (extensionEntry.getValue() > sum) {
                        sum = extensionEntry.getValue();
                        lang = languageEntry.getKey();
                    }
                }
            }
        }
        codeAnalysisResult.setLanguage(lang);
        return codeAnalysisResult;
    }

    private void collectExtension(File root) {
        File[] files = root.listFiles();
        if (null == files) {
            return;
        }
        for (File file:files) {
            String fileName = file.getName();
            int index = fileName.indexOf(".");
            if (file.isDirectory()) {
                if (0 == index) {
                    // 默认跳过 "." 开头的文件和没有扩展名的文件
                    continue;
                }
                collectExtension(file);
            }
            else {
                if (-1 == index || 0 == index) {
                    // 默认跳过 "." 开头的文件和没有扩展名的文件
                    continue;
                }
                int lastIndex = fileName.lastIndexOf(".");
                String extension = fileName.substring(lastIndex + 1);
                extensionMap.merge(extension, 1, Integer::sum);
            }
        }
    }
}
