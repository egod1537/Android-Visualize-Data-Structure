package com.cit.algorithmVisualize;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Pair;

import com.cit.algorithmVisualize.dataStructure.CGraph;
import com.cit.algorithmVisualize.dataStructure.CGrid;
import com.cit.algorithmVisualize.dataStructure.CList;
import com.cit.algorithmVisualize.mathStructure.Edge;
import com.cit.algorithmVisualize.mathStructure.PointD;
import com.cit.algorithmVisualize.mathStructure.Polygon;
import com.cit.algorithmVisualize.dataStructure.CPlane;
import com.cit.algorithmVisualize.mathStructure.PolygonD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
/*
    @description 시각화 클래스를 활용한 알고리즘 시각화 예제 클래스
 */
public class Examples {
    public static void fibonacci(GraphicView panel){
        CList<Integer> dp = new CList<>(panel, Arrays.asList(1, 1));
        dp.draw();
        for(int i=1; i <= 10; i++){
            int sz = dp.size();

            dp.clearColors();
            dp.setColors(new ArrayList<Integer>(Arrays.asList(sz-2,sz-1)), Color.RED);
            dp.draw();

            dp.clearColors();
            dp.add(dp.get(sz-2)+dp.get(sz-1));
            dp.setColor(sz, Color.GREEN);
            dp.draw();
        }
    }

    public static void binarySearch(GraphicView panel){
        CList<Integer> arr = new CList<>(panel, Arrays.asList(1,3,7,8,11,13,14,17));
        arr.draw();

        int x = 10;
        int lo = 0, hi = arr.size() - 1;
        while (lo + 1 < hi) {
            int mid = lo + hi >> 1;
            arr.addRangeMark(lo, hi, "now")
                    .addLineMark(lo, "low")
                    .addLineMark(hi+1, "high")
                    .addRectMarks(lo,hi,Color.RED, Color.TRANSPARENT)
                    .draw();

            arr.addCheckMark(mid, false)
                    .setColor(mid, Color.GREEN)
                    .draw();

            if (arr.get(mid) < x) lo = mid;
            else hi = mid;

            arr.clearMarks().clearColors();
        }

        arr.addRangeMark(hi,hi,"ans")
                .setColor(hi,Color.GREEN)
                .draw();
    }

    public static void prefixSum1D(GraphicView panel){
        CList<Integer> arr = new CList<>(panel,Arrays.asList(0, 3, 1, 6, -2, 4, 6, 11, 12));
        arr.draw();
        for(int i=1; i < arr.size(); i++){
            arr.clearColors()
                .clearMarks()
                .addCheckMark(i)
                .addRectMarks(0, i-1, Color.BLUE)
                .addRectMark(i, Color.RED)
                .draw();
            arr.set(i, arr.get(i)+arr.get(i-1));
            arr.setColor(i,Color.GREEN)
                .draw();
        }

        List<Pair<Integer,Integer>> query = Arrays.asList(
                new Pair<>(1, 3),
                new Pair<>(3, 8),
                new Pair<>(4, 4));

        for(Pair<Integer, Integer> q : query){
            int l = q.first, r = q.second;
            arr.clearColors()
                .clearMarks()
                .setColor(r, Color.RED)
                .addRectMarks(0, r, Color.RED)
                .addCheckMark(r)
                .draw()
                .setColor(l-1, Color.BLUE)
                .addRectMarks(0 ,l-1, Color.BLUE)
                .addCheckMark(l-1)
                .draw()
                .clearColors()
                .clearMarks()
                .addRectMarks(l,r,Color.GREEN)
                .addCheckMark(l)
                .addCheckMark(r)
                .draw();
        }
    }

