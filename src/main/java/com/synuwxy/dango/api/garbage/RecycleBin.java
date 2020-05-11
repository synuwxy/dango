package com.synuwxy.dango.api.garbage;

import com.synuwxy.dango.common.utils.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
public class RecycleBin {

    private final List<String> fileBin = new ArrayList<>();

    public void addPathToDirectoriesBin(String path) {
        fileBin.add(path);
    }

    public void removePathToDirectoriesBin(String path) {
        fileBin.remove(path);
    }

    public synchronized void cleanDirectoriesBin() throws IOException {
        for (String path:fileBin) {
            FileUtil.delete(path);
        }
        fileBin.clear();
    }
}
