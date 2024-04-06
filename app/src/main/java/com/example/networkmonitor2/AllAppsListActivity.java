package com.example.networkmonitor2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllAppsListActivity extends AppCompatActivity {
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