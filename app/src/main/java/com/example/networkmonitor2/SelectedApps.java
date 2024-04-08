package com.example.networkmonitor2;

import org.parceler.Parcel;

@Parcel
public class SelectedApps {
    String package_name;
    String app_Name;
    int uid;
    public SelectedApps(){}

    public String getApp_Name() {
        return app_Name;
    }

    public void setApp_Name(String app_Name) {
        this.app_Name = app_Name;
    }

    public SelectedApps(String package_name, String app_Name, int uid){
        this.package_name=package_name;
        this.app_Name=app_Name;
        this.uid=uid;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
