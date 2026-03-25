package Array.Graph;

import Array.Element;
import Array.Queue;
import General.AbstractGraph;


public class Graph extends AbstractGraph {

    private int[][] edges;

    public Graph(int vertexCount){
        super(vertexCount);
        edges = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++){
            for (int j = 0; j < vertexCount; j++){
                edges[i][j] = 0;
            }
        }
    }

    public Graph(int vertexCount, int[][] edges){
        this(vertexCount);
        for (int[] edge : edges) {
            if (edges[0].length == 2){
                addEdge(edge[0], edge[1]);
            } else {
                if (edges[0].length == 3){
                    addEdge(edge[0], edge[1], edge[2]);
                }
            }
        }
    }

    public void addEdge(int from, int to){
        edges[from][to] = 1;
    }

    public void addEdge(int from, int to, int weight){
        edges[from][to] = weight;
    }

    protected void depthFirstSearch(boolean[] visited, int fromNode){
        for (int toNode = 0; toNode < vertexCount; toNode++){
            if (edges[fromNode][toNode] > 0){
                if (!visited[toNode]){
                    visited[toNode] = true;
                    depthFirstSearch(visited, toNode);
                }
            }
        }
    }

    protected void breadthFirstSearch(boolean[] visited, int startNode){
        int fromNode;
        Queue queue = new Queue(100);
        queue.enqueue(new Element(startNode));
        while (!queue.isEmpty()){
            fromNode = queue.dequeue().getData();
            for (int toNode = 0; toNode < vertexCount; toNode++) {
                if (edges[fromNode][toNode] > 0) {
                    if (!visited[toNode]){
                        visited[toNode] = true;
                        queue.enqueue(new Element(toNode));
                    }
                }
            }
        }
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < vertexCount; i++){
            for(int j = 0; j < vertexCount; j++){
                if(edges[i][j] > 0){
                    s += i + " " + j + " " + edges[i][j] + "\n";
                }
            }
        }
        return s.trim();
    }

    /**
     * Given a weighted undirected graph representing the distances between cities in a country, write a method in
     * adjacency matrix representation which identifies the index of the capital city. The capital city has the
     * largest number of nearby cities compared to other cities. A city A is nearby to a city B, if its direct
     * distance to city B is the smallest across all cities compared. Identify the number of nearby cities for every
     * city, and use one array to store the number of nearby cities for every city.
     */
    public int capitalCity() {
    int[] nearbyCount = new int[vertexCount];

    for (int fromCity = 0; fromCity < vertexCount; fromCity++) {
        int minDistance = Integer.MAX_VALUE;

        for (int toCity = 0; toCity < vertexCount; toCity++) {
            if (fromCity == toCity) continue;
            if (edges[fromCity][toCity] > 0 && edges[fromCity][toCity] < minDistance) {
                minDistance = edges[fromCity][toCity];
            }
        }

        for (int toCity = 0; toCity < vertexCount; toCity++) {
            if (fromCity == toCity) continue;
            if (edges[fromCity][toCity] == minDistance) {
                nearbyCount[fromCity]++;
            }
        }
    }

    int capitalIndex = 0;
    for (int i = 1; i < vertexCount; i++) {
        if (nearbyCount[i] > nearbyCount[capitalIndex]) {
            capitalIndex = i;
        }
    }

    return capitalIndex;
}

    /**
     * Given a directed graph represented by adjacency matrix, write a recursive method in the Graph class that
     * determines whether the graph contains any cycles starting form vertex v. Use the same idea in Depth-First
     * Search (DFS). Your method should return true if there is at least one cycle in the graph, and false otherwise.
     */
    public boolean hasCycle(int v, boolean[] visited){
        if (visited[v]) return true;
        visited[v] = true;
        for (int to = 0; to < vertexCount; to++) {
            if (edges[v][to] != 0)
                return hasCycle(to, visited);
        }
        return false;
    }

    /**
     * Write a method that checks if the graph is complete bipartite graph or not. Write the function for adjacency
     * matrix representation. A graph $(V_1, V_2)$ is said to be a complete bipartite graph if every vertex in
     * $V_1$ is connected to every vertex of $V_2$.
     */
    public boolean isCompleteBipartite(){
        int[] colours = new int[vertexCount]; // 2 for blue, 1 for red, does not matter at all :)
        // no need for a visited array, since colour denotes it --> colour[] == 0 means I did not visit that node before.
        // let's start for node zero being blue.
        colours[0] = 2;
        for (int from = 0; from < vertexCount; from++) {
            for (int to = 0; to < vertexCount; to++) {
                if (from == to) continue;
                if (edges[from][to] != 0) {
                    if (colours[to] == 0) {
                        colours[to] = 3 - colours[from];
                    } else if (colours[from] == colours[to])
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Write the method in array~(adjacency matrix) implementation which returns whether graph is a star graph or
     * not. Star graph is obtained by connecting a node to all the remaining nodes. If a graph has n nodes, there are
     * n-1 edges as shown in example star graph below. You are not allowed to use depth first search or breadth
     * first search.
     */
    public boolean isStarGraph(){
        for (int from = 0; from < vertexCount; from++) {
            boolean areConnected = true;
            for (int to = 0; to < vertexCount; to++) {
                if (from == to) continue;
                if (edges[from][to] == 0) areConnected = false;
            }
            if (areConnected) return true;
        }
        return false;
    }

    /**
     * Write the method in array implementation which returns true if g is a subgraph of the current graph, false
     * otherwise. A graph $G_1$ is a subgraph of graph $G_2$ if every edge of graph $G_1$ is also an edge in graph
     * $G_2$.
     */
    public boolean isSubGraph(Graph g){
        for (int from = 0; from < g.vertexCount; from++) {
            for (int to = 0; to < g.vertexCount; to++) {
                if (g.edges[from][to] != 0 && edges[from][to] == 0) return false;
            }
        }
        return true;
    }

    /**
     * For a directed weighted graph, write the method in array implementation which returns length of the circle
     * assuming that the graph is a circular graph. A graph is circular if all the nodes create a circular path.
     * Each node is connected to two others, like points on a circle.
     */
    public int lengthOfCircle(){
        // question said "if all the nodes create a circular path." the second test case does not give us that.
        // due to that, this question is just implemented heuristically.
        int length = 1;
        int startNode = 0;
        boolean[] visited = new boolean[vertexCount];
        visited[startNode] = true;
        
        for (int toNode = 0; toNode < vertexCount; toNode++) {
            if (!visited[toNode] && edges[startNode][toNode] != 0) {
                startNode = toNode;
                visited[toNode] = true;
                length++;
            }
        }
        
        return length;
    }

    /**
     * Write the method in array implementation which returns the number of complete subgraphs in the graph. A
     * complete graph is a graph, in which all vertices are connected to all vertices. Assume that the graph only
     * consists of complete subgraphs of size $>$ 1, there are no extra vertices, which is not in a complete
     * subgraph. You are not allowed to use depth first search or breadth first search. In the graph below
     * (1, 2, 5), (3, 6) and (4, 7) are complete subgraphs.
     */
    public int numberOfCompleteSubGraphs(){
        boolean[] visited = new boolean[vertexCount];
        int count = 0;
        // AI SOLUTION: we can just check for every node, which nodes are connected to it,
        // and then check if those nodes are connected to each other.
        // If they are, we have a complete subgraph. We can mark all those nodes as visited, so we do not count them again.
        for (int start = 0; start < vertexCount; start++) {
            if (visited[start]) continue;

            int[] component = new int[vertexCount];
            int size = 0;

            for (int i = 0; i < vertexCount; i++) {
                if (i == start || edges[start][i] != 0 || edges[i][start] != 0) {
                    component[size++] = i;
                }
            }

            for (int i = 0; i < size; i++) {
                visited[component[i]] = true;
            }

            if (size <= 1) continue;

            boolean isComplete = true;
            for (int i = 0; i < size && isComplete; i++) {
                for (int j = 0; j < size; j++) {
                    if (i == j) continue;
                    if (edges[component[i]][component[j]] == 0 && edges[component[j]][component[i]] == 0) {
                        isComplete = false;
                        break;
                    }
                }
            }

            if (isComplete) count++;
        }

        return count;
    }
    /**
     * The adjacency matrix $M$ represents the number of ways to travel between pairs of nodes in a graph in exactly
     * one move. $M^k$ represents the number of ways to travel between pair of nodes in a graph in exactly k moves.
     * Write the method which calculates and returns $M^2$ for a graph. Your method should run in ${\cal O}(V^3)$
     * time.
     */
    public int[][] numberOfWaysInTwoMoves() {
        int[][] result = new int[vertexCount][vertexCount];
        // matrix multiplication
        for (int from = 0; from < vertexCount; from++) {
            for (int to = 0; to < vertexCount; to++) {
                int entry = 0;
                for (int i = 0; i < vertexCount; i++) {
                    entry += edges[from][i] * edges[i][to];
                }
                result[from][to] = entry;
            }
        }
        return result;
    }

    /**
     * Write a method to find the out-degree of a node given its index.
     */
    public int outDegree(int index){
        int outDegree = 0;
        for (int to = 0; to < vertexCount; to++) {
            if (edges[index][to] != 0) outDegree++;
        }
        return outDegree;
    }

    /**
     * Given the adjacency matrix representation of a graph G, write a method which returns true if there are two
     * nodes whose outgoing node lists are the same, false otherwise. Your method should run in ${\cal O}(V^3)$
     * time.
     */
    public boolean outgoingListSame(){
        for (int from = 0; from < vertexCount; from++) {
            for (int secondFrom = from + 1; secondFrom < vertexCount; secondFrom++) {
                boolean areAllSame = true;
                for (int to = 0; to < vertexCount; to++) {
                    if (edges[from][to] != edges[secondFrom][to]) areAllSame = false;
                }
                if (areAllSame) return true;
            }
        }
        return false;
    }

    /**
     Write the method in array~(adjacency matrix) implementation of undirected Graph which returns whether graph is a two-graph or not. You may assume
     graph is connected. A two-graph is a graph where degree of all vertices are 2. You are not allowed to use depth first search or breadth
     first search.
     */
    public boolean isTwoGraph() {
        for (int fromNode = 0; fromNode < vertexCount; fromNode++) {
            int edge = 0;
            for (int toNode = 0; toNode < vertexCount; toNode++) {
                if (edges[fromNode][toNode] != 0) edge++;
            }
            if (edge != 2) return false;
        }
        return true;
    }

}
