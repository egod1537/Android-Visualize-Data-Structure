package com.cit.algorithmVisualize.markInfo;

import android.graphics.Color;

import com.cit.algorithmVisualize.mathStructure.Polygon;

public class PolygonMarkInfo {
    public Polygon polygon;
    public int color_wire, color_fill, alpha;
    public PolygonMarkInfo(Polygon polygon, int color_wire, int color_fill, int alpha){
        this.polygon = new Polygon(polygon);
        this.color_wire = color_wire;
        this.color_fill = color_fill;
        this.alpha = alpha;
    }
    public PolygonMarkInfo(Polygon polygon, int color_wire, int color_fill){
        this(polygon,color_wire,color_fill, 255);
    }
    public PolygonMarkInfo(Polygon polygon, int color_wire){
        this(polygon,color_wire, Color.WHITE, 255);
    }
    public PolygonMarkInfo(Polygon polygon){
        this(polygon,Color.BLACK, Color.WHITE, 255);
    }
}
