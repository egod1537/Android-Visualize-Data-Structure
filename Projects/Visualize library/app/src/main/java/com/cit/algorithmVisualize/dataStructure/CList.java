package com.cit.algorithmVisualize.dataStructure;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.cit.algorithmVisualize.Direction;
import com.cit.algorithmVisualize.markInfo.CheckMarkInfo;
import com.cit.algorithmVisualize.markInfo.RangeMarkInfo;
import com.cit.algorithmVisualize.mathStructure.Polygon;
import com.cit.algorithmVisualize.GraphicView;
import com.cit.algorithmVisualize.markInfo.LineMarkInfo;
import com.cit.algorithmVisualize.markInfo.RectMarkInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
    @description 1차원 배열을 시각화할 수 있는 클래스
 */
public class CList<T> extends ArrayList<T> implements Cloneable {
    //각 칸의 색깔을 저장하고 있는 리스트
    private List<Integer> mColors = new ArrayList<>();
    //선 표시에 대한 정보를 가지고 있는 리스트
    private List<LineMarkInfo> mLineMarks = new ArrayList<>();
    //체크 표시에 대한 정보를 가지고 있는 리스트
    private List<CheckMarkInfo> mCheckMarks = new ArrayList<>();
    //구간 표시에 대한 정보를 가지고 있는 리스트
    private List<RangeMarkInfo> mRangeMarks = new ArrayList<>();
    //상자 표시에 대한 정보를 가지고 있는 리스트
    private List<RectMarkInfo> mRectMarks = new ArrayList<>();

    private GraphicView mPanel;

    //region constructor
    public CList(GraphicView panel){
        setGraphicPanel(panel);
    }
    public CList(GraphicView panel, List<T> list) {
        this(panel);
        addAll(list);
    }
    //endregion

    //region List Functions
    public void setGraphicPanel(GraphicView panel){
        this.mPanel = panel;
    }

    @Override
    public boolean add(T x){
        mColors.add(Color.WHITE);
        return super.add(x);
    }

    @Override
    public void add(int idx, T x){
        mColors.add(idx, Color.WHITE);
        super.add(idx, x);
    }
    @Override
    public boolean addAll(Collection<? extends T> c){
        for(T x : c)this.add(x);
        return false;
    }
    @Override
    public boolean remove(Object x){
        int idx = super.indexOf(x);
        mColors.remove(idx);
        return super.remove(x);
    }
    @Override
    public T remove(int idx){
        mColors.remove(idx);
        return super.remove(idx);
    }
    @Override
    public void clear(){
        mColors.clear();
        super.clear();
    }

    public Object clone(){
        CList<T> ret = new CList(mPanel);

        for(int i=0; i < this.size(); i++)
            ret.add(this.get(i));

        ret.mColors = new ArrayList<>(this.mColors);
        ret.mLineMarks = new ArrayList<>(this.mLineMarks);
        ret.mCheckMarks = new ArrayList<>(this.mCheckMarks);
        ret.mRangeMarks = new ArrayList<>(this.mRangeMarks);
        ret.mRectMarks = new ArrayList<>(this.mRectMarks);

        return ret;
    }
    //endregion

    //region Color
    public CList<T> setColor(int idx, int color){
        mColors.set(idx, color);
        return this;
    }
    public CList<T> setColors(List<Integer> idxs, int color){
        for(Integer idx : idxs) mColors.set(idx, color);
        return this;
    }
    public CList<T> clearColors(){
        for(int i=0; i < mColors.size(); i++)
            setColor(i, Color.WHITE);
        return this;
    }
    //endregion

    public CList<T> clearMarks(){
        clearLineMarks();
        clearCheckMarks();
        clearRangeMark();
        clearRectMark();
        return this;
    }

    //region LineMarks
    public CList<T> addLineMark(int idx, String text, boolean isUp){
        mLineMarks.add(new LineMarkInfo(idx, text, isUp));
        return this;
    }
    public CList<T> addLineMark(int idx, String text){
        return addLineMark(idx, text, true);
    }
    public CList<T> addLineMark(int idx, boolean isUp){
        return addLineMark(idx, "", isUp);
    }
    public CList<T> addLineMark(int idx){
        return addLineMark(idx, "", true);
    }

    public CList<T> removeLineMark(int idx){
        mLineMarks.removeIf(x->x.idx==idx);
        return this;
    }
    public CList<T> clearLineMarks(){
        mLineMarks.clear();
        return this;
    }
    //endregion

