package com.example.networkmonitor2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllAppsListActivity extends AppCompatActivity {
    private ListView listView;
    PackageManager pm;
    private ListAppAdapter adapter;
    List<AppInfo> installedApps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps_list);
        installedApps = getInstalledApps();
        listView=findViewById(R.id.installed_apps_list);
        adapter = new ListAppAdapter(this,0, installedApps);
        listView.setAdapter(adapter);
        ImageView tickIcon=(ImageView) findViewById(R.id.tick_icon);
        tickIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected apps from adapter
                List<SelectedApps> selectedApps = adapter.getSelectedApps();
                Parcelable Selected_apps_parcelable= Parcels.wrap(selectedApps);
                Intent intent=new Intent(getApplicationContext(),NetworkStatActivity.class);
                intent.putExtra("Selected Apps",Selected_apps_parcelable);
                startActivity(intent);
            }
        });
    }

    private List<AppInfo> getInstalledApps() {
        List<AppInfo> installedApps = new ArrayList<>();
        pm=getPackageManager();
        List<ApplicationInfo> packageList = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo appInfo : packageList) {
            String appName = appInfo.loadLabel(pm).toString();
            String packageName = appInfo.packageName;
            int uid = appInfo.uid;
            Drawable appIcon = appInfo.loadIcon(pm);
            installedApps.add(new AppInfo(appName, packageName, appIcon,uid));
        }
        Collections.sort(installedApps);

        return installedApps;
    }

}