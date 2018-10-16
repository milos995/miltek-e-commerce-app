package rs.fon.miltek.models;

import java.text.DecimalFormat;

public class Orders {
    private int orderID;
    private String city;
    private int zipCode;
    private String address;
    private String phone;
    private double inTotal;
    private String date;
    private int status;
    private DecimalFormat df = new DecimalFormat("#0.00");

    public Orders(int orderID, String city, int zipCode, String address, String phone, double inTotal, String date, int status) {
        this.orderID = orderID;
        this.city = city;
        this.zipCode = zipCode;
        this.address = address;
        this.phone = phone;
        this.inTotal = inTotal;
        this.date = date;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getInTotal() {
        return inTotal;
    }

    public String getInTotalS() {
        return df.format(inTotal)+" RSD";
    }

    public void setInTotal(double inTotal) {
        this.inTotal = inTotal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return zipCode+" - "+city+", "+address+", "+phone;
    }
}
