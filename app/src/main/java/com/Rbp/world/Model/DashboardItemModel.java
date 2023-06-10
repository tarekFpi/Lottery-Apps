package com.Rbp.world.Model;

public class DashboardItemModel {
    private int dashImage;

    /* renamed from: id */
    private int f601id;
    private String title;

    public DashboardItemModel(int i, String str, int i2) {
        this.dashImage = i;
        this.title = str;
        this.f601id = i2;
    }

    public int getDashImage() {
        return this.dashImage;
    }

    public void setDashImage(int i) {
        this.dashImage = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public int getId() {
        return this.f601id;
    }

    public void setId(int i) {
        this.f601id = i;
    }
}
