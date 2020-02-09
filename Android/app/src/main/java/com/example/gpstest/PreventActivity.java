package com.example.gpstest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;

//미아 예방 대책 관련 코드

public class PreventActivity extends AppCompatActivity {
    Button findguide; // 가이드 버튼
    Button siterul; //url 연결



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevent);

        // 항상 intent 넘겨줄때는 메니페트스에 액티비티 추가하기!!!!!
        // 가이드 안내 버튼
        findguide =(Button)findViewById(R.id.findguide);
        findguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Findguide.class);
                startActivity(intent);
            }
        });



    // 실종아동기관 url 연결 코드
        siterul = (Button)findViewById(R.id.siteurl );
        siterul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.missingchild.or.kr/CMSPage/CMSPage.cshtml"));
                startActivity(intent);
            }
        });
    // 실종아동기관 전화번호 연결 코드
        Button call = (Button)findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:02-777-0182"));
                startActivity(intent);
            }
        });

        //중앙치매센터 사이트 연결
        Button dementiapatient = (Button)findViewById(R.id.dementiapatient);
        dementiapatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.nid.or.kr/main/main.aspx"));
                startActivity(intent);

            }
        });

        //치매자가진단 사이트 연결
        Button dementiaprevent = (Button)findViewById(R.id.dementiaprevent);
        dementiaprevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.nid.or.kr/support/hi_list.aspx"));
                startActivity(intent);

            }
        });
        // 치매센터 전화번호 연결 코드
        Button dementiapatientcall = (Button)findViewById(R.id.dementiapatientcall);
        dementiapatientcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:1899-9988"));
                startActivity(intent);
            }
        });



    }
}
