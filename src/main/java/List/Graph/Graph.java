package List.Graph;

import General.AbstractGraph;
import List.Node;
import List.Queue;

import java.util.Arrays;

public class Graph extends AbstractGraph {
    private EdgeList[] edges;

    public Graph(int vertexCount){
        super(vertexCount);
        edges = new EdgeList[vertexCount];
        for (int i = 0; i < vertexCount; i++){
            edges[i] = new EdgeList();
        }
    }

    public Graph(int vertexCount, int[][] edges){
        this(vertexCount);
        for (int[] edge : edges) {
            if (edge.length == 2) {
                addEdge(edge[0], edge[1]);
            } else {
                if (edge.length == 3){
                    addEdge(edge[0], edge[1], edge[2]);
                }
            }
        }
    }

    public void addEdge(int from, int to){
        Edge edge = new Edge(from, to, 1);
        edges[from].insert(edge);
    }

    public void addEdge(int from, int to, int weight){
        Edge edge = new Edge(from, to, weight);
        edges[from].insert(edge);
    }

    protected void depthFirstSearch(boolean[] visited, int fromNode){
        Edge edge;
        int toNode;
        edge = edges[fromNode].getHead();
        while (edge != null){
            toNode = edge.getTo();
            if (!visited[toNode]){
                visited[toNode] = true;
                depthFirstSearch(visited, toNode);
            }
            edge = edge.getNext();
        }
    }

