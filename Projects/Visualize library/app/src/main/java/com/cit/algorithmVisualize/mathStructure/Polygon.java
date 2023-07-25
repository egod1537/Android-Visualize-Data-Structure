package com.cit.algorithmVisualize.mathStructure;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;
/*
    @description CPlane에서 다각형을 나타내는 클래스
 */
public class Polygon extends ArrayList<Point> {
    public Polygon(){

    }
    public Polygon(List<Point> list){
        for(Point point : list) add(point);
    }
    public Polygon(PolygonD polyf){
        for(PointD point : polyf) add(new Point((int)point.x, (int)point.y));
    }

    public void scaleX(double ratio){
        int size_poly = size();
        for(int i=0; i < size_poly; i++){
            Point point = get(i);
            point.x *= ratio;
            set(i, point);
        }
    }
    public void scaleY(double ratio){
        int size_poly = size();
        for(int i=0; i < size_poly; i++){
            Point point = get(i);
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
    public void move(Point point){
        move(point.x, point.y);
    }
    public void move(double x, double y){
        int size_poly = size();
        for(int i=0; i < size_poly; i++){
            Point point = get(i);
            point.x += x; point.y += y;
            set(i, point);
        }
    }
}
