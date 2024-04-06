package com.example.networkmonitor2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import org.parceler.Parcels;

import java.util.List;

public class NetworkStatActivity extends AppCompatActivity {

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
    }
}