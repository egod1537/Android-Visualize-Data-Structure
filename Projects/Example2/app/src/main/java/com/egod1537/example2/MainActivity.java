package com.egod1537.example2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.egod1537.visualizlibraryeditedaar.Examples;
import com.egod1537.visualizlibraryeditedaar.GraphicView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphicView fibonacci = findViewById(R.id.fibonacci);
        fibonacci.initialize(0,0,1080, 700);
        Examples.fibonacci(fibonacci);

        GraphicView binary_search = findViewById(R.id.binary_search);
        binary_search.initialize(0,0,1080, 700);
        Examples.binarySearch(binary_search);

        GraphicView prefixSum = findViewById(R.id.prefixSum);
        prefixSum.initialize(0,0,1080, 700);
        Examples.prefixSum1D(prefixSum);

        GraphicView bfs = findViewById(R.id.bfs);
        bfs.initialize(0,0,1080, 700);
        Examples.bfs(bfs);

        GraphicView convex = findViewById(R.id.convexHull);
        convex.initialize(0,0,1080, 1200);
        Examples.convexhull(convex);

        GraphicView dijkstra_view = findViewById(R.id.dijkstra_view);
        dijkstra_view.initialize(0,0,1080, 600);
        GraphicView dijkstra_table = findViewById(R.id.dijkstra_table);
        dijkstra_table.initialize(0,0,1080, 600);
        Examples.dijkstra(dijkstra_view, dijkstra_table);

        GraphicView segmenttree = findViewById(R.id.segmentree);
        segmenttree.initialize(0,0,1080, 600);
        Examples.segmentTree(segmenttree);
    }
}