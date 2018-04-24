package com.example.administrator.Palletizer;

public class AlarmObject {

    private String type;
    private String time;
    private String source;

    AlarmObject(){
    }

    AlarmObject(String mType, String mTime, String mSource){
        this.type = mType;
        this.time = mTime;
        this.source = mSource;
    }

    public String toString(){
        return "Alarm tipe: " + type + ", Raised at " + time + ", source: " + source;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
