package edu.neu.madcourse.numad21sp_weihanliu;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class Locator extends AppCompatActivity implements LocationListener {

    TextView display;
    LocationManager locationManager;
    boolean isGPSEnable;
    boolean canGetLocation;
    Location location;
    public static final int REQUEST_LOCATION_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locator);
        display = findViewById(R.id.location_display);
        display.setVisibility(View.INVISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        getPermission();
        setLocationText();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLocationText();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_CODE) {
            setLocationText();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        setLocationText();
    }

    private void getPermission() {
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!isGPSEnable) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Locator.this);
            builder.setMessage(R.string.gps_not_enabled)
                    .setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.create();
            builder.show();
        }
        canGetLocation = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (!canGetLocation) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_CODE);
        }
    }

    private void setLocationText() {
        canGetLocation = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnable && canGetLocation) {
            if(location == null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000, 0,this);
                if(locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(location != null) {
                        Resources res = getResources();
                        display.setText(res.getString(R.string.location_display,location.getLatitude(), location.getLongitude()));
                    }
                }
            }
        } else {
            display.setText(R.string.location_permission_required);
        }
        display.setVisibility(View.VISIBLE);
    }

}
