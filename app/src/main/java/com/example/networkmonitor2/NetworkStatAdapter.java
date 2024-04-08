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
            long appUsage = getAppUsage(uid);
            Log.d("Usage","Package Name: "+appName+"Usage: "+appUsage);
            Drawable appIcon = getAppIcon(packageName);

            holder.appIcon.setImageDrawable(appIcon);
            holder.packageName.setText(appName);
            holder.networkUsage.setText("App Usage: " + appUsage + " bytes");
        }
    }
    private long getAppUsage(int uid) {
        long endTime=System.currentTimeMillis();
        long startTime=endTime-(7*60*60);
        Log.d("NetworkStats", "Start Time: " + startTime + ", End Time: " + endTime);
        NetworkStats networkStats;
        try {
            networkStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_WIFI, "", startTime, endTime);
            NetworkStats.Bucket bucket = new NetworkStats.Bucket();
            Log.d("Bool: ", "getAppUsage:"+networkStats.hasNextBucket());
            while (networkStats.hasNextBucket()){
                networkStats.getNextBucket(bucket);
                Log.d("Uid: ","Bucket UID: " + bucket.getUid()+" UID: "+uid+"Transmission: "+(bucket.getRxBytes() + bucket.getTxBytes()));
                if (bucket.getUid() == uid) {
                    return bucket.getRxBytes() + bucket.getTxBytes();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
//        long ReceivedBytes=getPackageRxBytesWifi(networkStatsManager,uid,0,System.currentTimeMillis());
//        long TransmittedBytes=getPackageRxBytesWifi(networkStatsManager,uid,0,System.currentTimeMillis());
//        Log.d("MYLOG", String.valueOf(ReceivedBytes));
//        Log.d("MYLOG", String.valueOf(TransmittedBytes));
//        return ReceivedBytes+TransmittedBytes;
    }
    public long getPackageRxBytesWifi(NetworkStatsManager networkStatsManager,int packageUid,long startTime,long endTime) {
        NetworkStats networkStats = null;
        networkStats = networkStatsManager.queryDetailsForUid(
                ConnectivityManager.TYPE_WIFI,
                "",
                startTime,
                endTime,
                packageUid);
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        networkStats.getNextBucket(bucket);
        return bucket.getRxBytes();
    }
    public long getPackageTxBytesWifi(NetworkStatsManager networkStatsManager,int packageUid,long startTime, long endTime) {
        NetworkStats networkStats = null;
        networkStats = networkStatsManager.queryDetailsForUid(
                ConnectivityManager.TYPE_WIFI,
                "",
                startTime,
                endTime,
                packageUid);
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        networkStats.getNextBucket(bucket);
        return bucket.getTxBytes();
    }
    private long getStartOfDayInMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.appIcon);
            packageName = itemView.findViewById(R.id.packageName);
            networkUsage = itemView.findViewById(R.id.networkUsage);
        }
    }
}
