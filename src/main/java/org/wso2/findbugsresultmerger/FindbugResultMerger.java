package org.wso2.findbugsresultmerger;

import org.wso2.findbugsresultmerger.model.Component;
import org.wso2.findbugsresultmerger.model.File;
import org.wso2.findbugsresultmerger.model.Record;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by jayanga on 6/17/17.
 */
public class FindbugResultMerger {
    private Map<String, Component> componentMap = new HashMap<String, Component>();


    public void loadMainResult(Path path) throws IOException {
        List<String> mainContent = null;
        try {
            mainContent = Files.readAllLines(path);
        } catch (IOException e) {
            Utils.log("Error while reading the file : " + path.toFile().getAbsolutePath());
            throw e;
        }

        Utils.log(Integer.toString(mainContent.size()));

        for (String line : mainContent) {
            String[] tokens = line.split("\\|", -1);

            Component component = getComponent(tokens[3]);
            File file = component.getFile(tokens[1].substring(0, tokens[1].indexOf(" ")));
            Record record = new Record(tokens[0],
                    getLineNumber(tokens[1].substring(tokens[1].lastIndexOf(" "), tokens[1].length() - 1).trim()),
                    tokens[2],
                    tokens[4].trim(),
                    tokens[5].trim(),
                    tokens[6].trim(),
                    Record.RecordStatus.KNOWN);
            file.addRecord(record);
        }
    }

    public void loadNewResult(Path path) throws IOException {
        List<String> newContent = null;
        try {
            newContent = Files.readAllLines(path);
        } catch (IOException e) {
            Utils.log("Error while reading the file : " + path.toFile().getAbsolutePath());
            throw e;
        }

        Utils.log(Integer.toString(newContent.size()));

        for (String line : newContent) {
            String[] tokens = line.split("\\|", -1);
            Component component = getComponent(tokens[3].substring(tokens[3].indexOf("[") + 1, tokens[3].lastIndexOf("]")));
            File file = component.getFile(tokens[1].substring(tokens[1].indexOf(" ") + 1, tokens[1].indexOf(":")));
            Record record = new Record(tokens[0].trim(),
                    getLineNumber(tokens[1].substring(tokens[1].lastIndexOf(" "), tokens[1].lastIndexOf("]")).trim()),
                    getSeverty(tokens[2]), Record.RecordStatus.NEW);
            file.addRecord(record);
        }
    }

    private String getSeverty(String value) {
        switch (value) {
            case "1":
                return "High";
            case "2":
                return "Medium";
            default:
                return "UNDEFINED";
        }
    }

    private int getLineNumber(String record) {
        if (record.contains("-")) {
            return Integer.parseInt(record.substring(0, record.indexOf("-")));
        } else {
            return Integer.parseInt(record);
        }
    }

    private Component getComponent(String componentName) {
        Component component = componentMap.get(componentName);
        if (component == null) {
            component = new Component(componentName);
            componentMap.put(componentName, component);
        }
        return component;
    }

    public void print() {
        int lineNumber = 0;
        for (Map.Entry<String, Component> componentEntry : componentMap.entrySet()) {
            for (final Map.Entry<String, File> fileEntry : componentEntry.getValue().getFiles().entrySet()) {
                for (Record record : fileEntry.getValue().getRecordList()) {
                    Utils.log(Integer.toString(lineNumber++) + " : " + record.getVulnerability() + "|" + fileEntry.getValue().getName());
                }
            }
        }
    }

    public List<String> getVulnerabilityList() {
        List<String> vulnerabilityList = new ArrayList<>();
        for (Map.Entry<String, Component> componentEntry : componentMap.entrySet()) {
            for (final Map.Entry<String, File> fileEntry : componentEntry.getValue().getFiles().entrySet()) {
                for (Record record : fileEntry.getValue().getRecordList()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(record.getVulnerability());
                    stringBuilder.append("|");
                    stringBuilder.append(fileEntry.getValue().getName());
                    stringBuilder.append(" [line ");
                    stringBuilder.append(record.getLine());
                    stringBuilder.append("]|");
                    stringBuilder.append(record.getSeverity());
                    stringBuilder.append("|");
                    stringBuilder.append(componentEntry.getValue().getName());
                    stringBuilder.append("|");
                    stringBuilder.append(record.getTeamVerdict());
                    stringBuilder.append("|");
                    stringBuilder.append(record.getJustification());
                    stringBuilder.append("|");
                    stringBuilder.append(record.getSecurityTeamFeedback());
                    stringBuilder.append("|");
                    stringBuilder.append(record.getRecordStatus().toString());

                    vulnerabilityList.add(stringBuilder.toString());
                }
            }
        }
        Collections.sort(vulnerabilityList);
        return vulnerabilityList;
    }

//    private String getStatus(Record record) {
//        if (record.isExisting() == false) {
//            return "FIXED";
//        } else if (record.isExisting() == false) {
//
//        }
//    }
}
