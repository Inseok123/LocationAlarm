package com.example.gpstest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

// 현재 위치 및 주변 관공서 띄우기

public class PoliceMap extends AppCompatActivity implements OnMapReadyCallback,
        LocationListener, PlacesListener,GoogleMap.OnMarkerClickListener {


    LatLng currentPosition;
    List<Marker> prevMarkers;
    GoogleMap map;
    double latitude;
    double longtitude;
    LocationListener locationListener; //현재위치
    // MapFragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policemap);

        //마커 목록
        prevMarkers = new ArrayList<>();
        //지도 프래그먼트 생성
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
        //지도 로딩
        fragment.getMapAsync(this);

        // 검색 버튼 클릭 시
        Button btnSearch = (Button) findViewById(R.id.PoliceSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                prepareMap();
                drawMap();
                showPlaceInformation(currentPosition);
                // view.setVisibility(View.VISIBLE);
            }
        });




    }

    void showPlaceInformation(LatLng location) {
        //map.clear();
        if (prevMarkers != null) {
            prevMarkers.clear();
        }

        //구글 장소 검색 api 요청
        //장소 검색 api key를 여기다 입력
        new NRPlaces.Builder().listener(PoliceMap.this).key("AIzaSyD6HCh6LhB5du4tTJtU0Q1_3AxOJR58uJ0")
                .latlng(latitude, longtitude)
                .radius(5000)   //반경
                .type(PlaceType.POLICE) //경찰서 검색
                .build()
                .execute();

            map.animateCamera(CameraUpdateFactory.newLatLng(currentPosition));
    }

    //위치 변경 시
    @Override
    public void onLocationChanged(Location location) {

       /* Intent intent = getIntent();
        latitude = intent.getExtras().getDouble("latitude");
        longtitude = intent.getExtras().getDouble("longtitude");*/

        latitude = location.getLatitude();
        longtitude = location.getLongitude();
        currentPosition = new LatLng(latitude, longtitude);
        //drawMap();
    }

    void drawMap() {
        //map.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
        MarkerOptions marker = new MarkerOptions();
        marker.position(currentPosition);
        marker.title("위도 "+latitude+"경도 "+longtitude);
        // marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.call182));
        map.addMarker(marker);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        prepareMap();
        drawMap();
        GoogleMap mMap = googleMap;

        map.setOnMarkerClickListener(this);

    }
    //마커 클릭 이벤트 (마커 클릭 시 전화걸기 이벤트)
    @Override
    public boolean onMarkerClick(Marker marker) {
        //3초후 전화걸기 이벤트
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
                startActivity(intent);

            }
        };
        timer.schedule(task,3000);
        return false;
    }

    void prepareMap() {
        int check = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            // 위치정보 관리자 객체
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // 리스너 등록
            locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            // 수동으로 위치 구하기
            String locationProvider = LocationManager.GPS_PROVIDER;
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

            //최근 gps 좌표 저장
            if (lastKnownLocation != null) {
                latitude = lastKnownLocation.getLatitude();
                longtitude = lastKnownLocation.getLongitude();
                // 기본값 위치 좌표
            } else {
                //latitude = 37.5796212;
               // longitude = 126.9748523;
            }
            // 인텐트로 넘어온 값을 받아서 현재 착용자의 위치 주변의 관공서 띄우는 코드
            Intent intent = getIntent();
            latitude = intent.getExtras().getDouble("latitude");
            longtitude = intent.getExtras().getDouble("longtitude");
            currentPosition = new LatLng(latitude, longtitude);
            Log.i("test", "currentPosition" + currentPosition);

        }
    }

    @Override
    public void onPlacesFailure(PlacesException e) {

    }

    @Override
    public void onPlacesStart() {

    }

    @Override
    public void onPlacesSuccess(final List<Place> places) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (noman.googleplaces.Place place : places) {
                    LatLng latLng = new LatLng(place.getLatitude()
                            , place.getLongitude());

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(place.getName());
                    markerOptions.snippet(place.getVicinity());
                    Marker item = map.addMarker(markerOptions);
                    prevMarkers.add(item);
                }
                HashSet<Marker> hashSet = new HashSet<Marker>();
                hashSet.addAll(prevMarkers);
                prevMarkers.clear();
                prevMarkers.addAll(hashSet);
            }
        });
    }

    @Override
    public void onPlacesFinished() {


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    prepareMap();
                    drawMap();

                }
                break;

            default:
                break;
        }
    }

}