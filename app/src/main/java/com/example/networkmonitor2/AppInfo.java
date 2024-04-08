package com.example.networkmonitor2;
import android.graphics.drawable.Drawable;
import org.parceler.Parcel;
public class AppInfo implements Comparable<AppInfo>{
    String appName;
    String packageName;
    Drawable appIcon;
    int uid;
    boolean isSelected;
    public AppInfo(){
    }
    public AppInfo(String appName, String packageName, int uid) {
        this.appName = appName;
        this.packageName = packageName;
        this.uid = uid;
        this.isSelected=false;
    }
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
    @Override
    public int compareTo(AppInfo other) {
        // Implement comparison based on the name attribute
        return this.appName.compareTo(other.getAppName());
    }
}
