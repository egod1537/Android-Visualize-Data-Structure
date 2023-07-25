package com.cit.algorithmVisualize.dataStructure;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.cit.algorithmVisualize.GraphicView;
import com.cit.algorithmVisualize.mathStructure.PointD;
import com.cit.algorithmVisualize.mathStructure.Polygon;
import com.cit.algorithmVisualize.mathStructure.PolygonD;
import com.cit.algorithmVisualize.mathStructure.Edge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
    @description 그래프 구조를 시각화할 수 있는 클래스
 */
public class CGraph {
    public List<List<Edge>> G = new ArrayList<>();

    //각 정점이 그려질 위치를 저장하고 있는 리스트
    private List<Point> mVert = new ArrayList<>();
    //각 간선의 정보를 저장하고 있는 리스트
    private List<Edge> mEdge = new ArrayList<>();

    //각 정점의 색깔을 저장하고 있는 리스트
    private List<Integer> mVertColor = new ArrayList<>();
    //각 간선의 색깔을 저장하고 있는 리스트
    private List<Integer> mEdgeColor = new ArrayList<>();

    //각 정점 안에 나타낼 텍스트를 저장하고 있는 리스트
    //만약 아무것도 저장하고 있지 않다면 정점 번호가 적혀진다
    private List<String> mVertText = new ArrayList<>();

    //각 정점의 활성화 여부를 저장하고 있는 리스트
    private List<Boolean> mVisibleVert = new ArrayList<>();
    //각 간선의 활성화 여부를 저장하고 있는 리스트
    private List<Boolean> mVisibleEdge = new ArrayList<>();

    //방향 그래프 여부
    private boolean isDirected = false;
    //간선에 가중치의 여부
    private boolean isWeight = false;

    private int vertSize = -1;
    private int textSize = -1;

    //region Graph Functions
    private GraphicView mPanel;

    //region constructor
    public CGraph(GraphicView panel){
        setGraphicPanel(panel);
    }
    public CGraph(GraphicView panel, List<Point> vert){

        this(panel);
        addAll_vert(vert);
    }
    public CGraph(GraphicView panel, List<Point> vert, List<Edge> edge){

        this(panel, vert);
        addAll_edge(edge);
    }
    //endregion

    public CGraph setGraphicPanel(GraphicView panel){

        this.mPanel = panel;
        return this;
    }
    public CGraph setDirectedGraph(boolean isDirected){
        this.isDirected = isDirected;
        return this;
    }
    public CGraph setWeightGraph(boolean isWeight){
        this.isWeight = isWeight;
        return this;
    }

    public CGraph setVertSize(int size){
        this.vertSize = size;
        return this;
    }
    public CGraph setVertsize(){return setVertSize(-1);}

    public CGraph setTextSize(int size){
        this.textSize = size;
        return this;
    }
    public CGraph setTextSize(){return setTextSize(-1);}

    public int vertSize(){return mVert.size();}
    public int edgeSize(){return mEdge.size();}
    public CGraph clearColor(int color){
        setVertColor(color);
        setEdgeColor(color);
        return this;
    }
    public CGraph clearColor(){
        setVertColor();
        setEdgeColor();
        return this;
    }

    /*
        @description 정점을 추가하는 메서드
        @param point 추가할 정점이 view에 그려질 위치
        @param color 추가할 정점의 색깔 (기본값은 WHITE)
     */
    public CGraph add_vert(Point point, int color){
        mVert.add(point);
        G.add(new ArrayList<>());
        mVertColor.add(color);
        mVertText.add("");
        mVisibleVert.add(true);
        return this;
    }
    public CGraph add_vert(Point point){
        return add_vert(point, Color.WHITE);
    }
    /*
        @description 여러개의 정점을 추가하는 메서드
        @param points 추가할 정점들의 view에 그려질 위치
        @param colors 추가할 정점들의 색깔 (기본값은 WHITE)
     */
    public CGraph addAll_vert(List<Point> points, List<Integer> colors){
        for(int i=0; i < points.size(); i++)
            add_vert(points.get(i), colors.get(i));
        return this;
    }
    public CGraph addAll_vert(List<Point> list){
        return addAll_vert(list, Collections.nCopies(list.size(), Color.WHITE));
    }

    /*
        @description idx번 정점을 제거하는 메서드
        @param idx 제거할 정점의 번호
     */
    public CGraph remove_vert(int idx){
        mVert.remove(idx);
        mVertColor.remove(idx);
        mVertText.remove(idx);
        mVisibleVert.remove(idx);
        return this;
    }
    /*
        @description point위치에 있는 정점을 제거하는 메서드
        @param point 제거할 정점의 위치
     */
    public CGraph remove_vert(Point point){
        return remove_vert(mVert.indexOf(point));
    }

    public CGraph setVertColor(int idx, int color){
        mVertColor.set(idx, color);
        return this;
    }
    public CGraph setVertColor(int color){
        for(int i = 0; i < mVertColor.size(); i++)
            mVertColor.set(i, color);
        return this;
    }
    public CGraph setVertColor(){
        return setVertColor(Color.WHITE);
    }

    public CGraph setVertText(int idx, String text){
        mVertText.set(idx, text);
        return this;
    }
    public CGraph setVertText(int idx){
         return setVertText(idx, "");
    }

    public CGraph add_edge(Edge e, int color){
        e.idx = mEdge.size();
        mEdge.add(e);
        G.get(e.from).add(e);
        mEdgeColor.add(color);
        mVisibleEdge.add(true);
        return this;
    }

