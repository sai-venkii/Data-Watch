package com.example.networkmonitor2;

public class Time {
    private static Time timeInstance;
    private long startTime;
    private long endTime;
    private Time(){
        startTime=System.currentTimeMillis();
        endTime=0;
    }
    public static synchronized Time getInstance() {
        if (timeInstance == null) {
            timeInstance = new Time();
        }
        return timeInstance;
    }
    public Time(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
