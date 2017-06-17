package org.wso2.findbugsresultmerger.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jayanga on 6/16/17.
 */
public class Component {
    private String name;
    private Map<String, File> fileMap = new HashMap<String, File>();

    public Component(String name) {
        this.name = name;
    }

    public File getFile(String filename) {
        File file = fileMap.get(filename);
        if (file == null) {
            file = new File(filename);
            fileMap.put(filename, file);
        }
        return file;
    }

    public Map<String, File> getFiles() {
        return Collections.unmodifiableMap(fileMap);
    }

    public String getName() {
        return name;
    }
}
