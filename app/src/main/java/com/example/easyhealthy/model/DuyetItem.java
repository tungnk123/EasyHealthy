package com.example.easyhealthy.model;

public class DuyetItem {
    String title;
    int icon;

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

    public DuyetItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }
}
