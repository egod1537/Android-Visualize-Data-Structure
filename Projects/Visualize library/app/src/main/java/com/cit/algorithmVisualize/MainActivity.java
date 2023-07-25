package com.cit.algorithmVisualize;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cit.algorithmVisualize.dataStructure.CPlane;
import com.cit.algorithmVisualize.mathStructure.PointD;
import com.cit.algorithmVisualize.mathStructure.PolygonD;
import com.cit.algorithmVisualize.mathStructure.Polynomial;
import com.example.canvaspractice.R;
import com.example.canvaspractice.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphicView convexhull = findViewById(R.id.convexhull);
        convexhull.initialize(0,0,1080,600);

        CPlane plane = new CPlane(convexhull, Arrays.asList(
                new PointD(1, 0),
                new PointD(2, 0),
                new PointD(3, 0)
        ));
        plane.setAutoXYRange(false)
                .setXRange(-1133.5, 324375)
                .setYRange(-1.0, 3);
        Polynomial pd = new Polynomial(Arrays.asList(0.0,0.0,1.0));
        plane.addPolynomialMark(pd);
        plane.draw();

        plane.setXRange(-5.0, 5.0)
                .setYRange(-5.0, 5.0)
                .draw();


        GraphicView bfs = findViewById(R.id.bfs);
        bfs.initialize(0,0,1080, 700);
        Examples.bfs(bfs);

        GraphicView dijkstra_view = findViewById(R.id.dijkstra_view);
        dijkstra_view.initialize(0,0,1080, 600);
        GraphicView dijkstra_table = findViewById(R.id.dijkstra_table);
        dijkstra_table.initialize(0,0,1080, 600);
        Examples.dijkstra(dijkstra_view, dijkstra_table);

        GraphicView segmenttree = findViewById(R.id.segmentree);
        segmenttree.initialize(0,0,1080, 600);
        Examples.segmentTree(segmenttree);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}