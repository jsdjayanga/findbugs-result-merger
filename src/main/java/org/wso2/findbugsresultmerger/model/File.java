package org.wso2.findbugsresultmerger.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jayanga on 6/16/17.
 */
public class File {
    private String name;
    private List<Record> recordList = new ArrayList<>();

    public File(String name) {
        this.name = name;
    }

    public void addRecord(Record record) {
        int index = recordList.indexOf(record);
        if (index == -1) {
            recordList.add(record);
        } else {
            Record exising = recordList.get(index);
            if (exising.getRecordStatus() == Record.RecordStatus.KNOWN) {
                exising.setRecordStatus(Record.RecordStatus.KNOWN_RECURRENT);
            }
        }
    }

    public List<Record> getRecordList() {
        return Collections.unmodifiableList(recordList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
