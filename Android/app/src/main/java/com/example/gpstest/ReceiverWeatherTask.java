/*package com.example.gpstest;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReceiverWeatherTask extends AsyncTask<String,Void, JSONObject> {

    String nowtemp,mintemp,maxtemp,humid,direct,msg;
    String weather;

    String iconName = "";
    String nowTemp ="";
    String maxTemp ="";
    String minTemp ="";
    String humidity ="";
    String speed ="";
    String main ="";
    String description ="";

    String state;
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

    @Override
    protected void onPostExecute(JSONObject result) {

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
            }catch (JSONException e){
                e.printStackTrace();

            }
            description = transferWeather(description);

            msg = description + " 습도 " + humidity +"%, 풍속 "+ speed +"m/s" + " 온도 현재:"+nowTemp+" / 최저:"+minTemp
                    +"/ 최고:" +maxTemp;
            weather = msg;

        }


    }


    protected String transferWeather(String weather){
         state = weather.toLowerCase();

        if(state.equals("haze")){
            return "안개";
        }
        else if(state.equals("fog")){
            return "안개";
        }
        else if(state.equals("clouds")){
            return "구름";
        }
        else if(state.equals("few clouds")){
            return "구름 조금";
        }
        else if(state.equals("scattered clouds")){
            return "구름 낌";
        }
        else if(state.equals("broken clouds")){
            return "구름 많음";
        }
        else if(state.equals("overcast clouds")){
            return "구름 많음";
        }
        else if(state.equals("clear sky")){
            return "맑음";
        }
        return "";
    }

}
*/