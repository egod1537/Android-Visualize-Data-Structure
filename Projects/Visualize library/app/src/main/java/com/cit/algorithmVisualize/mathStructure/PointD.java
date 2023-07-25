package com.cit.algorithmVisualize.mathStructure;

import android.graphics.Point;
import android.graphics.PointF;

import androidx.annotation.NonNull;

public class PointD {
    public double x;
    public double y;
    public PointD(){}
    public PointD(double x, double y){
        this.x= x;
        this.y = y;
    }
    public PointD(@NonNull Point p){
        this.x = p.x;
        this.y = p.y;
    }
    public PointD(@NonNull PointF p){
        this.x = p.x;
        this.y = p.y;
    }
    public PointD(@NonNull PointD p){
        this.x = p.x;
        this.y = p.y;
    }
    public final void set(double x, double y){
        this.x = x;
        this.y = y;
    }
    public final void set(PointD p){
        this.x = p.x;
        this.y = p.y;
    }
    public final void negate(){
        x = -x;
        y = -y;
    }
    public final void offset(double dx, double dy){
        x += dx;
        y += dy;
    }
    public final boolean equals(double x, double y){return this.x == x && this.y == y;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointD pointD = (PointD) o;

        if (Double.compare(pointD.x, x) != 0) return false;
        if (Double.compare(pointD.y, y) != 0) return false;

        return true;
    }

    @Override
    public String toString() {
        return "PointF(" + x + ", " + y + ")";
    }

    /**
     * Return the euclidian distance from (0,0) to the point
     */
    public final double length() {
        return length(x, y);
    }

    /**
     * Returns the euclidian distance from (0,0) to (x,y)
     */
    public static double length(double x, double y) {
        return Math.hypot(x, y);
    }
}
