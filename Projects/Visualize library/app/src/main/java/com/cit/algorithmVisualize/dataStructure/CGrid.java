package com.cit.algorithmVisualize.dataStructure;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.cit.algorithmVisualize.markInfo.CheckMarkInfo;
import com.cit.algorithmVisualize.mathStructure.Polygon;
import com.cit.algorithmVisualize.Direction;
import com.cit.algorithmVisualize.GraphicView;
import com.cit.algorithmVisualize.markInfo.LineMarkInfo;
import com.cit.algorithmVisualize.markInfo.RangeMarkInfo;
import com.cit.algorithmVisualize.markInfo.RectMarkInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
/*
    @description 2차원 배열을 시각화할 수 있는 클래스
 */
public class CGrid<T> extends ArrayList<ArrayList<T>> {
    //격자 칸의 색깔을 저장하고 있는 리스트
    private List<List<Integer>> mColors = new ArrayList<>();
    //선 표시에 대한 정보를 저장하고 있는 리스트
    private List<LineMarkInfo> mLineMarks = new ArrayList<>();
    //체크 표시에 대한 정보를 저장하고 있는 리스트
    private List<CheckMarkInfo> mCheckMarks = new ArrayList<>();
    //구간 표시에 대한 정보를 저장하고 있는 리스트
    private List<RangeMarkInfo> mRangeMarks = new ArrayList<>();
    //상자 표시에 대한 정보를 저장하고 있는 리스트
    private List<RectMarkInfo> mRectMarks = new ArrayList<>();
    private GraphicView mPanel;
    //region constructor
    public CGrid(GraphicView panel){
        setGraphicPanel(panel);
    }
    public CGrid(GraphicView panel, List<List<T>> array){
        this(panel);
        for(int i=0; i < array.size(); i++)
            this.add(array.get(i));
    }
    //endregion

    public void setGraphicPanel(GraphicView panel){
        this.mPanel = panel;
    }

    //region List Functions
    public ArrayList<T> get(int x){return super.get(x);}
    public T get(int x, int y){return super.get(x).get(y);}

    @Override
    public boolean add(ArrayList<T> x){
        mColors.add(new ArrayList<>(Collections.nCopies(x.size(), Color.WHITE)));
        return super.add(x);
    }
    public CGrid add(List<T> x){
        ArrayList<T> temp = new ArrayList<>();
        temp.addAll(x);
        add(temp);
        return this;
    }

    @Override
    public void add(int idx, ArrayList<T> x){
        mColors.add(idx, new ArrayList<>(Collections.nCopies(x.size(), Color.WHITE)));
        super.add(idx, x);
    }
    @Override
    public boolean addAll(Collection<? extends ArrayList<T>> c){
        for(ArrayList<T> x : c)this.add(x);
        return false;
    }
    @Override
    public boolean remove(Object x){
        int idx = super.indexOf(x);
        mColors.remove(idx);
        return super.remove(x);
    }
    public boolean remove(int x, Object var){
        int idx = super.indexOf(var);
        mColors.get(x).remove(idx);
        return super.get(x).remove(var);
    }
    @Override
    public ArrayList<T> remove(int idx){
        mColors.remove(idx);
        return super.remove(idx);
    }
    public void remove(int x, int y){
        mColors.get(x).remove(y);
        super.get(x).remove(y);
    }
    @Override
    public void clear(){
        mColors.clear();
        super.clear();
    }
    public void clear(int x){
        mColors.remove(x);
        super.get(x).clear();
    }

    public int width(){
        int ret = 0;
        int size_x = height();
        for(int i=0; i < size_x; i++)
            ret = Math.max(ret, this.get(i).size());
        return ret;
    }
    public int height(){
        return super.size();
    }
    //endregion

    public CGrid clearMarks(){
        clearRectMarks();
        clearCheckMarks();
        clearLineMarks();
        clearRangeMarks();
        return this;
    }

