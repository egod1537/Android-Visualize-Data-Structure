package com.cit.algorithmVisualize.markInfo;

import com.cit.algorithmVisualize.Direction;

public class LineMarkInfo {
    public int idx;
    public String text;
    public Direction dir;

    public LineMarkInfo(int idx, String text, boolean up){
        this.idx = idx;
        this.text = text;
        this.dir = (up ? Direction.UP : Direction.DOWN);
    }
    public LineMarkInfo(int idx, String text, Direction dir){
        this.idx = idx;
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
