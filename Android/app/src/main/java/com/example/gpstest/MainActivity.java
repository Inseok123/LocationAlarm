package com.example.gpstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView houseimage;
    Button locationtracking,geofence,prevent;

    protected static String IP_ADDRESS = "52.79.189.85";
    protected static String TAG = "gpsreceive";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // 지오펜스 설정 클릭 부분
        geofence = (Button)findViewById(R.id.geofence);
        geofence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Findguide.class);
                startActivity(intent);


            }
        });

        //그림 이미지 표시하는 코드
        houseimage = (ImageView) findViewById(R.id.houseimage);
        houseimage.setImageResource(R.drawable.house);

        //버튼 온클릭 리스터 코드(미아방지대책)
        prevent = (Button)findViewById(R.id.prevent);
        prevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PreventActivity.class);
                startActivity(intent);
            }
        });



        // 아이 및 치매환자 신상 등록 버튼 리스너
        Button registerinfo = (Button)findViewById(R.id.registerinfo);
        registerinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterInfo.class);
                startActivity(intent);
            }
        });

        // 착용자의 위치확인 버튼 클릭 시
        Button getgpsvalue = (Button)findViewById(R.id.getgpsvalue);
        getgpsvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhpMainActivity.class);
                startActivity(intent);
            }
        });



    }


    // 옵션 메뉴 설정 부분
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //오늘의 날씨 클릭 할 부분
            case R.id.menu1 :
                Intent intent = new Intent(getApplicationContext(),Weather.class);
                startActivity(intent);

                Toast.makeText(this, "날씨정보입니다", Toast.LENGTH_SHORT).show();
                return true;
                // 두번째 옵션 메뉴 클릭 부분

        }
        return super.onOptionsItemSelected(item);
    }
}
