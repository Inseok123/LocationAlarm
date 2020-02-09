package com.example.gpstest;

public class GetGpsData {
    protected String member_latitude;
    protected String member_longtitude;
    protected String date;
    protected String time;

    public String getMember_latitude() {
        return member_latitude;
    }

    public String getMember_longtitude() {
        return member_longtitude;
    }



    public void setMember_latitude(String member_latitude) {
        this.member_latitude = member_latitude;
    }

    public void setMember_longtitude(String member_longtitude) {
        this.member_longtitude = member_longtitude;
    }
    public void setDate(String date){

        this.date = date;
    }
    public void setTime(String time)
    {
        this.time = time;
    }
}
