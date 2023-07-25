package com.cit.algorithmVisualize.mathStructure;

import java.util.ArrayList;
import java.util.List;
/*
    @description CPlane에서 다항식을 나타내는 클래스.
 */
public class Polynomial extends ArrayList<Double> implements IFunc{
    public Polynomial(List<Double> asList) {
        for(Double x : asList) this.add(x);
    }

    @Override
    public double f(double x) {
        float y=0, pw = 1;
        for(Double a : this){
            y += a*pw;
            pw *= x;
        }
        return y;
    }
}
