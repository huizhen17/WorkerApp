package com.example.workerapp;

public class OrderDetail {
    private String orderID;
    private String orderDate;
    private String orderTime;
    private String orderStatus;
    private String orderPrice;
    private String orderLatitude;
    private String orderLongitude;
    private String orderAddress;
    private String orderLink;

    public OrderDetail(String orderID, String orderDate, String orderTime, String orderStatus, String orderPrice, String orderLatitude, String orderLongitude, String orderAddress, String orderLink) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.orderPrice = orderPrice;
        this.orderLatitude = orderLatitude;
        this.orderLongitude = orderLongitude;
        this.orderAddress = orderAddress;
        this.orderLink = orderLink;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderLatitude() {
        return orderLatitude;
    }

    public void setOrderLatitude(String orderLatitude) {
        this.orderLatitude = orderLatitude;
    }

    public String getOrderLongitude() {
        return orderLongitude;
    }

    public void setOrderLongitude(String orderLongitude) {
        this.orderLongitude = orderLongitude;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderLink() {
        return orderLink;
    }

    public void setOrderLink(String orderLink) {
        this.orderLink = orderLink;
    }
}
