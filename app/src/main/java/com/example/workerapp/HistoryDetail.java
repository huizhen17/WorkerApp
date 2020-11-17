package com.example.workerapp;

public class HistoryDetail {
    private String historyID;
    private String historyDateTime;
    private String historyCustomerID;

    public HistoryDetail(){
        this("","","");
    }

    public HistoryDetail(String historyID, String historyDateTime, String historyCustomerID) {
        this.historyID = historyID;
        this.historyDateTime = historyDateTime;
        this.historyCustomerID = historyCustomerID;
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

    public String getHistoryCustomerID() {
        return historyCustomerID;
    }

    public void setHistoryCustomerID(String historyCustomerID) {
        this.historyCustomerID = historyCustomerID;
    }
}
