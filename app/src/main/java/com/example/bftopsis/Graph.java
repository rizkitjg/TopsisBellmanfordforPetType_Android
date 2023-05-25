package com.example.bftopsis;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

// now we must create graph object and implement dijkstra algorithm
public class Graph {
    private Node[] nodes;
    private Edge[] edges;

    private int noOfNodes;
    private int noOfEdges;

    private int startAt;
    private int endAt;

    private ArrayList<Integer> path = new ArrayList<Integer>();
    private ArrayList<Integer> edgePath = new ArrayList<Integer>();

    public Graph(Edge[] edges) {
        this.edges = edges;
        // create all nodes ready to be updated with the edges
        this.noOfNodes = calculateNoOfNodes(edges);
        this.nodes = new Node[this.noOfNodes];


        for (int n = 0; n < this.noOfNodes; n++) {
            this.nodes[n] = new Node();
        }
        // add all the edges to the nodes, each edge added to two nodes (to and from)
        this.noOfEdges = edges.length;
        for (int edgeToAdd = 0; edgeToAdd < this.noOfEdges; edgeToAdd++) {
            this.nodes[edges[edgeToAdd].getFromNodeIndex()].getEdges().add(edges[edgeToAdd]);
            this.nodes[edges[edgeToAdd].getToNodeIndex()].getEdges().add(edges[edgeToAdd]);
        }

        // add all the edges to the nodes, each edge added to two nodes (to and from)
        this.noOfEdges = edges.length;

    }

    private static int calculateNoOfNodes(Edge[] edges) {
        int noOfNodes = 0;
        for (Edge e : edges) {
            if (e.getToNodeIndex() > noOfNodes)
                noOfNodes = e.getToNodeIndex();
            if (e.getFromNodeIndex() > noOfNodes)
                noOfNodes = e.getFromNodeIndex();
        }
        noOfNodes++;
        return noOfNodes;
    }

    public void setUpEdgeLengths(ArrayList<String> edgeData) {
        // needs a list of edge id-s that will be set to Edge.maxEdgeLength in the edge object.
        // We can't delete edges but length of Edge.maxEdgeLengthmakes makes sure it will be
        // avoided if possible. See comment in Edge.java for further info
        for (int i = 0; i < this.edges.length; i++) {

            if (edgeData.contains(String.valueOf(edges[i].getEdgeId()))) {
                this.edges[i].setLength();
            }
        }
    }

    public void calculatePath() {
        try {
            int nodeNow = endAt;
            Log.v("HJ,", "Node saat ini: " + nodeNow);
            this.path.add(endAt);
            while (nodeNow != startAt) {
                if(nodes[nodeNow].getPredecessor() != 0) {
                    path.add(nodes[nodeNow].getPredecessor());
                    nodeNow = nodes[nodeNow].getPredecessor();
                    Log.v("HJ,", "Node saat ini: " + nodeNow);
                }
                else {
                    break;
                }


            }
        }
        catch (Exception e) {
            Log.v("HJ, gagal karena: ", e.toString());
        }


    }

    public void calculateEdgesPath() {
        int nodeNow = endAt;

        while (nodeNow != startAt) {
            edgePath.add(nodes[nodeNow].getPredecessorEdge());
            nodeNow = nodes[nodeNow].getPredecessor();
        }
    }

    public ArrayList<Integer> getPath() {

        return path;

    }

    public ArrayList<Integer> getEdgePath() {

        return edgePath;

    }

    /**
     * Calculates the shortest distance between two points
     * @param startpoint
     * @param endpoint
     */
    //Dijkstra Algorithm
    /*public void calculateShortestDistances(int startpoint, int endpoint) {

        startAt = startpoint;
        endAt = endpoint;

        this.nodes[startpoint].setDistanceFromSource(0);
        int nextNode = startpoint;

        // visit every node
        for (int i = 0; i < this.nodes.length; i++) {
            // loop around the edges of current node

            ArrayList<Edge> currentNodeEdges = this.nodes[nextNode].getEdges();

            for (int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++) {

                int neighbourIndex = currentNodeEdges.get(joinedEdge).getNeighbourIndex(nextNode);

                if (!this.nodes[neighbourIndex].isVisited()) {
                    int tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLength();

                    if (tentative < nodes[neighbourIndex].getDistanceFromSource()) {
                        nodes[neighbourIndex].setDistanceFromSource(tentative);
                        nodes[neighbourIndex].setPredecessor(nextNode);
                        nodes[neighbourIndex].setPredecessorEdge(currentNodeEdges.get(joinedEdge).getEdgeId());
                    }
                }
            }

            nodes[nextNode].setVisited(true);

            nextNode = getNodeShortestDistanced();

        }

    }*/

