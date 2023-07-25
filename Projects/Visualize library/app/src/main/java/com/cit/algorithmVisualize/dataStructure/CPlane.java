package com.cit.algorithmVisualize.dataStructure;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Range;

import com.cit.algorithmVisualize.GraphicView;
import com.cit.algorithmVisualize.markInfo.FuncMarkInfo;
import com.cit.algorithmVisualize.markInfo.PolygonMarkInfo;
import com.cit.algorithmVisualize.mathStructure.IFunc;
import com.cit.algorithmVisualize.mathStructure.PointD;
import com.cit.algorithmVisualize.mathStructure.Polygon;
import com.cit.algorithmVisualize.mathStructure.Polynomial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/*
    @description 2차원 평면을 시각화하는 클래스
 */
public class CPlane extends ArrayList<PointD> {
    //아래 정보를 Z-space 정규화를 적용하기 위해 사용되는 변수들이다.
    //현재 점들의 평균
    private PointD mean = new PointD();
    //현재 점들의 분산
    private PointD variance = new PointD();
    //현재 점들의 표준 편차
    private PointD stDev = new PointD();
    //정규화한 현재 점들의 x 범위
    private Range<Double> range_norm_x = new Range<>(0.0, 0.0);
    //정규화한 현재 점들의 y 범위
    private Range<Double> range_norm_y = new Range<>(0.0,0.0);
    //다각형 표시에 대한 정보를 가지고 있는 리스트
    private List<PolygonMarkInfo> mPolygonMarks = new ArrayList<>();
    //함수 표시에 대한 정보를 가지고 있는 리스트

    private boolean isAutoXYRange = true;
    private Range<Double> custom_x_range = new Range<>(0.0, 0.0);
    private Range<Double> custom_y_range = new Range<>(0.0, 0.0);

    private List<FuncMarkInfo> mFuncMarks = new ArrayList<>();
    private GraphicView mPanel;

    //region constructor
    public CPlane(GraphicView panel){
        this.mPanel = panel;
    }
    public CPlane(GraphicView panel, List<PointD> array){
        this(panel);
        for(PointD p : array) this.add(p);
    }

    public CPlane setGraphicPanel(GraphicView panel){
        this.mPanel = panel;
        return this;
    }
    //endregion

    public CPlane setAutoXYRange(boolean flag){isAutoXYRange = flag; return this;}
    public CPlane setXRange(double xl, double xr){custom_x_range = new Range<>(xl, xr); return this;}
    public CPlane setYRange(double yl, double yr){custom_y_range = new Range<>(yl, yr); return this;}

    @Override
    public boolean add(PointD x){
        return super.add(x);
    }

    @Override
    public void add(int idx, PointD x){
        super.add(idx, x);
    }
    @Override
    public boolean addAll(Collection<? extends PointD> c){
        for(PointD x : c)this.add(x);
        return false;
    }

    @Override
    public boolean remove(Object x){
        int idx = super.indexOf(x);
        return super.remove(x);
    }
    @Override
    public PointD remove(int idx){
        return super.remove(idx);
    }
    @Override
    public void clear(){
        super.clear();
    }

