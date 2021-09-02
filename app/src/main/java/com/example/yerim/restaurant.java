package com.example.yerim;

public class restaurant {   // object to store restaurant information
    private String resName;
    private String resLocation;
    private String resKind;
    private String resPrice;
    private String resWho;
    private String resHost;

    public restaurant(String resName, String resLocation, String resKind,
                      String resPrice, String resWho, String resHost) {
        this.resName = resName;
        this.resLocation = resLocation;
        this.resKind = resKind;
        this.resPrice = resPrice;
        this.resWho = resWho;
        this.resHost = resHost;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResLocation() {
        return resLocation;
    }

    public void setResLocation(String resLocation) {
        this.resLocation = resLocation;
    }

    public String getResKind() {
        return resKind;
    }

    public void setResKind(String resKind) {
        this.resKind = resKind;
    }

    public String getResPrice() {
        return resPrice;
    }

    public void setResPrice(String resPrice) {
        this.resPrice = resPrice;
    }

    public String getResWho() {
        return resWho;
    }

    public void setResWho(String resWho) {
        this.resWho = resWho;
    }

    public String getResHost() {
        return resHost;
    }

    public void setResHost(String resHost) {
        this.resHost = resHost;
    }
}