    //region Color
    public CGrid setColor(int x, int y, int color){
        mColors.get(x).set(y, color);
        return this;
    }
    public CGrid setColors(int x, List<Integer> idxs, int color){
        for(Integer idx : idxs)
            mColors.get(x).set(idx, color);
        return this;
    }
    public CGrid setColor(int x, int color){
        int size_color = mColors.get(x).size();
        mColors.set(x, new ArrayList<>(Collections.nCopies(size_color, color)));
        return this;
    }
    public CGrid setColors(List<Integer> idxs, int color){
        for(Integer idx : idxs){
            int size_color = mColors.get(idx).size();
            mColors.set(idx, new ArrayList<>(Collections.nCopies(size_color, color)));
        }
        return this;
    }
    public CGrid setColor(int color){
        int size_x = mColors.size();
        for(int i=0; i < size_x; i++) setColor(i, Color.WHITE);
        return this;
    }
    public CGrid clearColor(int x, int y){
        return setColor(x,y,Color.WHITE);
    }
    public CGrid clearColor(int x){
        return setColor(x, Color.WHITE);
    }
    public CGrid clearColor(){
        return setColor(Color.WHITE);
    }
    //endregion

    //region Line
    public CGrid addLineMark(int idx, String text, Direction dir){
        mLineMarks.add(new LineMarkInfo(idx,text, dir));
        return this;
    }
    public CGrid addLineMark(int idx, String text){
        return addLineMark(idx, text, Direction.UP);
    }
    public CGrid addLineMark(int idx){
        return addLineMark(idx, "");
    }
    public CGrid removeLineMark(int idx){
        mLineMarks.removeIf(x -> x.idx == idx);
        return this;
    }
    public CGrid clearLineMarks(){
        mLineMarks.clear();
        return this;
    }
    //endregion

    //region Check
    public CGrid addCheckMark(int idx, Direction dir){
        mCheckMarks.add(new CheckMarkInfo(idx, dir));
        return this;
    }
    public CGrid addCheckMark(int idx){
        return addCheckMark(idx, Direction.UP);
    }
    public CGrid removeCheckMark(int idx){
        mCheckMarks.removeIf(x -> x.idx == idx);
        return this;
    }
    public CGrid clearCheckMarks(){
        mCheckMarks.clear();
        return this;
    }
    //endregion

    //region Range
    public CGrid addRangeMark(int l, int r, String text, Direction dir){
        mRangeMarks.add(new RangeMarkInfo(l, r, text, dir));
        return this;
    }
    public CGrid addRangeMark(int l, int r, String text){
        return addRangeMark(l, r, text, Direction.UP);
    }
    public CGrid addRangeMark(int l, int r){
        return addRangeMark(l, r, "");
    }
    public CGrid removeRangeMark(int l, int r){
        mRangeMarks.removeIf(x -> x.l == l && x.r == r);
        return this;
    }
    public CGrid clearRangeMarks(){
        mRangeMarks.clear();
        return this;
    }
    //endregion

    //region Rect
    public CGrid addRectMark(int sx, int sy, int ex, int ey, int color_wire, int color_fill){
        mRectMarks.add(new RectMarkInfo(sx,sy,ex,ey,color_wire,color_fill));
        return this;
    }
    public CGrid addRectMark(int sx, int sy, int ex, int ey, int color_wire){
        return addRectMark(sx, sy, ex, ey, color_wire,Color.TRANSPARENT);
    }
    public CGrid addRectMark(int sx, int sy, int ex, int ey){
        return addRectMark(sx, sy, ex, ey, Color.BLACK);
    }
    public CGrid addRectMark(Point s, Point e, int color_wire, int color_fill){
        return addRectMark(s.x,s.y,e.x,e.y,color_wire,color_fill);
    }
    public CGrid addRectMark(Point s, Point e, int color_wire){
        return addRectMark(s,e,color_wire,Color.TRANSPARENT);
    }
    public CGrid addRectMark(Point s, Point e){
        return addRectMark(s,e,Color.BLACK);
    }
    public CGrid removeRectMark(int sx, int sy, int ex, int ey){
        mRectMarks.removeIf(mark ->
           mark.sx == sx && mark.sy == sy && mark.ex == ex && mark.ey == ey);
        return this;
    }
    public CGrid clearRectMarks(){
        mRectMarks.clear();
        return this;
    }
    //endregion

