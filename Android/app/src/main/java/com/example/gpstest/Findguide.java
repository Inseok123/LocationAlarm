package com.example.gpstest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

public class Findguide extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findguide);

        String str = "주변을 샅샅이 찾아보기";
        TextView content1 = (TextView)findViewById(R.id.content1);
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#5F00FF")),8,12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content1.setText(ssb);

        String str2 = "즉시 신고하기";
        TextView content2 = (TextView)findViewById(R.id.content2);
        SpannableStringBuilder ssb2 = new SpannableStringBuilder(str2);
        ssb2.setSpan(new ForegroundColorSpan(Color.parseColor("#5F00FF")),3,7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content2.setText(ssb2);

        String str3 ="아동과 관련된 증거를 보존하기";
        TextView content3 =(TextView)findViewById(R.id.content3);
        SpannableStringBuilder ssb3 = new SpannableStringBuilder(str3);
        ssb3.setSpan(new ForegroundColorSpan(Color.parseColor("#5F00FF")),7,16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content3.setText(ssb3);

        String str4 = "친구, 이웃을 통해 정보파악하기";
        TextView content4 = (TextView)findViewById(R.id.content4);
        SpannableStringBuilder ssb4 = new SpannableStringBuilder(str4);
        ssb4.setSpan(new ForegroundColorSpan(Color.parseColor("#5F00FF")),11,15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content4.setText(ssb4);

        String str5 = "실종아동전문기관에 접수하기";
        TextView content5 = (TextView)findViewById(R.id.content5);
        SpannableStringBuilder ssb5 = new SpannableStringBuilder(str5);
        ssb5.setSpan(new ForegroundColorSpan(Color.parseColor("#5F00FF")),9,12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content5.setText(ssb5);

        String str6 ="일시보호시설에 아이가 있는지 실종전문아동기관과 함께 확인하기";
        TextView content6 = (TextView)findViewById(R.id.content6);
        SpannableStringBuilder ssb6 = new SpannableStringBuilder(str6);
        ssb6.setSpan(new ForegroundColorSpan(Color.parseColor("#5F00FF")),0,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content6.setText(ssb6);

        String str7 =  "실종아동전문기관 홈페이지에서 보호시설에 있는 아동자료 확인하기";
        TextView content7 = (TextView)findViewById(R.id.content7);
        SpannableStringBuilder ssb7 = new SpannableStringBuilder(str7);
        ssb7.setSpan(new ForegroundColorSpan(Color.parseColor("#5F00FF")),16,24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content7.setText(ssb7);

        String str8 = "경찰에 유전자 검사를 요청하기";
        TextView content8 = (TextView)findViewById(R.id.content8);
        SpannableStringBuilder ssb8 = new SpannableStringBuilder(str8);
        ssb8.setSpan(new ForegroundColorSpan(Color.parseColor("#5F00FF")),4,10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content8.setText(ssb8);

    }
}