    //region CheckMarks
    public CList<T> addCheckMark(int idx, boolean isUp){
        mCheckMarks.add(new CheckMarkInfo(idx, isUp));
        return this;
    }
    public CList<T> addCheckMarks(List<Integer> idxs, boolean isUp){
        for(Integer idx : idxs) addCheckMark(idx, isUp);
        return this;
    }
    public CList<T> addCheckMark(int idx){return addCheckMark(idx, true);}
    public CList<T> addCheckMarks(List<Integer> idx){return addCheckMarks(idx, true);}
    public CList<T> removeCheckMark(int idx){
        mCheckMarks.removeIf(x -> x.idx == idx);
        return this;
    }
    public CList<T> clearCheckMarks(){
        mCheckMarks.clear();
        return this;
    }
    //endregion

    //region RangeMark
    public CList<T> addRangeMark(int l, int r, String text, boolean isUp){
        mRangeMarks.add(new RangeMarkInfo(l, r, text, isUp));
        return this;
    }
    public CList<T> addRangeMark(int l, int r, String text){
        return addRangeMark(l, r,text,true);
    }
    public CList<T> addRangeMark(int l, int r, boolean isUp){
        return addRangeMark(l, r,"",isUp);
    }
    public CList<T> addRangeMark(int l, int r){
        return addRangeMark(l, r, "");
    }
    public CList<T> removeRangeMark(int l, int r){
        mRangeMarks.removeIf(x -> x.l == l && x.r == r);
        return this;
    }
    public CList<T> clearRangeMark(){
        mRangeMarks.clear();
        return this;
    }
    //endregion

    //region RectMark
    public CList<T> addRectMarks(int l, int r, int color_wire, int color_fill){
        mRectMarks.add(new RectMarkInfo(l,r,color_wire, color_fill));
        return this;
    }
    public CList<T> addRectMark(int idx, int color_wire, int color_fill){
        return addRectMarks(idx, idx, color_wire, color_fill);
    }
    public CList<T> addRectMarks(int l, int r, int color_wire){
        return addRectMarks(l,r,color_wire, Color.TRANSPARENT);
    }
    public CList<T> addRectMark(int idx, int color_wire){
        return addRectMarks(idx, idx, color_wire);
    }
    public CList<T> addRectMarks(int l, int r){
        return addRectMarks(l,r,Color.BLACK, Color.TRANSPARENT);
    }
    public CList<T> addRectMark(int idx){
        return addRectMarks(idx, idx);
    }
    public CList<T> removeRectMark(int l, int r){
        mRectMarks.removeIf(x -> x.sy == l && x.ey == r);
        return this;
    }
    public CList<T> clearRectMark(){
        mRectMarks.clear();
        return this;
    }
    //endregion

    //region draw
    private int getCellScale(){
        return mPanel.mRect.width() / (super.size() + 1);
    }
    private int getLineStrokeWidth(){
        return getCellScale()/25;
    }
    private int getTextScale(){return getCellScale()/4;}
    private float getCheckScale(){return getCellScale()/100;}
    private Rect getCellRect(int idx){
        idx++;
        int size_cell = getCellScale();
        int nx = idx*size_cell, ny = mPanel.mRect.height()/2;
        return new Rect(nx,ny,nx+size_cell,ny+size_cell);
    }
    private Point getCellPoint(int idx){
        idx++;
        int size_cell = getCellScale();
        int nx = idx*size_cell, ny = mPanel.mRect.height()/2;
        return new Point(nx,ny);
    }
    private Point getCellPivot(int idx, boolean isUp){
        Rect rect = getCellRect(idx);
        int size_cell = getCellScale();

        Point ret = new Point(rect.left, rect.top);
        ret.y += (isUp ? -1 : 1) * size_cell*3/4;
        return ret;
    }
    private Point getCellPivot(int idx){
        return getCellPivot(idx, true);
    }

