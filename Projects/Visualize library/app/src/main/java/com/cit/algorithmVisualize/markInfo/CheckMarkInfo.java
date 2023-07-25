package com.cit.algorithmVisualize.markInfo;

import android.graphics.Point;

import com.cit.algorithmVisualize.mathStructure.Polygon;
import com.cit.algorithmVisualize.Direction;

public class CheckMarkInfo {
    public int idx;
    public Direction dir;

    public CheckMarkInfo(int idx, boolean up){
        this.idx = idx;
        this.dir = (up ? Direction.UP : Direction.DOWN);
    }
    public CheckMarkInfo(int idx, Direction dir){
        this.idx = idx;
        this.dir = dir;
    }
    public boolean isUp(){return dir == Direction.UP;}
    public boolean isDown(){return dir == Direction.DOWN;}
    public boolean isLeft(){return dir == Direction.LEFT;}
    public boolean isRight(){return dir == Direction.RIGHT;}
    public boolean isHorizontal(){return isLeft() || isRight();}
    public boolean isVertical(){return isDown() || isUp();}
    public Polygon getCheckPolygon(boolean isUp){
        Polygon ret = new Polygon();
        ret.add(new Point(0, -10));
        ret.add(new Point(20, 10));
        ret.add(new Point(10, 20));
        ret.add(new Point(0, 10));
        ret.add(new Point(-10, 20));
        ret.add(new Point(-20, 10));
        if(isUp) ret.scaleY(-1);
        return ret;
    }
    public Polygon getCheckPolygon(Direction dir){
        if(isVertical())
            return getCheckPolygon(isUp());
        else{
            Polygon ret = new Polygon();
            ret.add(new Point(10, 0));
            ret.add(new Point(-10, 20));
            ret.add(new Point(-20, 10));
            ret.add(new Point(-10, 0));
            ret.add(new Point(-20, -10));
            ret.add(new Point(-10, -20));
            if(isLeft()) ret.scaleX(-1);
            return ret;
        }
    }
}
