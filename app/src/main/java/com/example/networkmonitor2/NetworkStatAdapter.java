package com.example.networkmonitor2;

import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class NetworkStatAdapter extends RecyclerView.Adapter<NetworkStatAdapter.ViewHolder> {
    private Context context;
    private List<SelectedApps> selectedApps;
    private PackageManager packageManager;
    private NetworkStatsManager networkStatsManager;
    public NetworkStatAdapter(Context context, List<SelectedApps> selectedApps) {
        this.context = context;
        this.selectedApps = selectedApps;
        this.packageManager = context.getPackageManager();
        this.networkStatsManager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_network_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectedApps app = selectedApps.get(position);
        String packageName = app.getPackage_name();
        String appName=app.getApp_Name();
        int uid = app.getUid();
        Log.d("Package Name: ",appName);
        if (uid != -1) {
            long endTime=System.currentTimeMillis();
            long startTime=endTime-(7 * 24 * 60 * 60 * 1000);
            long wifiUsage = getWifiUsage(uid,startTime,endTime);
            long mobileUsage = getMobileUsage(uid,startTime,endTime);
            long totalAppUsage=wifiUsage+mobileUsage;
            Drawable appIcon = getAppIcon(packageName);

            holder.appIcon.setImageDrawable(appIcon);
            holder.packageName.setText(appName);
            holder.networkUsage.setText("App Usage: " + formatFileSize(totalAppUsage));
//            holder.wifiUsage.setText("Wifi: "+formatFileSize(wifiUsage));
//            holder.mobileUsage.setText("Mobile: "+formatFileSize(mobileUsage));
        }
    }
    private long getWifiUsage(int uid,long startTime,long endTime){
        long wifiUsage=0;
        NetworkStats networkStats;
        try {
            networkStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_WIFI, "", startTime, endTime);
            NetworkStats.Bucket bucket = new NetworkStats.Bucket();
            Log.d("Bool: ", "getAppUsage:"+networkStats.hasNextBucket());
            while (networkStats.hasNextBucket()){
                networkStats.getNextBucket(bucket);
                Log.d("Uid: ","Bucket UID: " + bucket.getUid()+" UID: "+uid+"Transmission: "+(bucket.getRxBytes() + bucket.getTxBytes()));
                if (bucket.getUid() == uid) {
                    wifiUsage+= bucket.getRxBytes() + bucket.getTxBytes();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return wifiUsage;
    }
    private long getMobileUsage(int uid,long startTime,long endTime){
        long mobileUsage=0;
        NetworkStats networkStats;
        try {
            networkStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_MOBILE, "", startTime, endTime);
            NetworkStats.Bucket bucket = new NetworkStats.Bucket();
            Log.d("Bool MobileNetworkStats", "" +networkStats.hasNextBucket());
            while (networkStats.hasNextBucket()){
                networkStats.getNextBucket(bucket);
                Log.d("Uid: ","Bucket UID: " + bucket.getUid()+" UID: "+uid+"Transmission: "+(bucket.getRxBytes() + bucket.getTxBytes()));
                if (bucket.getUid() == uid) {
                    mobileUsage+= bucket.getRxBytes() + bucket.getTxBytes();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return mobileUsage;
    }

    private String formatFileSize(long sizeInBytes) {
        final long KB = 1024;
        final long MB = KB * 1024;
        final long GB = MB * 1024;

        if (sizeInBytes >= GB) {
            return String.format("%.2f GB", (double) sizeInBytes / GB);
        } else if (sizeInBytes >= MB) {
            return String.format("%.2f MB", (double) sizeInBytes / MB);
        } else if (sizeInBytes >= KB) {
            return String.format("%.2f KB", (double) sizeInBytes / KB);
        } else {
            return sizeInBytes + " B";
        }
    }
    @Override
    public int getItemCount() {
        return selectedApps.size();
    }

    private Drawable getAppIcon(String packageName) {
        try {
            return packageManager.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView packageName;
        TextView networkUsage;
        TextView wifiUsage;
        TextView mobileUsage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.appIcon);
            packageName = itemView.findViewById(R.id.packageName);
            networkUsage = itemView.findViewById(R.id.networkUsage);
//            wifiUsage=itemView.findViewById(R.id.Wifi);
//            mobileUsage=itemView.findViewById(R.id.MobileData);
        }
    }
}
