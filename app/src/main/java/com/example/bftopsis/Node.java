package com.example.bftopsis;

import java.util.ArrayList;

public class Node {
    private int distanceFromSource = Integer.MAX_VALUE;
    private boolean visited;
    private int predecessor;
    private int predecessorEdge;

    private ArrayList<Edge> edges = new ArrayList<Edge>(); // now we must create edges

    public int getDistanceFromSource() {

        return distanceFromSource;
    }

    public void setDistanceFromSource(int distanceFromSource) {

        this.distanceFromSource = distanceFromSource;

    }

    public int getPredecessorEdge() {

        return predecessorEdge;

    }

    public void setPredecessorEdge(int predecessorEdge) {

        this.predecessorEdge = predecessorEdge;

    }

    public int getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(int predecessor) {
        this.predecessor = predecessor;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
}