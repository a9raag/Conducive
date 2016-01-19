package com.example.anurag.conducive;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.anurag.conducive.Service.ServiceFloating;

public class LaunchActivity extends Activity {
    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        startService(new Intent(LaunchActivity.this, ServiceFloating.class));

        Button launch = (Button)findViewById(R.id.button1);
        launch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startService(new Intent(LaunchActivity.this, ServiceFloating.class));
                Toast.makeText(LaunchActivity.this, "Launched", Toast.LENGTH_SHORT).show();
            }
        });

        Button stop = (Button)findViewById(R.id.button2);
        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopService(new Intent(LaunchActivity.this, ServiceFloating.class));
            }
        });

        Button config = (Button)findViewById(R.id.button_config);
        config.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                Intent intent = new Intent(LaunchActivity.this, Configurations.class);
//                startActivity(intent);
//                stopService(new Intent(LaunchActivity.this, ServiceFloating.class));
            }
        });
        checkPermission();

    }


    @TargetApi(Build.VERSION_CODES.M)
    public void checkPermission() {
        if (!Settings.canDrawOverlays(LaunchActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        }

    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // SYSTEM_ALERT_WINDOW permission not granted...
            }
        }
    }

    @Override
    protected void onResume() {
        startService(new Intent(LaunchActivity.this, ServiceFloating.class));
        super.onResume();
    }

}
