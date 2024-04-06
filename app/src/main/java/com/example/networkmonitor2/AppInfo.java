package com.example.networkmonitor2;
import android.graphics.drawable.Drawable;


public class AppInfo {
    private String appName;
    private String packageName;
    private Drawable appIcon;
    private int uid;
    private boolean isSelected;

    public AppInfo(String appName, String packageName, Drawable appIcon, int uid) {
        this.appName = appName;
        this.packageName = packageName;
        this.appIcon = appIcon;
        this.uid = uid;
        this.isSelected=false;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public int getUid() {
        return uid;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
