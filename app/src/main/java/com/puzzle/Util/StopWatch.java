package com.puzzle.Util;

//Referenced from http://www.goldb.org/stopwatchjava.html
public class StopWatch {
    
    private long timerStart = 0;
    private long timerStop = 0;
    private boolean timerTicking = false;

    
    public void starttimer() {
        this.timerStart = System.currentTimeMillis();
        this.timerTicking = true;
    }

    
    public void stoptimer() {
        this.timerStop = System.currentTimeMillis();
        this.timerTicking = false;
    }

    
    //elapsed time in milliseconds
    public long getElapsedTime() {
        long elapsedTime;
        if (timerTicking) {
             elapsedTime = (System.currentTimeMillis() - timerStart);
        }
        else {
            elapsedTime = (timerStop - timerStart);
        }
        return elapsedTime;
    }
    
    
    //elapsed time in seconds
    public long getElapsedTimeSecs() {
        long elapsedTime;
        if (timerTicking) {
            elapsedTime = ((System.currentTimeMillis() - timerStart) / 1000);
        }
        else {
            elapsedTime = ((timerStop - timerStart) / 1000);
        }
        return elapsedTime;
    }
 
    public static void main(String[] args) {
        StopWatch s = new StopWatch();
        s.starttimer();
        s.stoptimer();
        System.out.println("milliseconds: " + s.getElapsedTime());
    }
}