    private void update(){
        mean = new PointD();
        int n = this.size();
        for(PointD p : this){
            mean.x += p.x; mean.y += p.y;
        }
        for(PolygonMarkInfo mark : mPolygonMarks){
            for(Point p : mark.polygon){
                mean.x += p.x; mean.y += p.y;
                n++;
            }
        }
        mean.x /= n; mean.y /= n;

        variance = new PointD();
        for(PointD p : this){
            double dx = p.x - mean.x, dy = p.y-mean.y;
            dx*= dx; dy*=dy;
            variance.x += dx; variance.y += dy;
        }
        for(PolygonMarkInfo mark : mPolygonMarks){
            for(Point p : mark.polygon){
                double dx = p.x - mean.x, dy = p.y-mean.y;
                dx*= dx; dy*=dy;
                variance.x += dx; variance.y += dy;
            }
        }
        variance.x /= n; variance.y /= n;

        stDev.x = Math.sqrt(variance.x);
        stDev.y = Math.sqrt(variance.y);
    }
    private PointD normalize(PointD point){
        if(!isAutoXYRange){
            double mx = (range_norm_x.getUpper() - range_norm_x.getLower()) / 2.0,
                    my = (range_norm_y.getUpper() - range_norm_y.getLower()) / 2.0;
            return new PointD(point.x, point.y);
        }else
            return new PointD((point.x-mean.x)/stDev.x,
                (point.y-mean.y)/stDev.y);
    }
    private PointD normalize(Point point){
        return normalize(new PointD(point));
    }
    private Range<Double> getXRange(boolean isNormal){
        if(!isAutoXYRange) return custom_x_range;

        if(isNormal) update();
        List<Double> x = new ArrayList<>();
        for(PointD p : this){
            if(isNormal) x.add(normalize(p).x);
            else x.add(p.x);
        }
        for(PolygonMarkInfo mark : mPolygonMarks){
            for(Point p : mark.polygon){
                if(isNormal) x.add(normalize(p).x);
                else x.add(1.0*p.x);
            }
        }
        double l = x.stream().min(Double::compare).get(), r =x.stream().max(Double::compare).get();
        double d = r-l;
        return new Range<Double>(l-d*0.1, r+d*0.1);
    }
    private Range<Double> getXRange(){
        return getXRange(false);
    }
    private Range<Double> getYRange(boolean isNormal){
        if(!isAutoXYRange) return custom_y_range;

        if(isNormal) update();
        List<Double> y = new ArrayList<>();
        for(PointD p : this){
            if(isNormal) y.add(normalize(p).y);
            else y.add(p.y);
        }
        for(PolygonMarkInfo mark : mPolygonMarks){
            for(Point p : mark.polygon){
                if(isNormal) y.add(normalize(p).y);
                else y.add(1.0*p.y);
            }
        }
        double l = y.stream().min(Double::compare).get(), r =y.stream().max(Double::compare).get();
        double d = r-l;
        return new Range<Double>(l-d*0.1, r+d*0.1);
    }
    private Range<Double> getYRange(){return getYRange(false);}
    private Rect getViewRect(){
        int gap = getLayoutGap();
        Rect r = new Rect(mPanel.mRect);
        return new Rect(r.left+gap,r.top+gap,r.right-gap,r.bottom-gap);
    }

    //region PolygonMarks
    public CPlane addPolygonMark(Polygon poly, int color_wire, int color_fill, int alpha){
        mPolygonMarks.add(new PolygonMarkInfo(poly, color_wire, color_fill, alpha));
        return this;
    }
    public CPlane addPolygonMark(Polygon poly, int color_wire, int color_fill){
        return addPolygonMark(poly, color_wire, color_fill, 0);
    }
    public CPlane addPolygonMark(Polygon poly, int color_wire){
        return addPolygonMark(poly, color_wire, Color.TRANSPARENT);
    }
    public CPlane addPolygonMark(Polygon poly){
        return addPolygonMark(poly, Color.BLACK);
    }
    public CPlane removePolygonMark(Polygon poly){
        mPolygonMarks.remove(poly);
        return this;
    }
    public CPlane clearPolygonMarks(){
        mPolygonMarks.clear();
        return this;
    }
    //endregion
    //region FuncMarks
    public CPlane addFuncMark(IFunc func, int color){
        mFuncMarks.add(new FuncMarkInfo(func, color));
        return this;
    }
    public CPlane addFuncMark(IFunc func){
        return addFuncMark(func, Color.BLACK);
    }
    public CPlane addPolynomialMark(Polynomial poly, int color){
        return addFuncMark(poly, color);
    }
    public CPlane addPolynomialMark(Polynomial poly){
        return addPolynomialMark(poly, Color.BLACK);
    }
    public CPlane clearFuncMarks(){
        mFuncMarks.clear();
        return this;
    }
    //endregion

