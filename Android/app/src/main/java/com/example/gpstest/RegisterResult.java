package com.example.gpstest;

import android.app.Person;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.renderscript.Int2;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class RegisterResult extends AppCompatActivity {

    private String Data;
    private ArrayList<String> Arrdata = new ArrayList<>();
    String text = "";
    String Select = "";
    String Sex = "";
    ImageView Resultpicture;
    private final int Get_GALLERY_IMAGE = 200;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_registerresult);

    // 인텐트 정보 받아오는 부분(ResultInfo 에서 보낸 값들을 해당 엑티비티에 표시하기 위해 값을 받아오는 부분)
        Intent intent = getIntent();
        TextView textViewName = (TextView)findViewById(R.id.textViewName);
        String name = intent.getStringExtra("contact_name");
        if(name!=null)
            textViewName.setText(name);

        TextView textViewAge = (TextView)findViewById(R.id.textViewAge);
        String age = intent.getStringExtra("contact_age");
        if(age!=null)
            textViewAge.setText(age);

        TextView textViewPhone = (TextView)findViewById(R.id.textViewPhone);
        String phone = intent.getStringExtra("contact_phone");
        if(phone!=null)
            textViewPhone.setText(phone);

        TextView textViewGita = (TextView)findViewById(R.id.textViewGita);
        String gitacontent = intent.getStringExtra("contact_gita");
        if(gitacontent!=null)
            textViewGita.setText(gitacontent);

        TextView textViewSelect = (TextView)findViewById(R.id.textViewSelect);
        boolean children = intent.getBooleanExtra("contact_children",false);
        if(children){
            textViewSelect.setText("아이");
            Select = "아이";
        }
        else {
            textViewSelect.setText("치매환자");
            Select  = "치매환자";
        }

        TextView textViewGender = (TextView)findViewById(R.id.textViewGender);
        boolean Gender = intent.getBooleanExtra("boy",false);
        if(Gender){
            textViewGender.setText("남성");
            Sex = "남성";
        }
        else {
            textViewGender.setText("여성");
            Sex = "여성";
        }

        //이미지 받아오는 부분
        Resultpicture = (ImageView)findViewById(R.id.resultpicture);
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        Resultpicture.setImageBitmap(bitmap);

        String subject = "미아 및 치매환자 신상정보";


        //인텐트 정보를 받아서 카톡으로 공유하는 부분
        Intent intent1 = new Intent();
        intent1.setAction(Intent.ACTION_SEND);

        intent1.setType("text/plain");
        intent1.putExtra(Intent.EXTRA_SUBJECT, subject);


        text += " \n1. 유형 : " + Select;
        text += " \n2. 성별 : " + Sex;
        text += " \n3. 이름 : " + name;
        text += " \n4. 나이 : " + age;
        text += " \n5. 보호자 번호 : " + phone;
        text += " \n6. 인상착의 : " + gitacontent;

        intent1.putExtra(Intent.EXTRA_TEXT,text);
        Intent chooser = Intent.createChooser(intent1,"공유");
        startActivity(chooser);


    }

}
