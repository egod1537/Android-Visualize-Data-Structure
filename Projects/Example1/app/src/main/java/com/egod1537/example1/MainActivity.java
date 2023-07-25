package com.egod1537.example1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.egod1537.visualizlibraryeditedaar.Examples;
import com.egod1537.visualizlibraryeditedaar.GraphicView;
import com.egod1537.visualizlibraryeditedaar.dataStructure.CPlane;
import com.egod1537.visualizlibraryeditedaar.mathStructure.PointD;
import com.egod1537.visualizlibraryeditedaar.mathStructure.Polynomial;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphicView view = new GraphicView(this);
        view.initialize(0,0, 1080, 720);

        Examples.binarySearch(view);

        setContentView(view);
    }
}