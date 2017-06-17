package org.wso2.findbugsresultmerger.model;

/**
 * Created by jayanga on 6/16/17.
 */
public class Record {
    private String vulnerability;
    private int line;
    private String severity;
    private String teamVerdict;
    private String justification;
    private String securityTeamFeedback;
    private boolean existing;
    private RecordStatus recordStatus;

    public enum RecordStatus {
        KNOWN,
        KNOWN_RECURRENT,
        NEW,
    }

    public Record(String vulnerability, int line, String severity, RecordStatus recordStatus) {
        this.vulnerability = vulnerability;
        this.severity = severity;
        this.line = line;
        this.existing = false;
        this.recordStatus = recordStatus;
    }

    public Record(String vulnerability, int line, String severity, String teamVerdict, String justification,
                  String securityTeamFeedback, RecordStatus recordStatus) {
        this.vulnerability = vulnerability;
        this.line = line;
        this.severity = severity;
        this.teamVerdict = teamVerdict;
        this.justification = justification;
        this.securityTeamFeedback = securityTeamFeedback;
        this.existing = false;
        this.recordStatus = recordStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (line != record.line) return false;
        return vulnerability.equals(record.vulnerability);
    }

    @Override
    public int hashCode() {
        int result = vulnerability.hashCode();
        result = 31 * result + line;
        return result;
    }

    public String getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(String vulnerability) {
        this.vulnerability = vulnerability;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getTeamVerdict() {
        return teamVerdict;
    }

    public void setTeamVerdict(String teamVerdict) {
        this.teamVerdict = teamVerdict;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public boolean isExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }

    public String getSecurityTeamFeedback() {
        return securityTeamFeedback;
    }

    public void setSecurityTeamFeedback(String securityTeamFeedback) {
        this.securityTeamFeedback = securityTeamFeedback;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }
}
