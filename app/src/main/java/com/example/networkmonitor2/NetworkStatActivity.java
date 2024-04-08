package com.example.networkmonitor2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;

import org.parceler.Parcels;

import java.util.List;

public class NetworkStatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NetworkStatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_stat);
        Intent intent=getIntent();
        Parcelable app_parcelable=intent.getParcelableExtra("Selected Apps");
        List<SelectedApps> s_app= Parcels.unwrap(app_parcelable);
        for(SelectedApps e:s_app){
            Log.e("Selected apps",e.getPackage_name());
        }
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (!isAccessGranted()) {
            Intent intent2 = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent2);
        }
        adapter = new NetworkStatAdapter(this, s_app);
        recyclerView.setAdapter(adapter);
    }
    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        applicationInfo.uid, applicationInfo.packageName);
            }
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}