    public static void bfs(GraphicView panel){
        int n = 5;
        CGrid<Integer> grid = new CGrid(panel, Arrays.asList(
                Arrays.asList(0,1,1,1,0),
                Arrays.asList(0,0,0,1,0),
                Arrays.asList(1,0,0,0,0),
                Arrays.asList(0,0,0,1,0),
                Arrays.asList(0,1,0,0,0)
        )).draw();
        List<List<Boolean>> vit = new ArrayList<>();
        for(int i=0; i < n; i++){
            List<Boolean> list = new ArrayList<>(Collections.nCopies(n, false));
            vit.add(list);
        }

        int dx[] = {0,-1,0,1};
        int dy[] = {1,0,-1,0};

        Queue<Point> q = new LinkedList<>();
        q.add(new Point(0,0));
        vit.get(0).set(0, true);
        while(!q.isEmpty()){
            Point top = q.poll();
            grid.setColor(top.x,top.y, Color.BLUE);
            for(int i=0 ; i < 4; i++){
                int nx = top.x+dx[i] ,ny = top.y+dy[i];
                if(nx < 0 || ny < 0 || nx >= n || ny >= n) continue;
                if(grid.get(nx,ny) == 1 || vit.get(nx).get(ny)) continue;
                vit.get(nx).set(ny, true);
                grid.setColor(nx,ny,Color.GREEN);
                q.add(new Point(nx,ny));
            }
            grid.draw();
        }
    }
    public static double ccw(PointD a, PointD b, PointD c){
        return (b.x-a.x)*(c.y-a.y)-(c.x-a.x)*(b.y-a.y);
    }
    public static double pw(double x){return x*x;}
    public static double dst(PointD a, PointD b){return pw(a.x-b.x)+pw(a.y-b.y);}
    public static boolean chk(PointD a, PointD b){
        if(a.x != b.x) return a.x < b.x;
        return a.y < b.y;
    }
    public static PointD pivot = new PointD();
    static class PointComparator implements Comparator<PointD> {

