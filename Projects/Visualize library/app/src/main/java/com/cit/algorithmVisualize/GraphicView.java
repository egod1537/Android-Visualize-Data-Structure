package com.cit.algorithmVisualize;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.cit.algorithmVisualize.mathStructure.Polygon;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

/*
    @description 시각화되는 알고리즘의 캔버스를 정의해주는 클래스이다.
    렌더 관련 함수들이 존재하며, 시각화하는 알고리즘의 클래스들은 GraphicView의 함수들을 사용하여 GraphicView에 렌더 명령을 내린다.
 */
public class GraphicView extends View {
    public Rect mRect;

    //다음 렌더에 그려질 명령들을 저장하는 큐
    private Queue<Consumer<Canvas>> mDrawQueue = new LinkedList<>();
    //각 렌더마다 그린 명령들을 저장하는 큐
    private Queue<Queue<Consumer<Canvas>>> mRenderQueue = new LinkedList<>();

    private boolean isTouch = true;

    //region constructor
    public GraphicView(Context context) {
        super(context);
    }

    public GraphicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        renderClear();
    }

    public GraphicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //endregion

    //GraphicView의 Rect를 정해주는 함수, 객체를 생성하고 나서 무조건 호출해야되는 함수이다.
    public GraphicView initialize(int x, int y, int width, int height){
        this.mRect = new Rect(x,y,x+width,y+height);
        renderClear();
        return this;
    }
    public GraphicView initialize(Rect rect){
        return initialize(rect.left, rect.top, rect.width(), rect.height());
    }

    public GraphicView setNextDrawOnTouch(boolean isTouch){
        this.isTouch = isTouch;
        return this;
    }

    @Override
    public void onDraw(Canvas canvas){
        if(mRenderQueue.isEmpty()) return;
        super.onDraw(canvas);
        Queue<Consumer<Canvas>> queue = mRenderQueue.poll();
        for (Consumer action: queue){
            action.accept(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isTouch){
            int x = (int)event.getX(), y = (int)event.getY();
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    if(mRect.contains(x, y)) render();
                    break;
            }
        }
        return true;
    }

    public int width(){return mRect.width();}
    public int height(){return mRect.height(); }
    public boolean contains(Point point){
        return mRect.contains(point.x, point.y);
    }

    //렌더하는 함수
    public void render(){
        if(mRenderQueue.isEmpty()) return;
        invalidate();
    }

    public void renderClear(){
        AddQueue(x -> {
            Canvas canvas = (Canvas) x;

            Paint paint = new Paint();
            paint.setColor(Color.LTGRAY);

            canvas.drawRect(mRect.left, mRect.top, mRect.right, mRect.bottom, paint);
        });
    }
    //region Line
    public void drawLine(int sx, int sy, int ex, int ey, Paint paint_line){
        AddQueue(c -> {
            Canvas canvas = (Canvas) c;

            int px = mRect.left, py = mRect.top;
            canvas.drawLine(sx+px,sy+py,ex+px,ey+py,paint_line);
        });
    }
    public void drawLine(Point s, Point e, Paint paint_line){
        drawLine(s.x, s.y, e.x, e.y, paint_line);
    }
    public void drawLine(int sx, int ex, int sy, int ey, int stroke, int color, int alpha){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(stroke);
        paint.setAlpha(alpha);
        drawLine(sx, ex, sy, ey, paint);
    }
    public void drawLine(Point s, Point e, int stroke, int color, int alpha){
        drawLine(s.x, s.y,e.x,e.y,stroke,color,alpha);
    }
    public void drawLine(int sx, int ex, int sy, int ey, int stroke, int color){
        drawLine(sx,ex,sy,ey,stroke,color,255);
    }
    public void drawLine(Point s, Point e, int stroke, int color){
        drawLine(s.x,s.y,e.x,e.y,stroke,color);
    }
    public void drawLine(int sx, int ex, int sy, int ey, int stroke){
        drawLine(sx,ex,sy,ey,stroke,Color.BLACK);
    }
    public void drawLine(Point s, Point e, int stroke){
        drawLine(s.x,s.y,e.x,e.y,stroke);
    }
    public void drawLine(int sx, int ex, int sy, int ey){
        drawLine(sx,ex,sy,ey,1);
    }
    public void drawLine(Point s, Point e){
        drawLine(s.x,s.y,e.x,e.y);
    }
    //endregion

    //region Rect
    //region WireRect
    public  void drawWireRect(int x, int y, int w, int h, Paint paint_wire){
        AddQueue(c -> {
            Canvas canvas = (Canvas) c;
            int px = mRect.left, py = mRect.top;

            paint_wire.setStyle(Paint.Style.STROKE);
            canvas.drawRect(x-w/2+px,y-h/2+py,x+w/2+px,y+h/2+py,paint_wire);
        });
    }
    public void drawWireRect(Rect rect, Paint paint_wire){
        drawWireRect(rect.left, rect.top, rect.width(), rect.height(), paint_wire);
    }

    public void drawWireRect(int x, int y, int w, int h, int stroke, int color_stroke){
        Paint paint = new Paint();
        paint.setStrokeWidth(stroke);
        paint.setColor(color_stroke);
        drawWireRect(x,y,w,h,paint);
    }
    public void drawWireRect(Rect rect, int stroke, int color_stroke){
        drawWireRect(rect.left, rect.top, rect.width(), rect.height(), stroke, color_stroke);
    }

    public void drawWireRect(int x, int y, int w, int h, int stroke){drawWireRect(x,y,w,h,stroke, Color.BLACK);}
    public void drawWireRect(Rect rect, int stroke){
        drawWireRect(rect.left, rect.top, rect.width(), rect.height(), stroke,Color.BLACK);
    }

    public void drawWireRect(int x, int y, int w, int h){drawWireRect(x,y,w,h,1);}
    public void drawWireRect(Rect rect){
        drawWireRect(rect.left, rect.top, rect.width(), rect.height(), 1);
    }
    //endregion
    //region FillRect
    public void drawFillRect(int x, int y, int w, int h, Paint paint_fill){
        AddQueue(c -> {
            Canvas canvas = (Canvas) c;
            int px = mRect.left, py = mRect.top;

            canvas.drawRect(x-w/2.0f+px,y-h/2.0f+py,x+w/2.0f+px,y+h/2.0f+py,paint_fill);
        });
    }
    public void drawFillRect(Rect rect, Paint paint_fill){
        drawFillRect(rect.left, rect.top, rect.width(), rect.height(), paint_fill);
    }

    public void drawFillRect(int x, int y, int w, int h, int color_fill, int alpha){
        Paint fill_paint  = new Paint();
        fill_paint.setColor(color_fill);
        fill_paint.setAlpha(alpha);
        drawFillRect(x,y,w,h,fill_paint);
    }
    public void drawFillRect(Point s, Point e, int color_fill, int alpha){
        drawFillRect(s.x,s.y,e.x-s.x,e.y-s.y,color_fill,alpha);
    }

    public void drawFillRect(int x, int y, int w, int h, int color_fill){
        drawFillRect(x,y,w,h,color_fill,255);
    }
    public void drawFillRect(Point s, Point e, int color_fill){
        drawFillRect(s.x,s.y,e.x-s.x,e.y-s.y,color_fill,255);
    }

    public void drawFillRect(int x, int y, int w, int h){
        drawFillRect(x,y,w,h,Color.BLACK);
    }
    public void drawFillRect(Point s, Point e){
        drawFillRect(s.x,s.y,e.x-s.x,e.y-s.y);
    }
    //endregion
    public void drawRect(Rect rect, Paint paint_wire, Paint paint_fill){
        drawRect(rect.left, rect.top, rect.width(), rect.height(), paint_wire, paint_fill);
    }
    public void drawRect(Point ps, Point pe, Paint paint_wire, Paint paint_fill){
        drawRect(new Rect(ps.x, ps.y, pe.x, pe.y), paint_wire, paint_fill);
    }
    public void drawRect(int x, int y, int w, int h, Paint paint_wire, Paint paint_fill){
        drawFillRect(x,y,w,h,paint_fill);
        drawWireRect(x,y,w,h,paint_wire);
    }
    public void drawRect(int x, int y, int w, int h, int stroke, int color_stroke, int color_fill, int alpha){
        Paint paint_fill = new Paint();
        paint_fill.setColor(color_fill);
        paint_fill.setAlpha(alpha);
        drawFillRect(x,y,w,h,paint_fill);

        Paint paint_wire = new Paint();
        paint_wire.setStrokeWidth(stroke);
        paint_wire.setColor(color_stroke);
        drawWireRect(x,y,w,h, paint_wire);
    }
    public void drawRect(Point s, Point e, int stroke, int color_stroke, int color_fill, int alpha){
        drawRect(s.x,s.y,e.x-s.x,e.y-s.y,stroke,color_stroke,color_fill,alpha);
    }

    public void drawRect(int x, int y, int w, int h, int stroke, int color_stroke, int color_fill){
        drawRect(x,y,w,h,stroke,color_stroke,color_fill, 255);
    }
    public void drawRect(Point s, Point e, int stroke, int color_stroke, int color_fill){
        drawRect(s.x,s.y,e.x-s.x,e.y-s.y,stroke,color_stroke,color_fill);
    }

    public void drawRect(int x, int y, int w, int h, int stroke, int color_stroke){
        drawRect(x,y,w,h,stroke,color_stroke, Color.WHITE);
    }
    public void drawRect(Point s, Point e, int stroke, int color_stroke){
        drawRect(s.x,s.y,e.x-s.x,e.y-s.y,stroke,color_stroke);
    }

    public void drawRect(int x, int y, int w, int h, int stroke){
        drawRect(x,y,w,h,stroke,Color.BLACK);
    }
    public void drawRect(Point s, Point e, int stroke){
        drawRect(s.x,s.y,e.x-s.x,e.y-s.y,stroke);
    }

    public void drawRect(int x, int y, int w, int h){
        drawRect(x,y,w,h,1);
    }
    public void drawRect(Point s, Point e){
        drawRect(s.x,s.y,e.x-s.x,e.y-s.y);
    }

    //endregion

    //region Polygon
    //region WirePolygon
    public void drawWirePolygon(Polygon poly, Paint paint_wire){
        int px = mRect.left, py = mRect.top;

        Path sidePath = new Path();
        sidePath.reset();

        int sz = poly.size();
        for(int i=0; i < sz; i++){
            Point p = poly.get(i);
            int x = px+p.x, y = py+p.y;

            if(i == 0) sidePath.moveTo(x,y);
            else sidePath.lineTo(x,y);
        }
        sidePath.close();
        paint_wire.setStyle(Paint.Style.STROKE);

        AddQueue(c -> {
            Canvas canvas = (Canvas) c;
            canvas.drawPath(sidePath, paint_wire);
        });
    }
    public void drawWirePolygon(List<Point> poly, Paint paint_wire){
        drawWirePolygon(new Polygon(poly), paint_wire);
    }
    public void drawWirePolygon(Polygon poly, int stroke, int color_stroke){
        Paint paint_wire = new Paint();
        paint_wire.setStrokeWidth(stroke);
        paint_wire.setColor(color_stroke);
        drawWirePolygon(poly, paint_wire);
    }
    public void drawWirePolygon(List<Point> poly, int stroke, int color_stroke){
        drawWirePolygon(new Polygon(poly), stroke, color_stroke);
    }

    public void drawWirePolygon(Polygon poly, int stroke){
        drawWirePolygon(poly,stroke,Color.BLACK);
    }
    public void drawWirePolygon(List<Point> poly, int stroke){
        drawWirePolygon(poly,stroke,Color.BLACK);
    }

    public void drawWirePolygon(Polygon poly){
        drawWirePolygon(poly,1);
    }
    public void drawWirePolygon(List<Point> poly){
        drawWirePolygon(poly,1);
    }
    //endregion
    //region FillPolygon
    public void drawFillPolygon(Polygon poly, Paint paint_fill){
        int px = mRect.left, py = mRect.top;

        Path sidePath = new Path();
        sidePath.reset();

        int sz = poly.size();
        for(int i=0; i < sz; i++){
            Point p = poly.get(i);
            int x = px+p.x, y = py+p.y;

            if(i == 0) sidePath.moveTo(x,y);
            else sidePath.lineTo(x,y);
        }
        sidePath.close();

        AddQueue(c -> {
            Canvas canvas = (Canvas) c;
            canvas.drawPath(sidePath, paint_fill);
        });
    }
    public void drawFillPolygon(List<Point> poly, Paint paint_fill){
        drawFillPolygon(new Polygon(poly), paint_fill);
    }
    public void drawFillPolygon(Polygon poly, int color_fill, int alpha){
        Paint paint_fill = new Paint();
        paint_fill.setColor(color_fill);
        paint_fill.setAlpha(alpha);
        drawFillPolygon(poly, paint_fill);
    }
    public void drawFillPolygon(List<Point> poly, int color_fill, int alpha){
        drawFillPolygon(new Polygon(poly), color_fill, alpha);
    }

    public void drawFillPolygon(Polygon poly, int color_fill){
        drawFillPolygon(poly,color_fill,255);
    }
    public void drawFillPolygon(List<Point> poly, int color_fill){
        drawFillPolygon(poly,color_fill,255);
    }

    public void drawFillPolygon(Polygon poly){
        drawFillPolygon(poly,Color.BLACK);
    }
    public void drawFillPolygon(List<Point> poly){
        drawFillPolygon(poly,Color.BLACK);
    }
    //endregion
    public void drawPolygon(Polygon poly, Paint paint_wire, Paint paint_fill){
        drawFillPolygon(poly, paint_fill);
        drawWirePolygon(poly, paint_wire);
    }
    public void drawPolygon(List<Point> poly, Paint paint_wire, Paint paint_fill){
        drawPolygon(new Polygon(poly), paint_wire, paint_fill);
    }
    public void drawPolygon(Polygon poly, int stroke, int color_stroke, int color_fill, int alpha){
        drawFillPolygon(poly, color_fill, alpha);
        drawWirePolygon(poly, stroke, color_stroke);
    }
    public void drawPolygon(List<Point> poly, int stroke, int color_stroke, int color_fill, int alpha){
        drawPolygon(new Polygon(poly), stroke, color_stroke, color_fill, alpha);
    }

    public void drawPolygon(Polygon poly, int stroke, int color_stroke, int color_fill){
        drawPolygon(poly,stroke,color_stroke,color_fill,255);
    }
    public void drawPolygon(List<Point> poly, int stroke, int color_stroke, int color_fill){
        drawPolygon(new Polygon(poly), stroke, color_stroke, color_fill, 255);
    }

    public void drawPolygon(Polygon poly, int stroke, int color_stroke){
        drawPolygon(poly,stroke,color_stroke, Color.WHITE);
    }
    public void drawPolygon(List<Point> poly, int stroke, int color_stroke){
        drawPolygon(new Polygon(poly), stroke, color_stroke, Color.WHITE);
    }

    public void drawPolygon(Polygon poly, int stroke){
        drawPolygon(poly,stroke,Color.BLACK);
    }
    public void drawPolygon(List<Point> poly, int stroke){
        drawPolygon(new Polygon(poly), stroke, Color.BLACK);
    }

    public void drawPolygon(Polygon poly){
        drawPolygon(poly,1);
    }
    public void drawPolygon(List<Point> poly){
        drawPolygon(new Polygon(poly), 1);
    }
    //endregion

    public Rect getTextRect(String text, int size_text){
        Paint paint = new Paint();
        paint.setTextSize(size_text);

        Rect ret = new Rect();
        paint.getTextBounds(text, 0, text.length(), ret);
        return ret;
    }
    public void drawText(int x, int y, String text, Paint paint_text){
        paint_text.setTextAlign(Paint.Align.CENTER);

        float biasY = paint_text.ascent()/2 + paint_text.descent()/2;
        AddQueue(c -> {
            Canvas canvas = (Canvas) c;
            int px = mRect.left, py = mRect.top;
            canvas.drawText(text, px+x, py+y-biasY, paint_text);
        });
    }
    public void drawText(Point point, String text, Paint paint_text){
        drawText(point.x, point.y, text, paint_text);
    }
    public void drawText(int x, int y, String text, int text_size, int text_color){
        Paint paint_text = new Paint();
        paint_text.setColor(text_color);
        paint_text.setTextSize(text_size);
        drawText(x,y,text,paint_text);
    }
    public void drawText(Point point, String text, int text_size, int text_color){
        drawText(point.x, point.y, text, text_size, text_color);
    }
    public void drawText(Point point, String text, int text_size){
        drawText(point.x, point.y, text, text_size, Color.BLACK);
    }
    public void drawRectWithText(Rect rect, String text, Paint paint_wire, Paint paint_fill, Paint paint_text){
        drawRectWithText(rect.left, rect.top, rect.width(), rect.height(),
                text, paint_wire, paint_fill, paint_text);
    }
    public void drawRectWithText(int x, int y, int w, int h, String text,
                                 Paint paint_stroke, Paint paint_fill, Paint paint_text){
        drawRect(x,y,w,h, paint_stroke, paint_fill);
        drawText(x,y,text, paint_text);
    }
    public void drawRectWithText(Point point, int w, int h, String text,
                                 Paint paint_stroke, Paint paint_fill, Paint paint_text){
        drawRectWithText(point.x,point.y, w, h, text,
                paint_stroke, paint_fill, paint_text);
    }
    public void drawRectWithText(Point point, int len, String text,
                                 Paint paint_stroke, Paint paint_fill, Paint paint_text){
        drawRectWithText(point, len, len, text,
                paint_stroke, paint_fill, paint_text);
    }
    public void drawRectWithText(int x, int y, int w, int h, String text){
        drawWireRect(x,y,w,h, 20);
        drawText(x,y,text, 30, Color.RED);
    }
    public void drawRectWithText(int x, int y, int len, String text){
        drawRectWithText(x,y,len,len, text);
    }
    public void drawRectWithText(Point point, int w, int h, String text){
        drawRectWithText(point.x, point.y, w, h, text);
    }
    public void drawRectWithText(Point point, int len, String text){
        drawRectWithText(point, len, len, text);
    }

    //region Circle
    //region Wire Circle
    public void drawWireCircle(Point point, int r, Paint paint){
        paint.setStyle(Paint.Style.STROKE);
        AddQueue(c -> {
            Canvas canvas = (Canvas) c;
            int px = mRect.left, py = mRect.top;
            canvas.drawCircle(point.x+px, point.y+py, r, paint);
        });
    }
    public void drawWireCircle(Point point, int r, int stroke, int color_wire){
        Paint paint = new Paint();
        paint.setStrokeWidth(stroke);
        paint.setColor(color_wire);
        drawWireCircle(point, r, paint);
    }
    public void drawWireCircle(Point point, int r, int stroke){
        drawWireCircle(point, r, stroke, Color.BLACK);
    }
    public void drawWireCircle(Point point, int r){
        drawWireCircle(point, r);
    }

    //endregion
    //region Fill Circle
    public void drawFillCircle(Point point, int r, Paint paint){
        paint.setStyle(Paint.Style.FILL);
        AddQueue(c -> {
            Canvas canvas = (Canvas) c;
            int px = mRect.left, py = mRect.top;
            canvas.drawCircle(point.x+px, point.y+py, r, paint);
        });
    }
    public void drawFillCircle(Point point, int r, int color_fill){
        Paint paint = new Paint();
        paint.setColor(color_fill);
        drawFillCircle(point, r, paint);
    }
    public void drawFillCircle(Point point, int r){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        drawFillCircle(point, r, paint);
    }
    //endregion
    public void drawCircle(Point point, int r, Paint paint_wire, Paint paint_fill){
        drawFillCircle(point, r, paint_fill);
        drawWireCircle(point, r, paint_wire);
    }
    //endregion
    public void draw(){
        mRenderQueue.add(new LinkedList<>(mDrawQueue));
        mDrawQueue.clear();
    }
    private void AddQueue(Consumer function){
        mDrawQueue.add(function);
    }
}
