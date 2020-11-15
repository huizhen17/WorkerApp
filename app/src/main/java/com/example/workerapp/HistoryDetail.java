package com.example.workerapp;

public class HistoryDetail {
    private String historyID;
    private String historyDateTime;

    public HistoryDetail(){
        this("","");
    }

    public HistoryDetail(String historyID, String historyDateTime) {
        this.historyID = historyID;
        this.historyDateTime = historyDateTime;
    }

    public String getHistoryID() {
        return historyID;
    }

    public void setHistoryID(String historyID) {
        this.historyID = historyID;
    }

    public String getHistoryDateTime() {
        return historyDateTime;
    }

    public void setHistoryDateTime(String historyDateTime) {
        this.historyDateTime = historyDateTime;
    }
}
