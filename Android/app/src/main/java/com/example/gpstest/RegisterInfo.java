package com.example.gpstest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class RegisterInfo extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerinfo);


        //등록 정보 버튼을 누르면 체크박스 및 edittext 값을 받아와서 인텐트로 전달
        Button registerInfoCommit = (Button)findViewById(R.id.registerInfoCommit);
        registerInfoCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterResult.class);

                CheckBox children =(CheckBox)findViewById(R.id.checkbox1);
                intent.putExtra("contact_children",children.isChecked());

                CheckBox boy =(CheckBox)findViewById(R.id.checkbox3);
                intent.putExtra("boy",boy.isChecked());

                EditText editTextName = (EditText)findViewById(R.id.edittextname);
                intent.putExtra("contact_name",editTextName.getText().toString());

                EditText editTextAge = (EditText)findViewById(R.id.edittextage);
                intent.putExtra("contact_age",editTextAge.getText().toString());
                EditText ParentPhone = (EditText)findViewById(R.id.edittextphone);
                intent.putExtra("contact_phone",ParentPhone.getText().toString());

                EditText editTextGita = (EditText)findViewById(R.id.edittextgita);
                intent.putExtra("contact_gita",editTextGita.getText().toString());

                // 등록한 사진을 인텐트로 전달
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                float scale = (float) (1024/(float)bitmap.getWidth());
                int image_w = (int) (bitmap.getWidth() * scale);
                int image_h = (int) (bitmap.getHeight() * scale);
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image", byteArray);


                startActivity(intent);
            }
        });

        imageView = (ImageView)findViewById(R.id.picture);

        Button galleryopen = (Button)findViewById(R.id.galleryopen);
        galleryopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    imageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
