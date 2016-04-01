package com.bj58.socialsystem.utils;

//距离计算公式[单位米]
public class PosDistanceUtil {
	
    private final static double PI = 3.14159265358979323; // 圆周率
    private final static double R = 6371229; // 地球的半径 (单位M)

    public static int getDistance(double longt1, double lat1, double longt2,double lat2) {
        double x, y, distance;
        x = (longt2 - longt1) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
        y = (lat2 - lat1) * PI * R / 180;
        distance = Math.hypot(x, y);
        return (int)distance;
    }
}