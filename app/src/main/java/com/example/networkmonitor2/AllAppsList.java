package com.example.networkmonitor2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AllAppsList extends AppCompatActivity {

    private ListView listView;
    private ListAppAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps_list);
        List<AppInfo> installedApps = getInstalledApps();
        listView=findViewById(R.id.installed_apps_list);
        adapter = new ListAppAdapter(this,0, installedApps);
        listView.setAdapter(adapter);
    }
    private List<AppInfo> getInstalledApps() {
        List<AppInfo> installedApps = new ArrayList<>();
        List<PackageInfo> packageList = getPackageManager().getInstalledPackages(0);

        for (PackageInfo packageInfo : packageList) {
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            String appName = appInfo.loadLabel(getPackageManager()).toString();
            String packageName = packageInfo.packageName;
            int uid = appInfo.uid;
            Drawable appIcon = appInfo.loadIcon(getPackageManager());
            installedApps.add(new AppInfo(appName, packageName, appIcon,uid));
        }

        return installedApps;
    }

}