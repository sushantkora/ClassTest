package com.example.kora1101.classtest;

/**
 * Created by kora1101 on 8/28/2017.
 */

public class light {
    float x;
    float y;
    float z;
    int index;
    public light(int index,float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
        this.index=index;
    }
    public double NormalData(){
        return Math.sqrt(x*x+y*y+z*z);
    }
}
