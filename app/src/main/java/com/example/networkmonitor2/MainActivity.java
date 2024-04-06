package com.example.networkmonitor2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ProgressBar loadingProgressBar;
    private boolean isLoading = false;
    private static final int LOADING_DELAY = 2000; // 5 seconds

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
            }
        });
    }

    private void showLoading() {
        isLoading = true;
        loadingProgressBar.setVisibility(View.VISIBLE);
        // Start the timer to hide loading after delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading();
            }
        }, LOADING_DELAY);
    }

    private void hideLoading() {
        isLoading = false;
        loadingProgressBar.setVisibility(View.GONE);
        startNextActivity();
    }

    private void startNextActivity() {
        Intent intent = new Intent(MainActivity.this, AllAppsListActivity.class);
        startActivity(intent);
        // Finish current activity if needed
        finish();
    }}