    //region draw
    private int getCellScaleX(){return (mPanel.mRect.height())/(this.height()+1);}
    private int getCellScaleY(){return (mPanel.mRect.width())/(this.width()+1);}
    private int getCellScale(){
        return Math.min(getCellScaleX(), getCellScaleY());
    }
    private int getLineStrokeWidth(){
        return getCellScale()/25;
    }
    private int getTextScale(){return getCellScale()/4;}
    private float getCheckScale(){return getCellScale()/100;}
    private Point getCellPoint(float x, float y){
        x++; y++;
        int size_cell = getCellScale();
        int px = (int)(size_cell*y), py  = (int)(size_cell*x);
        int dx = -size_cell/2 + (mPanel.mRect.width() - size_cell*width())/2;
        int dy = -size_cell/2 + (mPanel.mRect.height() - size_cell*height())/2;
        return new Point(px+dx, py+dy);
    }
    private void drawRangeMarks(){
        Paint paint_text = new Paint();
        paint_text.setColor(Color.BLACK);
        paint_text.setTextSize(getTextScale());

        for(RangeMarkInfo mark : mRangeMarks){
            int offset = getCellScale()/10;
            if(mark.isVertical()){
                if(mark.isUp()) offset *= -1;

                Point point_l = getCellPoint((mark.isUp() ? -0.75f : height()-0.25f), mark.l);
                Point point_r = getCellPoint((mark.isUp() ? -0.75f : height()-0.25f), mark.r);

                Point point_ul = new Point(point_l), point_dl = new Point(point_l);
                point_ul.offset(0,-offset);
                point_dl.offset(0,offset);

                Point point_ur = new Point(point_r),point_dr = new Point(point_r);
                point_ur.offset(0,-offset);
                point_dr.offset(0,offset);

                mPanel.drawLine(point_ul, point_dl);
                mPanel.drawLine(point_ur, point_dr);
                mPanel.drawLine(point_dl, point_dr);

                Point point_text = new Point((point_dl.x +point_dr.x)/2, point_dr.y + 2*offset);
                mPanel.drawText(point_text, mark.text, paint_text);
            }else{
                if(mark.isLeft()) offset *= -1;

                Point point_l = getCellPoint(mark.l,  (mark.isLeft() ? -0.75f : width()-0.25f));
                Point point_r = getCellPoint(mark.r, (mark.isLeft() ? -0.75f : width()-0.25f));

                Point point_ul = new Point(point_l), point_dl = new Point(point_l);
                point_ul.offset(-offset, 0);
                point_dl.offset(offset, 0);

                Point point_ur = new Point(point_r),point_dr = new Point(point_r);
                point_ur.offset(-offset, 0);
                point_dr.offset(offset, 0);

                mPanel.drawLine(point_ul, point_dl);
                mPanel.drawLine(point_ur, point_dr);
                mPanel.drawLine(point_dl, point_dr);

                Point point_text = new Point(point_dr.x + 2*offset, (point_dl.y +point_dr.y)/2);
                mPanel.drawText(point_text, mark.text, paint_text);
            }
        }
    }
    private void drawCheckMarks(){
        Paint paint_wire = new Paint();
        paint_wire.setColor(Color.BLACK);
        paint_wire.setStrokeWidth(getLineStrokeWidth());

        Paint paint_fill = new Paint();
        paint_fill.setColor(Color.BLACK);

        for(CheckMarkInfo mark : mCheckMarks){
            Polygon poly_check = mark.getCheckPolygon(mark.dir);
            poly_check.scale(getCheckScale());
            if(mark.isVertical())
                poly_check.move(getCellPoint((mark.isUp() ? -0.75f : height()-0.25f), mark.idx));
            else
                poly_check.move(getCellPoint(mark.idx,
                        (mark.isRight() ? -0.75f : super.get(mark.idx).size()-0.25f)));

            mPanel.drawPolygon(poly_check, paint_wire, paint_fill);
        }
    }
    private void drawLineMarks(){
        int size_line = getCellScale();
        int hSize = size_line/2;

        Paint paint_line = new Paint();
        paint_line.setColor(Color.BLACK);
        paint_line.setStrokeWidth(getLineStrokeWidth());

        Paint paint_text = new Paint();
        paint_text.setColor(Color.BLACK);
        paint_text.setTextSize(getTextScale());

        for(LineMarkInfo mark : mLineMarks){
            if(mark.isVertical()){
                Point pivot_up = getCellPoint(0, mark.idx);
                Point pivot_down = getCellPoint(height()-1, mark.idx);
                pivot_up.offset(-hSize, -hSize*3/2);
                pivot_down.offset(-hSize, hSize*3/2);
                mPanel.drawLine(pivot_up, pivot_down, paint_line);

                Point point_text = new Point((mark.isUp() ? pivot_up : pivot_down));

                Rect rect_text = mPanel.getTextRect(mark.text, getTextScale());
                point_text.y += (mark.isUp() ? -1 : 1) * rect_text.height();
                mPanel.drawText(point_text, mark.text, paint_text);
            }else{
                Point pivot_left = getCellPoint(mark.idx, 0);
                Point pivot_right = getCellPoint(mark.idx, width()-1);
                pivot_left.offset(-hSize*5/3, -hSize);
                pivot_right.offset(hSize*5/3, -hSize);
                mPanel.drawLine(pivot_left, pivot_right, paint_line);

                Point point_text = new Point((mark.isLeft() ? pivot_left : pivot_right));

                Rect rect_text = mPanel.getTextRect(mark.text, getTextScale());
                point_text.x += (mark.isLeft() ? -1 : 1) * rect_text.width();
                mPanel.drawText(point_text, mark.text, paint_text);
            }
        }
    }
    private void drawRectMarks(){
        int size_cell = getCellScale();
        for(RectMarkInfo mark : mRectMarks){
            Point point_start = getCellPoint(mark.sx, mark.sy),
                    point_end = getCellPoint(mark.ex, mark.ey);

            int dx = (mark.ex - mark.sx +1), dy = (mark.ey - mark.sy + 1);

            Point point_pivot = new Point(
                    (point_start.x+point_end.x)/2, (point_start.y + point_end.y)/2);
            Rect rect = new Rect(
                    point_pivot.x, point_pivot.y,
                    point_pivot.x+size_cell*dx, point_pivot.y+size_cell*dy
            );

            Paint paint_wire = new Paint();
            paint_wire.setColor(mark.color_wire);
            paint_wire.setStrokeWidth(getLineStrokeWidth());

            Paint paint_fill = new Paint();
            paint_fill.setColor(mark.color_fill);

            mPanel.drawRect(rect, paint_wire, paint_fill);
        }
    }
    private void drawCells(){
        int size_x = height();
        int size_cell = getCellScale();

        Paint paint_wire = new Paint();
        paint_wire.setColor(Color.BLACK);
        paint_wire.setStrokeWidth(getLineStrokeWidth());

        Paint paint_text = new Paint();
        paint_text.setColor(Color.BLACK);
        paint_text.setTextSize(getTextScale());

        for(int i=0; i <size_x; i++){
            int size_y = super.get(i).size();
            for(int j=0; j < size_y; j++){
                Paint paint_fill = new Paint();
                paint_fill.setColor(mColors.get(i).get(j));

                mPanel.drawRectWithText(getCellPoint(i,j), size_cell,
                        Integer.toString((Integer)this.get(i,j)),
                        paint_wire, paint_fill, paint_text);
            }
        }
    }

    public CGrid<T> draw(){
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
