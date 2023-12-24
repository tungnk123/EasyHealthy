package com.example.easyhealthy.model;

public class HoatDongData {
    private String title;
    private int icon;
    private int number;

    private String donViDo;

    public HoatDongData(String title, int icon, int number, String donViDo) {
        this.title = title;
        this.icon = icon;
        this.number = number;
        this.donViDo = donViDo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDonViDo() {
        return donViDo;
    }

    public void setDonViDo(String donViDo) {
        this.donViDo = donViDo;
    }
}
