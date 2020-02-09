package com.example.gpstest;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Weather extends AppCompatActivity {
    //최소 GPS 정보 업데이트 거리
    public final int MIN_DISTANCE_CHANGE_FOR_UPDATES = 1000;
    //최소 업데이트시간 1분
    public final int MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean isGetLocation = false;
    Location location;
    double lat;
    double lon;
    LocationManager locationManager;
    TextView weatherinfo;
    private ArrayList<String> stringList;
    private JSONArray jsonArray;
    String iconURL;
    ImageView weathericons;
    String msg;
    String nowTemp ="";
    String maxTemp ="";
    String minTemp ="";
    String humidity ="";
    String speed ="";
    String main ="";
    String description ="";
    String state;
    String iconName ="";
    String locate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        stringList = new ArrayList<>();
        getLocation();

        ReceiverWeatherTask receiverWeatherTask = new ReceiverWeatherTask();
        //텍스트 뷰
        weatherinfo = (TextView) findViewById(R.id.tv_WeatherInfo);
        //이미지 뷰
        weathericons = (ImageView) findViewById(R.id.weathericon);
        //weathericons.setImageResource(R.drawable.call117);
    }

    public Location getLocation() {
        try {

            final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {

            } else {
                this.isGetLocation = true;

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                    if (locationManager != null) {
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return location;

    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            getWeatherData(lat, lon);
            //weatherinfo.setText("위도" + location.getLatitude() + "경도" + location.getLongitude());

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
    };



    private void getWeatherData(double lat, double lon) {
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon +
                "&units=metric&appid=ea977e3fbfa2e9827bc1b3818e31f5b3";
        ReceiverWeatherTask receiverUseTask = new ReceiverWeatherTask();
        receiverUseTask.execute(url);

    }

    private class ReceiverWeatherTask extends AsyncTask<String,Void, JSONObject> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... datas) {
            try{
                HttpURLConnection connection = (HttpURLConnection) new URL(datas[0]).openConnection();
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.connect();

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader in = new BufferedReader(reader);

                    String readed;
                    while ((readed = in.readLine()) != null) {
                        JSONObject jsonObject = new JSONObject(readed);
                        return jsonObject;
                    }
                }
                else{
                    return null;
                }
                return null;
            } catch(Exception e){
                e.printStackTrace();
            }
            return  null;
        }

        //json 데이터 파싱 부분
        @Override
        protected void onPostExecute(JSONObject result) {
            StringBuffer sb = new StringBuffer();
            if(result != null){


                try {
                   iconName = result.getJSONArray("weather").getJSONObject(0).getString("icon");
                    nowTemp = result.getJSONObject("main").getString("temp");
                    humidity = result.getJSONObject("main").getString("humidity");
                    minTemp = result.getJSONObject("main").getString("temp_min");
                    maxTemp = result.getJSONObject("main").getString("temp_max");
                    speed = result.getJSONObject("wind").getString("speed");
                    main = result.getJSONArray("weather").getJSONObject(0).getString("main");
                    description = result.getJSONArray("weather").getJSONObject(0).getString("description");
                    locate = result.getString("name");
                    description = transferWeather(description);
                    iconURL = "http://openweathermap.org/img/w"+iconName+".png";
                    //데이터를 불러와서 텍스트 뷰에 출력하는 부분
                    sb.append(" 현재 위치 : "+locate+"\n 현재 날씨 : "+description+"\n" + " 습도 : "
                            + humidity +"% \n 풍속 : "+ speed +"m/s\n" + " 현재온도 : "+nowTemp+
                            "도 \n 최저온도 : "+minTemp+"도"
                            +" \n 최고온도 : " +maxTemp+"도");
                    // 날씨 정보를 텍스트로 출력 하는 부분
                    weatherinfo.setText(sb.toString());
                    // 해당 날씨에 맞는 이미지 출력
                    if(description.equals("조각 구름") ||  description.equals("구름 조금"))
                    { weathericons.setImageResource(R.drawable.littlecloud);
                    }else if(description.equals("미세먼지 없는 맑은 하늘이에요")){
                        weathericons.setImageResource(R.drawable.sun);
                    }else if(description.equals("안개가 있으니 시야에 주의하세요")){
                        weathericons.setImageResource(R.drawable.fog);
                    }else if(description.equals("눈과 비가 오니 우산 챙기세요")){
                        weathericons.setImageResource(R.drawable.rainorsnow);
                    }else if(description.equals("눈이 오니까 빙판길에 주의하세요") || description.equals("약한 눈이 내려요")){
                        weathericons.setImageResource(R.drawable.snow);
                    }else if(description.equals("가벼운 비가 내리네요")){
                        weathericons.setImageResource(R.drawable.rain);
                    }else if(description.equals("비가 와요, 우산 챙기세요")||description.equals("비가 많이 오니 외출을 자제하세요")){
                        weathericons.setImageResource(R.drawable.rain);
                    }else if(description.equals("소나기가 내리고 있으니 잠시 쉬었다 가세요")){
                        weathericons.setImageResource(R.drawable.shower);
                    }else if(description.equals("천둥 번개가 치니 외출을 자제하세요")){
                        weathericons.setImageResource(R.drawable.lighting);
                    }else if(description.equals("태풍, 외출을 자제하세요!!")){
                        weathericons.setImageResource(R.drawable.strongwind);
                    }else if(description.equals("황사 바람, 외출을 자제하세요")){
                        weathericons.setImageResource(R.drawable.dust);
                    }else if(description.equals("구름이 있어요")){
                        weathericons.setImageResource(R.drawable.highcloud);
                    }else if(description.equals("황사가 심해요, 미세먼지 마스크 꼭 챙기세요")){
                        weathericons.setImageResource(R.drawable.dust);
                    }else if(description.equals("흐림")){
                        weathericons.setImageResource(R.drawable.dark);
                    }

                }catch (JSONException e){
                    e.printStackTrace();

                }


                /*msg = description + " 습도 " + humidity +"%, 풍속 "+ speed +"m/s" + " 온도 현재:"+nowTemp+" / 최저:"+minTemp
                        +"/ 최고:" +maxTemp;*/


            }


        }


        protected String transferWeather(String weather) {
            state = weather.toLowerCase();

            if (state.equals("haze")) {
                return "안개가 있으니 시야에 주의하세요";
            } else if (state.equals("fog")) {
                return "안개가 있으니 시야에 주의하세요";
            }else if (state.equals("mist")) {
                return "안개가 있으니 시야에 주의하세요";
            } else if (state.equals("clouds")) {
                return "구름이 있어요";
            } else if (state.equals("few clouds")) {
                return "구름 조금";
            } else if (state.equals("scattered clouds")) {
                return "조각 구름";
            } else if (state.equals("broken clouds")) {
                return "조각 구름";
            } else if (state.equals("overcast clouds")) {
                return "흐림";
            } else if (state.equals("light snow")) {
                return "약한 눈이 내려요";
            } else if (state.equals("snow")) {
                return "눈이 오니까 빙판길에 주의하세요";
            } else if (state.equals("rain and snow")) {
                return "눈과 비가 오니 우산 챙기세요";
            } else if (state.equals("light rain") || state.equals("light intensity drizzle")
                || state.equals("drizzle") || state.equals("drizzle rain")){
                return "가벼운 비가 내리네요";
            } else if (state.equals("moderate rain")) {
                return "비가 와요, 우산 챙기세요";
            } else if (state.equals("shower rain and drizzle") || state.equals("shower drizzle")
                || state.equals("heavy shower rain and drizzle") ||state.equals("shower rain")) {
                return "소나기가 내리고 있으니 잠시 쉬었다 가세요";
            }else if (state.equals("thunderstorm") || state.equals("heavy thunderstorm")
                    || state.equals("ragged thunderstorm") || state.equals("thunderstorm with heavy drizzle")
             || state.equals("thunderstorm with heavy rain")) {
                return "천둥 번개가 치니 외출을 자제하세요";
            }else if (state.equals("dust")) {
                return "황사가 심해요, 미세먼지 마스크 꼭 챙기세요";
            }else if (state.equals("tornado")) {
                return "태풍, 외출을 자제하세요!!";
            }else if (state.equals("sand, dust whirls")) {
                return "황사 바람, 외출을 자제하세요";
            }else if (state.equals("very heavy rain") || state.equals("extreme rain")
            || state.equals("heavy intensity rain")) {
                return "비가 많이 오니 외출을 자제하세요";
            }else if (state.equals("calm")) {
                return "바람 없음";
            }else if (state.equals("clear sky")) {
                return "미세먼지 없는 맑은 하늘이에요";
            }else if (state.equals("gentle breeze")) {
                return "산들바람";
            }
            return "";
        }

    }

    /*private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

*/

}

