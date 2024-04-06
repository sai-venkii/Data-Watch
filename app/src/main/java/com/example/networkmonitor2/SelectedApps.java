package com.example.networkmonitor2;

import org.parceler.Parcel;

@Parcel
public class SelectedApps {
    String package_name;
    int uid;
    public SelectedApps(){}
    public SelectedApps(String package_name,int uid){
        this.package_name=package_name;
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