    //region draw
    private int getLayoutGap(){
        return Math.min(mPanel.width()/10, mPanel.height()/10);
    }
    private int getLineStrokeWidth(){return getSymbolSize();}
    private Point getLayoutPivot(){
        return new Point(getLayoutGap(), mPanel.height()-getLayoutGap());
    }
    private int getSymbolSize(){return Math.min(mPanel.width()/75, mPanel.height()/75);}
    private Point getPointToView(PointD point){
        double sx = range_norm_x.getLower(), sy = range_norm_y.getLower();
        double ex = range_norm_x.getUpper(), ey = range_norm_y.getUpper();

        point = normalize(point);
        double px = point.x - sx, py = point.y - sy;
        double dx = ex-sx, dy = ey-sy;
        double rx = px/dx, ry = py/dy;

        int gap = getLayoutGap();
        int width = mPanel.width() - 2*gap, height = mPanel.height()-2*gap;
        return new Point((int)(width*rx)+gap, height-(int)(height*ry)+gap);
    }
    private Point getPointToView(double x, double y){
        return getPointToView(new PointD(x,y));
    }
    private void drawXLayout(){
        int gap = getLayoutGap();
        Range<Double> range_x = getXRange();
        double sx = range_x.getLower(), ex = range_x.getUpper();
        int isx = (int)Math.ceil(sx), iex = (int)Math.floor(ex);

        int dx = Math.max(1, (iex-isx)/10);

        for(int x=isx; x <= iex; x += dx){
            int px = getPointToView(x, 0).x;

            Point point_u = new Point(px, gap),
                    point_d = new Point(px, mPanel.height()-gap);
            mPanel.drawLine(point_u, point_d, getLineStrokeWidth()/2, Color.GRAY);

            Rect rect_text = mPanel.getTextRect(Integer.toString(x), 20);
            point_d.y += 2*rect_text.height();
            mPanel.drawText(point_d, Integer.toString(x), 20);
        }

        Point start = new Point(getLayoutPivot());
        Point end = new Point(start);
        end.x += mPanel.width() - 2*gap;
        mPanel.drawLine(start, end, getLineStrokeWidth());
    }
    private void drawYLayout(){
        int gap = getLayoutGap();
        Range<Double> range_y = getYRange();
        double sy = range_y.getLower(), ey = range_y.getUpper();
        int isy = (int)Math.ceil(sy), iey = (int)Math.floor(ey);

        int dy = Math.max(1, (iey-isy)/10);

        for(int y=isy; y <= iey; y += dy){
            int py = getPointToView(0, y).y;

            Point point_l = new Point(gap, py),
                    point_r = new Point(mPanel.width()-gap, py);
            mPanel.drawLine(point_l, point_r, getLineStrokeWidth()/2, Color.GRAY);

            Rect rect_text = mPanel.getTextRect(Integer.toString(y), 20);
            point_l.x -= gap/3 + rect_text.width()/2;
            mPanel.drawText(point_l, Integer.toString(y), 20);
        }

        Point start = new Point(getLayoutPivot());
        Point end = new Point(start);
        end.y = getLayoutGap();
        mPanel.drawLine(start, end, getLineStrokeWidth());
    }
    private void drawFuncMarks(){
        Range<Double> range_x = getXRange(), range_y = getYRange();
        int delta = mPanel.width()-2*getLayoutGap();
        double xl = range_x.getLower();
        double dx = (range_x.getUpper() - range_x.getLower())/delta;
        for(FuncMarkInfo mark : mFuncMarks){
            List<Double> ys = new ArrayList<>(delta);

            Paint paint = new Paint();
            paint.setColor(mark.color);
            paint.setStrokeWidth(getLineStrokeWidth());

            for(int i= 0 ; i <= delta; i++){
                double x = xl+dx*i;
                double y = mark.func.f(x);
                ys.add(y);

                if(i > 0 && range_y.contains(ys.get(i-1)) && range_y.contains(ys.get(i))){
                    double px = xl + (i-1)*dx, py = ys.get(i-1);
                    mPanel.drawLine(getPointToView(new PointD(px, py)),
                            getPointToView(new PointD(x, y)),
                            paint);
                }
            }
        }
    }
    private void drawPolygonMarks(){
        for(PolygonMarkInfo mark : mPolygonMarks){
            Paint paint_wire = new Paint();
            paint_wire.setStrokeWidth(getLineStrokeWidth());
            paint_wire.setColor(mark.color_wire);

            Paint paint_fill = new Paint();
            paint_fill.setColor(mark.color_fill);
            paint_fill.setAlpha(mark.alpha);

            Polygon poly = new Polygon(mark.polygon);
            for(int i=0; i < poly.size(); i++){
                Point pt = poly.get(i);
                Point to = getPointToView(new PointD(pt.x, pt.y));
                poly.set(i, to);
            }

            mPanel.drawPolygon(poly, paint_wire, paint_fill);
        }
    }
    private void drawLayout(){
        drawXLayout();
        drawYLayout();
    }
    private void drawPoints(){
        for(PointD p : this){
            mPanel.drawFillCircle(getPointToView(p)
                    , getSymbolSize(), Color.BLACK);
        }
    }
    public CPlane draw(){
        range_norm_x = getXRange(true);
        range_norm_y = getYRange(true);
        mPanel.renderClear();

        drawLayout();
        drawPoints();
        drawFuncMarks();
        drawPolygonMarks();

        mPanel.draw();
        return this;
    }
    //endregion
}
