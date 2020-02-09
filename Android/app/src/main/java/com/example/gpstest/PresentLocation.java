package com.example.gpstest;


import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//현재 위치 나오게 하는 자바코드

public class PresentLocation extends AppCompatActivity implements  OnMapReadyCallback {

    MapFragment fragment;
    GoogleMap map,mGooglemap;
    SupportMapFragment googleMap;
    //위도 ,경도
     double latitude,longtitude;
    //거리 계산
    double distance;
    int meter = 0;
    Location locationA = new Location("Childlocation"); //착용자(현재위치)
    Location locationB = new Location("Parentlocation"); //부모위치 위치
    Button geofenceclick;
    String phonenumber = "010-9749-9745";
    String message = "지오펜스 범위를 이탈했습니다.";
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentlocation);


        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.presentlocation);
        fragment.getMapAsync(this);

        // phpMainActivity 내 착용자의 위치 버튼을 클릭하면 위도,경도값을 인텐트로 넘기는데 여기서 위도,경도값을 받는 부분
        Intent intent = getIntent();
        latitude = intent.getExtras().getDouble("latitude");
        longtitude = intent.getExtras().getDouble("longtitude");
        Log.v("lati", latitude + "");
        Log.v("lati", longtitude + "");

        //지오펜스 거리 계산 클릭 시
        geofenceclick = (Button) findViewById(R.id.Geofenceclick);
        geofenceclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //착용자의 위치 저장
                locationA.setLatitude(latitude);
                locationA.setLongitude(longtitude);
                //부모의 현재 위치 저장
                locationB.setLatitude(37.486520);
                locationB.setLongitude(126.887790);
                //거리계산
                distance = locationA.distanceTo(locationB);
                meter = (int)distance;
                //Log.d("fgga", meter+"m");
                //Toast.makeText(PresentLocation.this, "asfdas", Toast.LENGTH_SHORT).show();
                Log.d("pohno",phonenumber+" "+message);
                if(meter<1000) {

                }
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        drawMap();
    }

    void drawMap() {
        int check = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            LatLng geoPoint = new LatLng(latitude, longtitude);  //전달받은 위도,경도 값을 지정하는 부분
            map.moveCamera(CameraUpdateFactory.newLatLng(geoPoint));

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(geoPoint, 15));
            MarkerOptions marker = new MarkerOptions();
            marker.position(geoPoint);
            marker.title("현재 착용자의 위치");
            marker.snippet("위도 "+latitude+" 경도 "+longtitude);
            map.addMarker(marker);

        }
    }




}