    public CGraph add_edge(Edge e){
        return add_edge(e, Color.BLACK);
    }

    public CGraph addAll_edge(List<Edge> list, List<Integer> colors){
        for(int i=0; i < list.size(); i++)
            add_edge(list.get(i), colors.get(i));
        return this;
    }
    public CGraph addAll_edge(List<Edge> list){
        return addAll_edge(list, Collections.nCopies(list.size(), Color.BLACK));
    }
    public CGraph remove_edge(Edge e){
        int idx = mEdge.indexOf(e);
        mEdge.remove(idx);
        mEdgeColor.remove(idx);
        mVisibleEdge.remove(idx);
        return this;
    }
    public CGraph remove_edge(int u, int v, int c){
        return remove_edge(new Edge(u, v, c));
    }
    public CGraph remove_edge(int u, int v){
        return remove_edge(u,v,0);
    }
    public CGraph setEdgeColor(int idx, int color){
        mEdgeColor.set(idx, color);
        return this;
    }
    public CGraph setEdgeColor(int color){
        for(int i = 0; i < mEdgeColor.size(); i++)
            mEdgeColor.set(i, color);
        return this;
    }
    public CGraph setEdgeColor(){
        return setEdgeColor(Color.BLACK);
    }

    public CGraph setVertActive(int idx, boolean isActive){
        mVisibleVert.set(idx, isActive);
        return this;
    }
    public CGraph setEdgeActive(int idx, boolean isActive){
        mVisibleEdge.set(idx, isActive);
        return this;
    }
    //endregion

    //region draw
    private int getVertScale(){
        if(vertSize == -1)
            return mPanel.width() * mPanel.height() / 15_000;
        return vertSize;
    }
    private int getLineStrokeWidth(){return getVertScale()/10;}
    private int getTextScale(){
        if(textSize == -1)
            return getVertScale()*3/4;
        return textSize;
    }
    private int getWeightedGap(){return getTextScale();}
    private int getDirectedScale(){return 20;}

    private void drawVerts(){
        int n = vertSize();
        Paint paint_wire = new Paint();
        paint_wire.setColor(Color.BLACK);
        paint_wire.setStrokeWidth(getLineStrokeWidth());

        Paint paint_text = new Paint();
        paint_text.setTextSize(getTextScale());
        paint_text.setColor(Color.BLACK);

        for(int i=0; i < n; i++){
            if(!mVisibleVert.get(i)) continue;
            Paint paint_fill = new Paint();
            paint_fill.setColor(mVertColor.get(i));
            mPanel.drawCircle(mVert.get(i), getVertScale(), paint_wire, paint_fill);

            String text = mVertText.get(i);
            if(text.isEmpty()) text = Integer.toString(i);
            mPanel.drawText(mVert.get(i), text, paint_text);
        }
    }
    private void drawEdgeCost(Edge edge){
        Point u = mVert.get(edge.from), v = mVert.get(edge.to);
        PointF diff = new PointF(-v.y+u.y, v.x-u.x);
        double d = Math.sqrt(diff.x*diff.x + diff.y * diff.y);
        diff.x /= d; diff.y /= d;

        PointF mid = new PointF((u.x+v.x)/2, (u.y+v.y)/2);
        mid.offset(diff.x*getWeightedGap(), diff.y*getWeightedGap());

        mPanel.drawText(new Point((int)mid.x, (int)mid.y), Integer.toString(edge.cost), getTextScale());
    }
    private void drawEdgeDirect(Edge edge, int color){
        Point u = mVert.get(edge.from), v = mVert.get(edge.to);
        double dx =  v.x-u.x, dy = v.y-u.y;
        double d = Math.sqrt(dx*dx+dy*dy);

        int sz = getDirectedScale();
        int sz_vert = getVertScale();
        PointD dA = new PointD(-dy, dx), dB = new PointD(dy, -dx);
        dA.x = (dA.x / d * sz); dA.y = (dA.y / d * sz);
        dB.x = (dB.x / d * sz); dB.y = (dB.y / d * sz);

        PointD dR = new PointD(-dx, -dy);
        dR.x = (dR.x / d *(sz_vert+sz)); dR.y = (dR.y / d *(sz_vert+sz));

        PolygonD poly = new PolygonD();
        poly.add(new PointD(v.x+dA.x+dR.x, v.y+dA.y+dR.y));
        poly.add(new PointD(v.x+dR.x/2, v.y+dR.y/2));
        poly.add(new PointD(v.x+dB.x+dR.x, v.y+dB.y+dR.y));

        Paint paint = new Paint();
        paint.setColor(color);
        mPanel.drawFillPolygon(new Polygon(poly), paint);
    }
    private void drawEdges(){
        int n = edgeSize();

        for(int i=0; i < n; i++){
            if(!mVisibleEdge.get(i)) continue;
            Edge edge = mEdge.get(i);
            Paint paint = new Paint();
            paint.setStrokeWidth(getLineStrokeWidth());
            paint.setColor(mEdgeColor.get(i));
            if(mVisibleVert.get(edge.from) && mVisibleVert.get(edge.to)){
                mPanel.drawLine(mVert.get(edge.from), mVert.get(edge.to), paint);
                if(isWeight)drawEdgeCost(edge);
                if(isDirected)drawEdgeDirect(edge, mEdgeColor.get(i));
            }
        }
    }

    public CGraph draw(){
        mPanel.renderClear();

        drawEdges();
        drawVerts();

        mPanel.draw();
        return this;
    }
    //endregion
}
