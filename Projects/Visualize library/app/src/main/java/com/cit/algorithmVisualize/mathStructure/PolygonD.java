package com.cit.algorithmVisualize.mathStructure;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;
/*
    @description Polygon의 Double 버전 클래스
 */
public class PolygonD extends ArrayList<PointD> {
    public PolygonD(){

    }
    public PolygonD(List<PointD> list){
        for(PointD point : list) add(point);
    }

    public void scaleX(double ratio){
        int size_poly = size();
        for(int i=0; i < size_poly; i++){
            PointD point = get(i);
            point.x *= ratio;
            set(i, point);
        }
    }
    public void scaleY(double ratio){
        int size_poly = size();
        for(int i=0; i < size_poly; i++){
            PointD point = get(i);
            point.y *= ratio;
            set(i, point);
        }
    }
    public void scale(double ratio){
        scaleX(ratio); scaleY(ratio);
    }
    public void moveX(double x){
        move(x,0);
    }
    public void moveY(double y){
        move(0,y);
    }
    public void move(PointF point){
        move(point.x, point.y);
    }
    public void move(double x, double y){
        int size_poly = size();
        for(int i=0; i < size_poly; i++){
            PointD point = get(i);
            point.x += x; point.y += y;
            set(i, point);
        }
    }
}