    protected void breadthFirstSearch(boolean[] visited, int startNode){
        Edge edge;
        int fromNode, toNode;
        Queue queue = new Queue();
        queue.enqueue(new Node(startNode));
        while (!queue.isEmpty()){
            fromNode = queue.dequeue().getData();
            edge = edges[fromNode].getHead();
            while (edge != null) {
                toNode = edge.getTo();
                if (!visited[toNode]){
                    visited[toNode] = true;
                    queue.enqueue(new Node(toNode));
                }
                edge = edge.getNext();
            }
        }
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < vertexCount; i++){
            Edge edge = edges[i].getHead();
            while (edge != null){
                s += edge.getFrom() + " " + edge.getTo() + " " +  edge.getWeight() + "\n";
                edge = edge.getNext();
            }
        }
        return s;
    }

    /**
     * Write a new constructor for Graph class for linked list implementation which constructs a new graph by
     * merging all graphs in the graph array. Size represents the number of graphs in graphs. You can assume that
     * all graphs have the same size, and the intersections of the graphs are empty.
     */
    public Graph(Graph[] graphs){
        super(graphs[0].vertexCount); //this line is super important :)
        this.edges = new EdgeList[graphs[0].vertexCount];
        
        for (int i = 0; i < vertexCount; i++)
            edges[i] = new EdgeList();
        
        for (Graph graph : graphs) {
            if (graph == null) continue;
            for (int i = 0; i < vertexCount; i++) {
                Edge current = graph.edges[i].getHead();
                while (current != null) {
                    Edge candidate = edges[i].search(current.getTo());
                    if (candidate == null) {
                        edges[i].insert(new Edge(i, current.getTo(), current.getWeight()));
                    }
                    current = current.getNext();
                }
            }
        }
        
    
    }

    /**
     * Write a function that computes the number of bidirectional edges in a graph. Write the function for adjacency
     * list representation.
     */
    public int bidirectionalEdges(){
        int bid = 0;
        
        for (int i = 0; i < vertexCount; i++) {
            for (int j = i + 1; j < vertexCount; j++) {
                if (edges[i].search(j) != null && edges[j].search(i) != null) bid++;
            }
        }
        return bid;
    }

    /**
     * Modify the breadth first search linked list implementation of Graph such that it checks if there is a path
     * from the node index1 to index2.
     */
    public boolean breadthFirstSearch(boolean[] visit, int index1, int index2){
        int fromNode, toNode;
        Edge edge;
        Queue queue = new Queue();
        queue.enqueue(new Node(index1));
        visit[index1] = true;
        while (!queue.isEmpty()) {
            fromNode = queue.dequeue().getData();
            edge = edges[fromNode].getHead();
            while (edge != null) {
                toNode = edge.getTo();
                if (!visit[toNode]){
                    visit[toNode] = true;
                    queue.enqueue(new Node(toNode));
                    if (toNode == index2) return true;
                }
                edge = edge.getNext();
            }
        }
        
        return false;
    }

    /**
     * Write the method in linked list implementation which constructs a graph from numbers 0, 1, 2, $\ldots$,
     * N - 1; where the numbers represent the node indexes and two nodes are connected if they have common divisor
     * other than 1.
     */
    public Graph constructGraphFromNumbers(int N){
        Graph constructed = new Graph(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = 2; k <= i && k <= j; k++) {
                    if (i % k == 0 && j % k == 0) {
                        constructed.edges[i].insert(new Edge(i, j, 1));
                        constructed.edges[j].insert(new Edge(j, i, 1));
                        break;
                    }
                }
            }
        }
        return constructed;
    }

    /**
     * Write the method in linked list implementation of {\bf Graph} class that returns a new graph formed by adding
     * edges which exist both in the original graph and g2. You may assume both graphs are unweighted.
     */
    public Graph intersection(Graph g2, int v){
        Graph intersection = new Graph(g2.vertexCount);
        
        for (int fromNode = 0; fromNode < vertexCount; fromNode++) {
            Edge current = edges[fromNode].getHead();
            while (current != null) {
                Edge scan = g2.edges[fromNode].getHead();
                while (scan != null) {
                    if (scan.getTo() == current.getTo()) {
                        if (intersection.edges[fromNode].search(scan.getTo()) == null)
                            intersection.edges[fromNode].insert(new Edge(fromNode, scan.getTo(), scan.getWeight()));
                    }
                    scan = scan.getNext();
                }
                current = current.getNext();
            }
        }
        
        return intersection;
    }

    /**
     * Write the method in linked list implementation which constructs an inverse graph of a given graph in linked
     * list implementation. In inverse graph, two distinct vertices are adjacent if and only if they are not adjacent
     * in the original graph. You are not allowed to use extra data structures apart from the constructed graph.
     */
    public Graph inverseGraph(){
        Graph inverse = new Graph(this.vertexCount);
        for (int fromNode = 0; fromNode < vertexCount; fromNode++) {
            for (int toNode = 0; toNode < vertexCount; toNode++) {
                if (edges[fromNode].search(toNode) == null) {
                    inverse.edges[fromNode].insert(new Edge(fromNode, toNode, 1));
                }
            }
        }
        return inverse;
    }

    /**
     * A bipartite graph is a graph such that vertices of the graph can be partitioned into two subsets such that no
     * edge has both its vertices in the same subset. Write a method for adjacency list representation which checks
     * if the corresponding graph is bipartite or not. Hint: Use Depth or breath first search to traverse the graph.
     */
    public boolean isBipartite(){
        int[] colors = new int[vertexCount]; // 2 for blues', 1 for reds' coloring
        
        for (int i = 0; i < vertexCount; i++) {
            if (colors[i] != 0) continue;
            
            Queue queue = new Queue();
            queue.enqueue(new Node(i));
            colors[i] = 2;
            
            int fromNode, toNode;
            Edge edge;
            while (!queue.isEmpty()) {
                fromNode = queue.dequeue().getData();
                edge = edges[fromNode].getHead();
                while (edge != null) {
                    toNode = edge.getTo();
                    if (colors[toNode] == 0) {
                        colors[toNode] = 3 - colors[fromNode];
                        queue.enqueue(new Node(toNode));
                    } else if (colors[fromNode] == colors[toNode]) return false;
                    edge = edge.getNext();
                }
            }
        }
        
        return true;
    }

    /**
     * A graph represents a ring topology if all the nodes create a circular path. Each node is connected to two
     * others, like points on a circle. Write a class method in Graph class for adjacency list representation which
     * checks if the corresponding graph is circular or not.
     */
    public boolean isCircular(){
        boolean[] visited = new boolean[vertexCount];
        int start = 0; // all nodes included you can choose whichever you want.
        
        Queue queue = new Queue();
        queue.enqueue(new Node(start));
        visited[start] = true;
        
        int fromNode, toNode; Edge edge;
        
        while (!queue.isEmpty()) {
            fromNode = queue.dequeue().getData();
            edge = edges[fromNode].getHead();
            while (edge != null) {
                toNode = edge.getTo();
                if (!visited[toNode]) {
                    queue.enqueue(new Node(toNode));
                    visited[toNode] = true;
                } else if (toNode == start) return true;
                
                edge = edge.getNext();
            }
        }
        
        return false;
    }

    /**
     * Write a method that checks if the graph is fully connected or not.
     */
    public boolean isFullyConnected(){
        for (int fromNode = 0; fromNode < vertexCount; fromNode++) {
            for (int toNode = 0; toNode < vertexCount; toNode++) {
                if (edges[fromNode].search(toNode) == null) return false;
            }
        }
        return true;
    }

    /**
     * Write a method which checks if two graphs are the same. Assume that, the method is written in the Adjacency
     * list representation of a graph.
     */
    public boolean isSame(Graph g){
        Edge edge, gEdge;
        for (int fromNode = 0; fromNode < vertexCount; fromNode++) {
            edge = edges[fromNode].getHead();
            gEdge = g.edges[fromNode].getHead();
            while (edge != null && gEdge != null) {
                if (edge.getTo() != gEdge.getTo() && edge.getWeight() != gEdge.getWeight()) return false;
                edge = edge.getNext();
                gEdge = gEdge.getNext();
            }
            if (edge != null || gEdge != null) return false;
        }
        return true;
    }

    /**
     * Write the method in linked list implementation of {\bf Graph} class that returns a new graph formed by adding
     * edges which exist in the original graph or g2. You may assume both graphs are weighted, if an edge exists in
     * both graphs, add the resulting edge with the sum of their weights. You are not allowed to use any linked list
     * methods.
     */
    public Graph merge(Graph g2, int v){ // assumed the same number of vertexCount
        Graph merged = new Graph(vertexCount);
        
        for (int fromNode = 0; fromNode < vertexCount; fromNode++) {
            Edge current = edges[fromNode].getHead();
            while (current != null) {
                 Edge scndCur = g2.edges[fromNode].getHead();
                 int weight = current.getWeight();
                 
                 while (scndCur != null) {
                     if (scndCur.getTo() == current.getTo()) {
                         weight += scndCur.getWeight();
                         break;
                     }
                     scndCur = scndCur.getNext();
                 }
                 merged.edges[fromNode].insert(new Edge(fromNode, current.getTo(), weight));
                 current = current.getNext();
            }
            
            Edge scndCur = g2.edges[fromNode].getHead();
            while (scndCur != null) {
                Edge lookUp = edges[fromNode].getHead();
                while (lookUp != null) {
                    if (scndCur.getTo() == lookUp.getTo())
                        break;
                    lookUp = lookUp.getNext();
                }
                if (lookUp == null)
                    merged.edges[fromNode].insert(new Edge(fromNode, scndCur.getTo(), scndCur.getWeight()));
                scndCur = scndCur.getNext();
            }
        }
        
        return merged;
    }

    /**
     * Given the adjacency list representation of a graph G, write a method which returns true if there are two
     * nodes whose outgoing node lists are the same, false otherwise. Assume that the outgoing node lists are
     * sorted. Your method should run in ${\cal O}(V^3)$ time.
     */
    public boolean outgoingListSame(){
        for (int fromNode = 0; fromNode < vertexCount; fromNode++) {
            for (int secondFrom = fromNode + 1; secondFrom < vertexCount; secondFrom++) {
                boolean areAllSequenceSame = true;
                Edge first = edges[fromNode].getHead();
                Edge second = edges[secondFrom].getHead();
                while (first != null && second != null) {
                    if (first.getTo() != second.getTo() || first.getWeight() != second.getWeight()) {
                        areAllSequenceSame = false;
                        break;
                    }
                    first = first.getNext();
                    second = second.getNext();
                }
                if (areAllSequenceSame && first == null && second == null) return true;
            }
        }
        return false;
    }

    /**
     * Modify the breadth first search {\bf linked list} implementation such that it will store the length of the
     * shortest paths from the start node in {\em lengths} parameter. At the end of the execution, lengths[i] will
     * show the shortest path length from node $start$ to node $i$. You may assume that the path length elements are
     * initialized to $vertexCount$ (number of nodes, which should be larger than any possible shortest path) when
     * you call the function.
     */
    public void shortest(int[] lengths, int start){
        Arrays.fill(lengths, vertexCount);
        boolean[] visited = new boolean[vertexCount];
        Queue queue = new Queue();
        queue.enqueue(new Node(start));
        int length = 0;
        lengths[start] = length;
        visited[start] = true;
        
        int fromNode, toNode;
        Edge edge;
        while (!queue.isEmpty()) {
            length++;
            fromNode = queue.dequeue().getData();
            edge = edges[fromNode].getHead();
            while (edge != null) {
                toNode = edge.getTo();
                if (!visited[toNode]){
                    visited[toNode] = true;
                    lengths[toNode] = length;
                    queue.enqueue(new Node(toNode));
                }
                edge = edge.getNext();
            }
        }
    }

    /**
     * Modify the breadth first search {\bf linked list} implementation such that it will store the indexes of the
     * nodes of the shortest paths from the start node in {\em paths} parameter. At the end of the execution, the
     * indexes in the paths[i] array will show the path visited from node $start$ to node $i$. You may assume that
     * the visited array is initialized to false and paths array is already allocated.
     */
    public void shortest(int[][] path, boolean[] visited, int start){
        Edge edge;
        int fromNode, toNode;
        Queue queue = new Queue();
        queue.enqueue(new Node(start));
        path[start] = new int[]{start};
        visited[start] = true;
        
        while (!queue.isEmpty()){
            fromNode = queue.dequeue().getData();
            edge = edges[fromNode].getHead();
            while (edge != null) {
                toNode = edge.getTo();
                if (!visited[toNode]){
                    visited[toNode] = true;
                    queue.enqueue(new Node(toNode));
                    
                    int[] parentPath = path[fromNode];
                    int[] newPath = new int[parentPath.length + 1];
                    for (int i = 0; i < parentPath.length; i++)
                        newPath[i] = parentPath[i];
                    newPath[parentPath.length] = toNode;
                    path[toNode] = newPath;
                }
                edge = edge.getNext();
            }
        }
    }

    /**
     * For a directed weighted graph, write the method in linked list implementation which returns the shortest
     * distance between the nodes index1 and index2 by two hops, that is, it will return of the shortest of all
     * paths, where one goes from index1 node to node $i$, then from node $i$ to node $index2$.
     */
    public int shortestIn2Hops(int index1, int index2){
        int minCost = Integer.MAX_VALUE;
        for (int btw = 0; btw < vertexCount; btw++) {
            Edge firstStep = edges[index1].search(btw);
            Edge secondStep = edges[btw].search(index2);
            if (firstStep != null &&  secondStep != null) {
                if (firstStep.getWeight() + secondStep.getWeight() < minCost)
                    minCost = firstStep.getWeight() + secondStep.getWeight();
            }
        }
        return minCost;
    }

    /**
     * Write the method in linked list implementation which returns the indexes of the nodes which are reachable by
     * two hops, that is, it will consist of all indexes $j$, where one goes from index node to node $i$, then from
     * node $i$ to node $j$. The size of the returning array should be as much as needed. If there are two or more
     * ways to go to a node $j$, then $j$ must appear that many times in the list (no need to sort or check for
     * duplicates).
     */
    public int[] twoHops(int index){
        int[] twoHops = new int[0];
        Queue fromsOfSecondSteps = new Queue();
        for (int toNode = 0; toNode < vertexCount; toNode++) {
            if (edges[index].search(toNode) != null) {
                fromsOfSecondSteps.enqueue(new Node(toNode));
            }
        }
        
        while (!fromsOfSecondSteps.isEmpty()) {
            int fromNode = fromsOfSecondSteps.dequeue().getData();
            for (int toNode = 0; toNode < vertexCount; toNode++) {
                if (edges[fromNode].search(toNode) != null) {
                    int[] newTwoHops = new int[twoHops.length + 1];
                    for (int j = 0; j < twoHops.length; j++)
                        newTwoHops[j] = twoHops[j];
                    newTwoHops[twoHops.length] = toNode;
                    twoHops = newTwoHops;
                }
            }
        }
        return twoHops;
    }

    /**
     Write a new constructor for Graph class for linked list implementation which constructs a new graph by insersection of all graphs in the
     graph array, where edge is included in the constructed when such edge appears in at least minCount number of graphs. Size represents the
     number of graphs in graphs variable. You may assume that all graphs have the same size and same set of vertices.
     */
    public Graph(Graph[] graphs, int minCount) {
        super(graphs[0].vertexCount);
        this.edges = new EdgeList[graphs[0].vertexCount];
        for (int i = 0; i < vertexCount; i++) edges[i] = new EdgeList();
        
        for (int fromNode = 0; fromNode < vertexCount; fromNode++) {
            for (int toNode = 0; toNode < vertexCount; toNode++) {
                int found = 0;
                for (Graph graph : graphs) {
                    Edge candidate = graph.edges[fromNode].search(toNode);
                    if (candidate != null) {
                        found++;
                    }
                }
                if (found >= minCount) this.edges[fromNode].insert(new Edge(fromNode, toNode, 1));
            }
        }
    }

    /**
     * Write the method in linked list implementation which returns the number of odd-valued
     * edges, where an edge is odd-valued if its two distinct node values are both odd.
     * You are not allowed to use extra data structures.
     */
    public int oddEdgeGraph() {
        int odds = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (i % 2 == 0)
                continue;
            else {
                Edge current = edges[i].getHead();
                while (current != null) {
                    if (current.getTo() % 2 == 1) odds++;
                    current = current.getNext();
                }
            }
        }
        return odds;
    }
}
