package com.cit.algorithmVisualize.markInfo;

public class RectMarkInfo {
    public int sx, sy, ex, ey;
    public int color_wire, color_fill, alpha;

    public RectMarkInfo(int sx, int sy, int ex, int ey, int color_wire, int color_fill, int alpha){
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
        this.color_wire = color_wire;
        this.color_fill = color_fill;
        this.alpha = alpha;
    }
    public RectMarkInfo(int sx, int sy, int ex, int ey, int color_wire, int color_fill){
        this(sx,sy,ex,ey,color_wire,color_fill,255);
    }
    public RectMarkInfo(int l, int r, int color_wire, int color_fill){
        this.sy = l;
        this.ey = r;
        this.color_wire = color_wire;
        this.color_fill = color_fill;
    }
}
