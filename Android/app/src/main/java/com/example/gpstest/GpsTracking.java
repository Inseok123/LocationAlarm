package com.example.gpstest;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


//현재 위치 나오게 하는 자바코드
public class GpsTracking extends AppCompatActivity implements OnMapReadyCallback
    , GoogleMap.OnMapClickListener
   {

    MapFragment fragment;
    GoogleMap map;

    protected static String IP_ADDRESS = "52.79.189.85";
    protected static String TAG = "gpsreceive";
    protected TextView mTextViewResult;
    protected String mJsonString;
    protected ArrayList<GetGpsData> mArrayList;
    protected UsersAdapter mAdapter;

    // 위도,경도 배열
    Double latitude[] = new Double[100];
    Double longitude[] = new Double[100];
    //날짜, 시간 배열
    ArrayList<String> datelist;
    ArrayList<String> timelist;
    int arraysize = 0;
    //POLYLINE 긋기 위한 배열 선언
    ArrayList<LatLng> arrayLine = new ArrayList<LatLng>(100);
    ArrayList<LatLng> polylinelist = new ArrayList<LatLng>(100);
    PolylineOptions polylineOptions;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpstracking);

            /*Gpstrack thread = new Gpstrack();
            thread.start();*/

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gpstrack);
        fragment.getMapAsync(this);


        // phpMainActivity 내 착용자의 위치 버튼을 클릭하면 위도,경도값을 인텐트로 넘기는데 여기서 위도,경도값을 받는 부분
        Intent intent = getIntent();
        Bundle b = getIntent().getExtras();
        //위도 경도 배열 값을 받아서 저장하는 부분
        ArrayList<String> latilist = b.getStringArrayList("list");
        ArrayList<String> longlist = b.getStringArrayList("list2");
        // 날짜, 시간 값을 받아서 저장하는 부분
        datelist = b.getStringArrayList("list3");
        timelist = b.getStringArrayList("list4");

        arraysize = latilist.size();

        for (int i = 0; i < latilist.size(); i++) {
            latitude[i] = Double.parseDouble(latilist.get(i));
            Log.d("asdf", latitude[i] + " " + "latitude" + i + "");
            Log.d("agasd", latilist.get(i) + "");

        }
        for (int i = 0; i < longlist.size(); i++) {
            longitude[i] = Double.parseDouble(longlist.get(i));
            Log.d("asdf", longitude[i] + " " + "longitude" + i + "");
            Log.d("agasd", longlist.get(i) + "");

        }

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

            for (int i = 0; i < arraysize; i++) {
                LatLng geoPoint = new LatLng(latitude[i], longitude[i]);  //전달받은 위도,경도 값을 지정하는 부분
                map.moveCamera(CameraUpdateFactory.newLatLng(geoPoint));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(geoPoint, 15));
                MarkerOptions marker = new MarkerOptions();
                marker.position(geoPoint);

                marker.title("날짜 " + datelist.get(i) + " 시간 " + timelist.get(i) + " 현재 착용자의 위치");
                marker.snippet("위도 " + latitude[i] + " 경도 " + longitude[i]);
                map.addMarker(marker);

                //위도 경도 값을 arrayLine 리스트에 삽입
                arrayLine.add(i, geoPoint);
                //list값에 중복된 값 제거하는 부분
                for(int j=0;j<arrayLine.size();j++){
                    if(!polylinelist.contains(arrayLine.get(j)))
                        polylinelist.add(arrayLine.get(j));
                }

                Log.d("qwer", String.valueOf(arrayLine.get(i)));
                Log.d("qdfdf",polylinelist+"");
            }
            // 맵 클릭시 polyline 출력 부분
            polylineOptions = new PolylineOptions();
            polylineOptions.color(Color.BLUE);
            polylineOptions.width(20);
            polylineOptions.addAll(polylinelist);
            map.addPolyline(polylineOptions);
        }
    }


       @Override
       public void onMapClick(LatLng latLng) {

       }
   }



