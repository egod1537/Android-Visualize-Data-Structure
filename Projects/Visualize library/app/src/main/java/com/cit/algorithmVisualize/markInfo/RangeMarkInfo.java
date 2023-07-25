package com.cit.algorithmVisualize.markInfo;

import com.cit.algorithmVisualize.Direction;

public class RangeMarkInfo {
    public int l, r;
    public String text;
    public Direction dir;

    public RangeMarkInfo(int l, int r, String text, boolean up){
        this.l = l;
        this.r = r;
        this.text = text;
        this.dir = (up ? Direction.UP : Direction.DOWN);
    }
    public RangeMarkInfo(int l, int r, String text, Direction dir){
        this.l = l;
        this.r = r;
        this.text = text;
        this.dir = dir;
    }
    public boolean isUp(){return dir == Direction.UP;}
    public boolean isDown(){return dir == Direction.DOWN;}
    public boolean isLeft(){return dir == Direction.LEFT;}
    public boolean isRight(){return dir == Direction.RIGHT;}
    public boolean isHorizontal(){return isLeft() || isRight();}
    public boolean isVertical(){return isDown() || isUp();}
}
