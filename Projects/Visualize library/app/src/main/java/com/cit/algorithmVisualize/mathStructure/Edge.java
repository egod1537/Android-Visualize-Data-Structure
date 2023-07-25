package com.cit.algorithmVisualize.mathStructure;

/*
    @description CGraph 클래스에서 간선을 나타내는 클래스
 */
public class Edge {
    public int from, to, cost, idx;

    public Edge(int from, int to, int cost, int idx){
        this.from = from;
        this.to = to;
        this.cost = cost;
        this.idx = idx;
    }
    public Edge(int from, int to, int cost){
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
    public Edge(int from, int to){
        this(from, to, 0);
    }

    @Override
    public boolean equals(Object object){
        Edge other = (Edge) object;
        return from == other.from && to == other.to && cost == other.cost && idx == other.idx;
    }
}