    public void calculateShortestDistances(int startpoint, int endpoint) {
            startAt = startpoint;
            endAt = endpoint;

            this.nodes[startpoint].setDistanceFromSource(0);
            int nextNode = startpoint;

            //bellman ford
            int n = this.nodes.length;
            for (int i = 0; i < n; ++i) {
                boolean lastIteration = i == n-1;
                boolean atLeastOneChange = false;
                Log.v("BF, Iterasi i ke-", String.valueOf(i));

                //for all edges...
                for(int j = 0; j < this.edges.length; ++j) {
                    Log.v("BF, Iterasi j ke-", String.valueOf(j));
                    int u = this.edges[j].getFromNodeIndex();
                    int v = this.edges[j].getToNodeIndex();
                    int weight = this.edges[j].getLength();
                    Log.v("BF, node awal edge", String.valueOf(u));
                    Log.v("BF, node akhir edge", String.valueOf(v));
                    int totalCostToEdgeSource = this.nodes[u].getDistanceFromSource();
                    Log.v("BF, cost node awal", String.valueOf(totalCostToEdgeSource));


                    // Ignore edge if no path to edge source was found so far
                    if (totalCostToEdgeSource == Integer.MAX_VALUE) {
                        Log.v("BF, ignore kamu max", String.valueOf("Integer max lu mah " + totalCostToEdgeSource));
                        continue;
                    }

                    // Calculate total cost from start via edge source to edge target
                    int totalCostToEdgeTarget = this.nodes[u].getDistanceFromSource() + weight;
                    Log.v("BF, tentative", String.valueOf(totalCostToEdgeTarget));

                    // Cheaper path found?
                    // a) regular iteration --> Update total cost and predecessor
                    // b) negative cycle detection --> throw exception
                    if(totalCostToEdgeTarget < this.nodes[v].getDistanceFromSource()) {
                        if(lastIteration) {
                            throw new IllegalArgumentException("Negative cycle detected");
                        }
                        Log.v("BF, jumpa jarak <", String.valueOf(true));
                        this.nodes[v].setDistanceFromSource(totalCostToEdgeTarget);
                        this.nodes[v].setPredecessor(u);
                        //this.nodes[v].setPredecessorEdge(this.nodes[u].getEdges().get(j).getEdgeId());
                        atLeastOneChange = true;
                    }


                }
                // Optimization: terminate if nothing was changed
                if(!atLeastOneChange) break;

            }



        // visit every node --- DIJKSTRA
        /*for (int i = 0; i < this.nodes.length; i++) {
            // loop around the edges of current node

            ArrayList<Edge> currentNodeEdges = this.nodes[nextNode].getEdges();

            for (int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++) {

                int neighbourIndex = currentNodeEdges.get(joinedEdge).getNeighbourIndex(nextNode);

                if (!this.nodes[neighbourIndex].isVisited()) {
                    int tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLength();

                    if (this.nodes[nextNode].getDistanceFromSource() != Integer.MAX_VALUE && tentative < nodes[neighbourIndex].getDistanceFromSource()) {
                        nodes[neighbourIndex].setDistanceFromSource(tentative);
                        nodes[neighbourIndex].setPredecessor(nextNode);
                        nodes[neighbourIndex].setPredecessorEdge(currentNodeEdges.get(joinedEdge).getEdgeId());
                    }
                }
            }

            nodes[nextNode].setVisited(true);

            nextNode = getNodeShortestDistanced();

        }*/


    }

    /*private int getNodeShortestDistanced() {
        int storedNodeIndex = 0;
        int storedDist = Integer.MAX_VALUE;
        for (int i = 0; i < this.nodes.length; i++) {
            int currentDist = this.nodes[i].getDistanceFromSource();
            if (!this.nodes[i].isVisited() && currentDist < storedDist) {
                storedDist = currentDist;
                storedNodeIndex = i;

            }
        }
        return storedNodeIndex;
    }*/
}