        @Override
        public int compare(PointD a, PointD b) {
            double cw = ccw(pivot, a, b);
            if(cw > 0) return -1;
            else if(cw < 0) return 1;
            else{
                if(dst(pivot, a) < dst(pivot, b)) return -1;
                else return 1;
            }
        }
    }
    public static void convexhull(GraphicView panel){
        CPlane points = new CPlane(panel, Arrays.asList(
                new PointD(1, 1),
                new PointD(1, 2),
                new PointD(1, 3),
                new PointD(2, 1),
                new PointD(2, 2),
                new PointD(2, 3),
                new PointD(3, 1),
                new PointD(3, 2)
        ));
        points.draw();
        int n = points.size();
        int mx = 0;
        for(int i=1; i <  n; i++)
            if(chk(points.get(i), points.get(mx))) mx = i;

        pivot = points.get(mx);
        points.remove(mx);
        Collections.sort(points, new PointComparator());

        points.add(0, pivot);
        PolygonD cv = new PolygonD();
        for(PointD p : points){
            cv.remove(p);
            while(cv.size() > 1 && ccw(cv.get(cv.size()-2), cv.get(cv.size()-1), p) <= 0)
                cv.remove(cv.size()-1);
            cv.add(p);
            points.addPolygonMark(new Polygon(cv), Color.BLACK, Color.GRAY, 128)
                    .draw();
        }
    }
    public static void dijkstra(GraphicView panel, GraphicView table){
        int n = 5, m = 6, st = 0;
        List<Point> v = new ArrayList<>(Arrays.asList(
                new Point(250, 100),
                new Point(125, 200),
                new Point(175, 350),
                new Point(375, 350),
                new Point(425, 200)
        ));
        List<Edge> e = new ArrayList<>(Arrays.asList(
                new Edge(0, 1, 2),
                new Edge(0, 2, 3),
                new Edge(0, 3, 1),
                new Edge(0, 4, 10),
                new Edge(1, 3, 2),
                new Edge(2, 3, 1),
                new Edge(2, 4, 1),
                new Edge(3, 4, 3)
        ));
        CList<Integer> dst = new CList<Integer>(table, Collections.nCopies(n, 100));

        CGraph gph = new CGraph(panel, v, e)
                .setDirectedGraph(true)
                .setWeightGraph(true);
        gph.draw();

        PriorityQueue<Pair<Integer,Integer>> pq = new PriorityQueue<>(
                (x, y) ->{
                    if(x.first != y.first) return y.first - x.first;
                    return y.second - x.second;
                }
        );
        pq.add(new Pair<>(0, st)); dst.set(st, 0);
        while(!pq.isEmpty()){
            Pair<Integer, Integer> top = pq.poll();
            int cost = -top.first, pos = top.second;
            gph.clearColor()
                .setVertColor(pos, Color.GREEN);
            for(Edge l : gph.G.get(pos)){
                int to = l.to, d = l.cost;
                gph.setEdgeColor(l.idx, Color.RED);
                if(dst.get(to) > cost + d){
                    dst.set(to, cost+d);
                    dst.draw();
                    pq.add(new Pair<>(-(cost+d), to));
                }
            }
            gph.draw();
        }
    }
    static class SegmentTree{
        private int n;
        private CGraph view;
        private List<Integer> t = new LinkedList<>();
        public SegmentTree(int n, CGraph view){
            this.n = n;
            this.view = view;
            for(int i=0; i < 2*n; i++) t.add(0);
            init(1,0,n-1);
            view.draw();
        }
        public void init(int num, int s, int e){
            if(s == e){
                view.setVertText(num, "["+s+","+e+"] "+t.get(num));
                return;
            }
            view.setVertText(num, "["+s+","+e+"] "+t.get(num));
            int mid = (s+e)/2;
            init(2*num, s, mid);
            init(2*num+1,mid+1, e);
        }
        public int upt(int num, int s, int e, int idx, int p){
            if(idx < s || e < idx) return t.get(num);
            if(s == e){
                t.set(num, t.get(num)+p);
                view.setVertColor(num, Color.GREEN);
                view.setVertText(num, "["+s+","+e+"] "+t.get(num))
                    .draw();
                return t.get(num);
            }
            int mid = (s+e)/2;
            t.set(num, upt(2*num,s,mid,idx,p)+upt(2*num+1,mid+1,e,idx,p));
            view.setVertColor(num, Color.GREEN);
            view.setVertText(num, "["+s+","+e+"] "+t.get(num))
                    .draw();
            return t.get(num);
        }
        public void upt(int idx, int p){
            view.setVertColor()
                    .draw();
            upt(1,0,n-1, idx, p);
            view.setVertColor()
                    .draw();
        }
        public int qry(int num, int s, int e, int l, int r){
            if(r < s || e< l) return 0;
            if(l <= s && e <= r){
                view.setVertColor(num, Color.BLUE)
                        .draw();
                return t.get(num);
            }
            int mid = (s+e)/2;
            int ret = qry(2*num,s,mid,l,r)+qry(2*num+1,mid+1,e,l,r);
            view.setVertColor(num, Color.BLUE)
                    .draw();
            return ret;
        }
        public int qry(int l, int r){
            view.setVertColor()
                    .draw();
            int ret = qry(1,0,n-1,l,r);
            view.setVertColor()
                    .draw();
            return ret;
        }
    }
    public static void segmentTree(GraphicView view){
        CGraph gph = new CGraph(view);
        List<Point> v = new ArrayList<>(Arrays.asList(
                new Point(0,0),
                new Point(500, 100),

                new Point(250, 200),
                new Point(750, 200),

                new Point(125, 300),
                new Point(375, 300),
                new Point(625, 300),
                new Point(875, 300),

                new Point(62, 400),
                new Point(187, 400),
                new Point(313, 400),
                new Point(437, 400),
                new Point(563, 400),
                new Point(687, 400),
                new Point(813, 400),
                new Point(937, 400)
        ));
        List<Edge> e = new ArrayList<>(Arrays.asList(
                new Edge(1, 2, 0),
                new Edge(1, 3, 0),

                new Edge(2, 4, 0),
                new Edge(2, 5, 0),

                new Edge(3, 6, 0),
                new Edge(3, 7, 0),

                new Edge(4, 8, 0),
                new Edge(4, 9, 0),

                new Edge(5, 10, 0),
                new Edge(5, 11, 0),

                new Edge(6, 12, 0),
                new Edge(6, 13, 0),

                new Edge(7, 14, 0),
                new Edge(7, 15, 0)
        ));

        gph.addAll_vert(v)
            .addAll_edge(e)
            .setVertActive(0, false)
                .setVertSize(50)
                .setTextSize(25);

        SegmentTree seg = new SegmentTree(8,gph);

        List<Integer> num = Arrays.asList(1, 9, 10, 3, 5, 6, 7, 8);
        for(int i=0; i < num.size(); i++)
            seg.upt(i, num.get(i));

        seg.qry(1, 3);
        seg.qry(0, 7);
        seg.qry(4, 6);
        seg.qry(3, 5);
    }
}
