package com.example.mobile_aris.models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_aris.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class set_appointment extends AppCompatActivity {

    // initializing
    // FusedLocationProviderClient
    // object
    private FusedLocationProviderClient mFusedLocationClient;
    public Double longitude_here,latititude_here;
    private ArrayList<clinics> cliniclist_near = new ArrayList<>();
    private ArrayList<clinics> cliniclist_all = new ArrayList<>();
    private int PERMISSION_ID = 44;
    private Spinner spinerclinic,spinnertime;
    private TextView date,purpose;
    String clinic_id;
    ArrayAdapter<String> stringAdapterclinic_near,stringAdapterclinic_all;
    ArrayList<String> stringclinic_near = new ArrayList<>();
    ArrayList<String> stringclinic_all = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        date = (TextView) findViewById(R.id.in_date);
        purpose = (TextView) findViewById(R.id.Purpose);
        spinerclinic = (Spinner) findViewById(R.id.add_facilities);
        spinnertime = (Spinner) findViewById(R.id.set_time);

        // method to get the location





    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_nearby:
                if (checked)
                    cliniclist_near.clear();
                    stringclinic_near.clear();
                    getLastLocation();
                    stringAdapterclinic_near = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, stringclinic_near);
                    stringAdapterclinic_near.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinerclinic.setAdapter(stringAdapterclinic_near);
                    spinerclinic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            clinic_id = cliniclist_near.get(position).get_id();
                            spinerclinic.getSelectedItem().toString();
                            Log.d("_id", "onItemSelected: "+clinic_id);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
            case R.id.radio_all:
                if (checked)
                    cliniclist_all.clear();
                    stringclinic_all.clear();
                    getLastLocation();
                    stringAdapterclinic_all = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, stringclinic_all);
                    stringAdapterclinic_all.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinerclinic.setAdapter(stringAdapterclinic_all);
                    break;

        }
    }



    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            longitude_here = location.getLongitude();
                            latititude_here = location.getLatitude();
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            String url = "clinic/get/clinic?latitude="+latititude_here+"&longitude="+longitude_here;
                            Log.d("coord", "getdata: "+url);
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                    Request.Method.GET,
                                    getString(R.string.url) + url, null, response -> {
                                        try {
                                            JSONObject clinics = response.getJSONObject("clinics");
                                            Log.d("response", "onComplete: "+clinics);
                                            JSONArray nearby = new JSONArray(clinics.getString("nearby"));
                                            JSONArray all = new JSONArray(clinics.getString("all"));

                                            for (int i = 0; i < nearby.length(); i++) {
                                                JSONObject clinic_1 = nearby.getJSONObject(i);
                                                JSONObject a1 = new JSONObject(clinic_1.getString("location"));
                                                JSONObject a2 = new JSONObject(clinic_1.getString("address"));
                                                clinics near_clinic = new clinics(
                                                        clinic_1.getString("_id"),
                                                        clinic_1.getString("name")
                                                );
                                                cliniclist_near.add(near_clinic);
                                                stringclinic_near.add(near_clinic.getName_clinic());
                                            }

                                            for (int i = 0; i < all.length(); i++) {

                                                JSONObject clinic_2 = all.getJSONObject(i);
                                                clinics all_clinic = new clinics(
                                                        clinic_2.getString("_id"),
                                                        clinic_2.getString("name")
                                                );

                                                cliniclist_all.add(all_clinic);
                                                stringclinic_all.add(all_clinic.getName_clinic());
                                            }



                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    },
                                    error -> {
                                        Log.e("volleyError", error.toString());
                                    }
                            );

                            requestQueue.add(jsonObjectRequest);
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            longitude_here = mLastLocation.getLongitude();
            latititude_here = mLastLocation.getLatitude();
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}