    private void drawRectMarks(){
        int size_cell = getCellScale();

        for(RectMarkInfo mark : mRectMarks){
            Paint paint = new Paint();
            paint.setStrokeWidth(getLineStrokeWidth()*5/3);

            Point point_l = getCellPoint(mark.sy),
                    point_r = getCellPoint(mark.ey);

            int x = (point_l.x+point_r.x)/2, y = (point_l.y+point_r.y)/2;
            int size_rect = size_cell*(mark.ey - mark.sy+1);
            Rect rect = new Rect(x, y,
                    x+size_rect,y+size_cell);

            Paint paint_wire = new Paint(paint);
            paint_wire.setColor(mark.color_wire);

            Paint paint_fill = new Paint(paint);
            paint_fill.setColor(mark.color_fill);

            mPanel.drawRect(rect, paint_wire, paint_fill);
        }
    }
    private void drawRangeMarks(){
        for(RangeMarkInfo mark : mRangeMarks){
            int offset_y = getCellScale() / 10;
            if(mark.isUp()) offset_y *= -1;

            Point point_l = getCellPivot(mark.l, mark.isUp());
            Point point_r = getCellPivot(mark.r, mark.isUp());

            Point point_ul = new Point(point_l), point_dl = new Point(point_l);
            point_ul.offset(0,-offset_y);
            point_dl.offset(0,offset_y);

            Point point_ur = new Point(point_r),point_dr = new Point(point_r);
            point_ur.offset(0,-offset_y);
            point_dr.offset(0,offset_y);

            mPanel.drawLine(point_ul, point_dl);
            mPanel.drawLine(point_ur, point_dr);
            mPanel.drawLine(point_dl, point_dr);

            Point point_text = new Point((point_dl.x +point_dr.x)/2, point_dr.y + 2*offset_y);
            Paint paint_text = new Paint();
            paint_text.setColor(Color.BLACK);
            paint_text.setTextSize(getTextScale());

            mPanel.drawText(point_text, mark.text, paint_text);
        }
    }
    private void drawCheckMarks(){
        for(CheckMarkInfo mark : mCheckMarks){
            Paint paint_wire = new Paint();
            paint_wire.setColor(Color.BLACK);
            paint_wire.setStrokeWidth(getLineStrokeWidth());

            Paint paint_fill = new Paint();
            paint_fill.setColor(Color.BLACK);

            Polygon poly_check = mark.getCheckPolygon(mark.dir);
            poly_check.scale(getCheckScale());
            poly_check.move(getCellPivot(mark.idx, mark.isUp()));

            mPanel.drawPolygon(poly_check, paint_wire, paint_fill);
        }
    }
    private void drawLineMarks(){
        int size_line = getCellScale();
        int hSize = size_line/2;

        Paint paint_line = new Paint();
        paint_line.setColor(Color.BLACK);
        paint_line.setStrokeWidth(getLineStrokeWidth()*5/3);

        Paint paint_text = new Paint();
        paint_text.setColor(Color.BLACK);
        paint_text.setTextSize(getTextScale());

        for(LineMarkInfo mark : mLineMarks){
            Point pivot = getCellPoint(mark.idx);

            Point pivot_up = new Point(pivot);
            Point pivot_down = new Point(pivot);
            pivot_up.offset(-hSize, -hSize*5/3);
            pivot_down.offset(-hSize, hSize*5/3);
            mPanel.drawLine(pivot_up, pivot_down, paint_line);

            Point point_text = new Point((mark.dir == Direction.UP ? pivot_up : pivot_down));

            Rect rect_text = mPanel.getTextRect(mark.text, getTextScale());
            point_text.y += (mark.dir == Direction.UP ? -1 : 1) * rect_text.height();
            mPanel.drawText(point_text, mark.text, paint_text);
        }
    }
    private void drawCells(){
        int size_list = size();
        int size_cell = getCellScale();

        Paint paint_wire = new Paint();
        paint_wire.setColor(Color.BLACK);
        paint_wire.setStrokeWidth(getLineStrokeWidth());

        Paint paint_text = new Paint();
        paint_text.setColor(Color.BLACK);
        paint_text.setTextSize(getTextScale());

        for(int i=0; i < size_list; i++){
            Paint paint_fill = new Paint();
            paint_fill.setColor(mColors.get(i));

            mPanel.drawRectWithText(getCellRect(i),
                    Integer.toString((Integer) super.get(i)),
                    paint_wire, paint_fill, paint_text);
        }
    }
    public CList draw(){
        mPanel.renderClear();

        drawCells();
        drawRectMarks();
        drawLineMarks();
        drawCheckMarks();
        drawRangeMarks();

        mPanel.draw();
        return this;
    }
    //endregion
}
