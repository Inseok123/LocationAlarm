package com.example.gpstest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PhpMainActivity extends AppCompatActivity  {

    protected static String IP_ADDRESS = "52.79.189.85";
    protected static String TAG = "gpsreceive";
    protected EditText mEditTextName;

    protected TextView mTextViewResult;
    protected String mJsonString;
    protected ArrayList<GetGpsData> mArrayList;
    protected UsersAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected EditText mEditTextSearchKeyword;

    protected GestureDetector gestureDetector;

    String TAG_JSON="webnautes";
    String TAG_LATITUDE = "latitude";
    String TAG_LONGTITUDE = "longtitude";
    String TAG_DATE = "date1";
    String TAG_TIME = "time1";

    String date;
    String time,hour,minute,second;
    double lati,longti;
    MapFragment fragment;
    GoogleMap map,mGooglemap;
    SupportMapFragment googleMap;;
   /* String latiarray[] = new String[10];
    String longtiarray[] = new String[10];*/
    Double latarray[] = new Double[100];
    Double lonarray[] = new Double[100];

    // 배열 값 저장하는 부분
    ArrayList<String> latlist = new ArrayList<String>();
    ArrayList<String> longlist = new ArrayList<String>();
    ArrayList<String> datelist = new ArrayList<String>();
    ArrayList<String> timelist = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phpmysql_main);

       // stringList = new ArrayList<>();

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        mArrayList = new ArrayList<>();

        mAdapter = new UsersAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        gestureDetector = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener() {

            //누르고 뗄 때 한번만 인식하도록 하기위해서
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
       /* // 1.위도 경도 불러오기 버튼
        Button button_all = (Button) findViewById(R.id.button_main_all);
        button_all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/gpsreceive.php", "");
            }
        });*/

        // 2. 날짜 시간 값 체크 부분
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)findViewById(R.id.result);
                textView.setText("날짜 : " + date + " 시간 : "+hour+"시 "+minute+"분 "+second+"초");

            }
        });
        // 3. 착용자의 위치확인 띄우기
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();
                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/gpsreceive.php", "");

                Timer timer = new Timer();
                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),PresentLocation.class);
                        intent.putExtra("latitude",lati);
                        intent.putExtra("longtitude",longti);
                        startActivity(intent);
                    }
                };
                timer.schedule(task1,1200);
                return;
            }
        });

        // 4. 주변 관공서 띄우기
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mArrayList.clear();
                    mAdapter.notifyDataSetChanged();
                    GetData task = new GetData();
                    task.execute( "http://" + IP_ADDRESS + "/gpsreceive.php", "");

                    Timer timer = new Timer();
                    TimerTask task1 = new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), PoliceMap.class);
                            intent.putExtra("latitude",lati);
                            intent.putExtra("longtitude",longti);
                            startActivity(intent);
                        }
                    };
                    timer.schedule(task1,1200);

                }
        });


        // 5. 착용자 위치추적
         Button gpstracking = (Button)findViewById(R.id.gpstracking);
        gpstracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();
                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/gpsreceive.php", "");

                Timer timer = new Timer();
                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), GpsTracking.class);
                        Bundle b = new Bundle();
                        intent.putExtra("bundle",b); //실수 배열 전달
                        intent.putExtra("list",latlist);
                        intent.putExtra("list2",longlist);
                        intent.putExtra("list3",datelist);
                        intent.putExtra("list4",timelist);
                        startActivity(intent);
                    }
                };
                timer.schedule(task1,1200);

            }
        });

    }

    public class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(PhpMainActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    public void showResult(){

        TAG_JSON="webnautes";
        TAG_LATITUDE = "latitude";
        TAG_LONGTITUDE = "longtitude";
        TAG_DATE = "date1";
        TAG_TIME = "time1";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);
                String latitude = item.getString(TAG_LATITUDE);
                String longtitude = item.getString(TAG_LONGTITUDE);
                // 데이터로부터 받은 string 값을 실수로 변환하여 다른 액티비티로 인텐트 넘기는 변수 설정
                lati = Double.parseDouble(latitude);
                longti = Double.parseDouble(longtitude);

                Log.i("itme",item+"");
                // db로부터 읽어온 값을 배열에 저장하기


                // json 데이터 스트링 배열에 저장
                String getlatitude[] = new String[jsonArray.length()];
                String getlongtitude[] = new String[jsonArray.length()];
                getlatitude[i] = item.getString(TAG_LATITUDE);
                getlongtitude[i] = item.getString(TAG_LONGTITUDE);

                //json 데이터 double형 배열에 저장
                Double getlatarray[] = new Double[jsonArray.length()];
                Double getlonarray[] = new Double[jsonArray.length()];
                getlatarray[i] = Double.parseDouble(getlatitude[i]);
                getlonarray[i] = Double.parseDouble(getlongtitude[i]);

                String datearray[] = new String[jsonArray.length()];
                String timearray[] = new String[jsonArray.length()];
                datearray[i] = item.getString(TAG_DATE);
                timearray[i] = item.getString(TAG_TIME);

                // 배열 리스트에 데이터 저장하는 부분
                latlist.add(getlatitude[i]);
                longlist.add(getlongtitude[i]);
                datelist.add(datearray[i]);
                timelist.add(timearray[i]);

                Log.d("JSON Object", item + "");
                Log.d("JsonParsing", getlatitude[i] + "," + getlongtitude[i] + "," );
                Log.d("count",jsonArray.length()+"");
                Log.d("date",datearray[i]);
                Log.d("date",timearray[i]);



                date = item.getString(TAG_DATE);
                time = item.getString(TAG_TIME);
                hour = time.substring(0,2);
                minute = time.substring(2,4);
                second = time.substring(4,6);


                GetGpsData personalData = new GetGpsData();

               /* personalData.setMember_latitude(latitude);
                personalData.setMember_longtitude(longtitude);*/
                personalData.setDate(date);
                personalData.setTime(time);

                //mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }



}
