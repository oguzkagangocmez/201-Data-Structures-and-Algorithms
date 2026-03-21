import List.Graph.Graph;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestListGraph {

    @Test
    public void testGraph(){
        Graph graph = new Graph(4,
                new int[][]{{0, 1}, {0, 2}, {1, 0}, {2, 0}});
        Graph graph2 = new Graph(4,
                new int[][]{{0, 1}, {1, 1}, {1, 2}, {2, 0}});
        Graph[] graphs = new Graph[2];
        graphs[0] = graph;
        graphs[1] = graph2;
        Graph result = new Graph(graphs);
        assertEquals("0 1 1\n0 2 1\n1 0 1\n1 1 1\n1 2 1\n2 0 1\n", result.toString());
        graph = new Graph(10,
                new int[][]{{0, 1}, {0, 2}, {1, 0}, {2, 0}});
        graph2 = new Graph(10,
                new int[][]{{0, 1}, {0, 2}, {1, 0}, {2, 0}});
        Graph[] graphs2 = new Graph[2];
        graphs2[0] = graph;
        graphs2[1] = graph2;
        result = new Graph(graphs2);
        assertEquals("0 1 1\n0 2 1\n1 0 1\n2 0 1\n", result.toString());
    }

    @Test
    public void testBidirectionalEdges(){
        Graph graph = new Graph(4,
                new int[][]{{0, 1}, {1, 0}, {2, 0},
                        {0, 2}, {0, 3}});
        assertEquals(2, graph.bidirectionalEdges());
        graph = new Graph(4,
                new int[][]{{0, 1}, {1, 0}, {2, 0},
                        {0, 2}, {0, 3}, {3, 4},
                        {3, 0}});
        assertEquals(3, graph.bidirectionalEdges());
    }

    @Test
    public void testBreadthFirstSearch(){
        Graph graph = new Graph(4,
                new int[][]{{0, 1}, {1, 2}, {1, 3}, {2, 3}});
        boolean[] visited = new boolean[4];
        assertTrue(graph.breadthFirstSearch(visited,0, 2));
        visited = new boolean[4];
        assertFalse(graph.breadthFirstSearch(visited,3, 1));
        graph = new Graph(10,
                new int[][]{{0, 1}, {1, 2}, {0, 3},
                {3, 5}, {5, 4}, {4, 5}});
        boolean[] visited2 = new boolean[10];
        assertTrue(graph.breadthFirstSearch(visited2,0, 4));
        visited2 = new boolean[10];
        assertFalse(graph.breadthFirstSearch(visited2,5, 0));
    }

    @Test
    public void testConstructGraphFromNumbers(){
        Graph graph = new Graph(10,
                new int[][]{{2,4},{2,6},{3,6},{4,2},{4,6},{6,2},{6,3},{6,4}});
        Graph result = graph.constructGraphFromNumbers(7);
        System.out.println(result);
        assertEquals(result.toString(), graph.toString());
    }

    @Test
    public void testIntersection(){
        Graph graph = new Graph(10,
                new int[][]{{2, 4}, {4, 2}, {6, 3},
                {3, 6}, {1, 5}});
        Graph graph2 = new Graph(10,
                new int[][]{{2, 4}, {3, 6}, {1, 5}, {6, 5}});
        Graph expected = new Graph(10,
                new int[][]{{2, 4}, {3, 6}, {1, 5}});
        Graph actual = graph.intersection(graph2, 0);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testInverseGraph(){
        Graph graph = new Graph(3,
                new int[][]{{0, 0}, {0, 1}, {1, 0},
                        {1, 2}, {2, 1}});
        Graph expected = new Graph(3,
                new int[][]{{1, 1}, {2, 0}, {2, 2}, {0, 2}});
        Graph actual = graph.inverseGraph();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testIsBipartite(){
        Graph graph = new Graph(6,
                new int[][]{{0, 4}, {1, 3}, {1, 4},
                {2, 4}, {2, 5}});
        assertTrue(graph.isBipartite());
        graph = new Graph(4,
                new int[][]{{0, 2}, {1, 3}, {2, 3}});
        assertFalse(graph.isBipartite());
        // 1. Single node
        assertTrue(new Graph(1, new int[][]{}).isBipartite());
    
        // 2. Two nodes connected
        assertTrue(new Graph(2, new int[][]{{0, 1}}).isBipartite());
    
        // 3. Odd cycle (triangle) -> NOT bipartite
        assertFalse(new Graph(3,
                new int[][]{{0, 1}, {1, 2}, {2, 0}}).isBipartite());
    
        // 4. Even cycle -> bipartite
        assertTrue(new Graph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}}).isBipartite());
    
        // 5. Disconnected (all good)
        assertTrue(new Graph(6,
                new int[][]{{0, 1}, {2, 3}, {4, 5}}).isBipartite());
    
        // 6. Disconnected with one odd cycle -> NOT bipartite
        assertFalse(new Graph(6,
                new int[][]{
                        {0, 1}, {1, 2}, {2, 0}, // bad component
                        {3, 4}
                }).isBipartite());
    
        // 7. Star graph
        assertTrue(new Graph(5,
                new int[][]{{0,1}, {0,2}, {0,3}, {0,4}}).isBipartite());
    
        // 8. Chain / line
        assertTrue(new Graph(5,
                new int[][]{{0,1}, {1,2}, {2,3}, {3,4}}).isBipartite());
    
        // 9. Self-loop -> NOT bipartite
        assertFalse(new Graph(3,
                new int[][]{{0, 0}, {1, 2}}).isBipartite());
    
        // 10. Complete graph K4 -> NOT bipartite
        assertFalse(new Graph(4,
                new int[][]{
                        {0,1},{0,2},{0,3},
                        {1,2},{1,3},
                        {2,3}
                }).isBipartite());
    
        // 11. Square + diagonal -> NOT bipartite
        assertFalse(new Graph(4,
                new int[][]{
                        {0,1},{1,2},{2,3},{3,0},
                        {0,2}
                }).isBipartite());
    
        // 12. Only isolated nodes
        assertTrue(new Graph(5, new int[][]{}).isBipartite());
    }

    @Test
    public void testIsCircular(){
        Graph graph = new Graph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}});
        assertTrue(graph.isCircular());
        graph = new Graph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 2}});
        assertFalse(graph.isCircular());
    }

    @Test
    public void testIsFullyConnected(){
        Graph graph = new Graph(3,
                new int[][]{{0, 0}, {0, 1}, {0, 2},
                        {1, 0}, {1, 1}, {1, 2},
                        {2, 0}, {2, 1}, {2, 2}});
        assertTrue(graph.isFullyConnected());
        graph = new Graph(3,
                new int[][]{{0, 0}, {0, 1}, {1, 2},
                        {1, 0}, {2, 1}});
        assertFalse(graph.isFullyConnected());
    }

    @Test
    public void testIsSame(){
        Graph graph = new Graph(3,
                new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 2}});
        Graph graph2 = new Graph(3,
                new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 2}});
        assertTrue(graph.isSame(graph2));
        graph = new Graph(4,
                new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 2}});
        graph2 = new Graph(4,
                new int[][]{{0, 0}, {0, 1}, {1, 0},
                        {1, 2}, {2, 3}});
        assertFalse(graph.isSame(graph2));
    }

    @Test
    public void testMerge(){
        Graph g1 = new Graph(10,
            new int[][]{
                    {2, 4, 1},
                    {6, 3, 1},
                    {1, 5, 1}
            });

        Graph g2 = new Graph(10,
                new int[][]{
                        {2, 4, 2}, // common edge
                        {3, 6, 2}, // reverse direction (different edge if directed)
                        {1, 7, 2}
                });

        Graph expected = new Graph(10,
                new int[][]{
                        {2, 4, 3}, // 1 + 2
                        {6, 3, 1}, // only in g1
                        {3, 6, 2}, // only in g2
                        {1, 5, 1}, // only in g1
                        {1, 7, 2}  // only in g2
                });

        Graph actual = g1.merge(g2, 0);
    
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testOutgoingListSame() {
        Graph graph = new Graph(6,
                new int[][]{{0, 1}, {0, 3}, {0, 4},
                        {2, 1}, {2, 3}, {2, 4},
                        {4, 0}, {5, 1}});
        assertTrue(graph.outgoingListSame());
        graph = new Graph(5,
                new int[][]{{0, 1}, {1, 2}, {2, 3},
                        {3, 4}, {4, 0}});
        assertFalse(graph.outgoingListSame());
    }

    @Test
    public void testShortest() {
        Graph graph = new Graph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}});
        int[] lengths = new int[4];
        graph.shortest(lengths, 0);
        assertArrayEquals(new int[]{0, 1, 2, 3}, lengths);
        graph = new Graph(5,
                new int[][]{{0, 1}, {1, 2}, {3, 4}});
        int[] lengths2 = new int[5];
        graph.shortest(lengths2, 0);
        assertArrayEquals(new int[]{0, 1, 2, 5, 5}, lengths2);
    }

    @Test
    public void testShortest2() {
        // 0 - 1 - 2 - 3
        int V = 4;
        Graph graph = new Graph(V,
                new int[][]{{0, 1}, {1, 0}, {1, 2},
                {2, 1}, {2, 3}, {3, 2}});
        boolean[] visited = new boolean[V];
        int[][] paths = new int[V][V];
        graph.shortest(paths, visited, 0);
        assertArrayEquals(new int[]{0}, paths[0]);
        assertArrayEquals(new int[]{0, 1}, paths[1]);
        assertArrayEquals(new int[]{0, 1, 2}, paths[2]);
        assertArrayEquals(new int[]{0, 1, 2, 3}, paths[3]);
    }

    @Test
    public void testShortestIn2Hops() {
        /*
         * direct 0->3 weight 1 (should be ignored for "two hops")
         * two-hop 0->1->3 = 2+2 = 4
         * two-hop 0->2->3 = 10+1 = 11
         * answer must be 4
         */
        Graph graph = new Graph(4,
                new int[][]{{0, 1, 10}, {1, 3, 2}, {0, 2, 3},
                        {2, 3, 20}, {0, 4, 6}, {4, 3, 1}});
        assertEquals(7, graph.shortestIn2Hops(0, 3));
        graph = new Graph(4,
                new int[][]{{0, 3, 1}, {0, 1, 2}, {1, 3, 2},
                        {0, 2, 10}, {2, 3, 1}});
        assertEquals(4, graph.shortestIn2Hops(0, 3));
    }

    @Test
    public void testTwoHops() {
        /*
         * 0 -> 1 -> 3
         * 0 -> 2 -> 3
         * Two different 2-hop walks end at 3, so output should contain [3,3] (order irrelevant)
         */
        Graph graph = new Graph(4, new int[][]{{0, 1}, {0, 2}, {1, 3}, {2, 3}});
        int[] actual = graph.twoHops(0);
        int[] expected = new int[]{3, 3};
        Map<Integer, Integer> counts1 = new HashMap<>();
        for (int x : actual)
            counts1.put(x, counts1.getOrDefault(x, 0) + 1);
        Map<Integer, Integer> counts2 = new HashMap<>();
        for (int x : expected)
            counts2.put(x, counts2.getOrDefault(x, 0) + 1);
        assertEquals(counts1, counts2);
        assertEquals(expected.length, actual.length);
    }

    @Test
    public void testGraphMinCount(){
        Graph graph = new Graph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}});
        Graph graph2 = new Graph(4,
                new int[][]{{0, 1}, {1, 3}, {2, 3}, {3, 1}});
        Graph[] graphs = new Graph[2];
        graphs[0] = graph;
        graphs[1] = graph2;
        Graph result = new Graph(graphs, 2);
        assertEquals("0 1 1\n2 3 1\n", result.toString());
        graph = new Graph(4,
                new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}});
        graph2 = new Graph(4,
                new int[][]{{0, 1}, {1, 3}, {2, 3}, {3, 1}});
        Graph graph3 = new Graph(4,
                new int[][]{{0, 1}, {0, 2}, {1, 3}, {2, 1}});
        Graph[] graphsJoint = new Graph[3];
        graphsJoint[0] = graph;
        graphsJoint[1] = graph2;
        graphsJoint[2] = graph3;
        result = new Graph(graphsJoint, 2);
        assertEquals("0 1 1\n1 3 1\n2 3 1\n", result.toString());
    }

    @Test
    public void testOddEdgeGraph(){
        Graph graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 0);
        assertEquals(0, graph.oddEdgeGraph());

        Graph graph2 = new Graph(6);
        graph2.addEdge(0, 1);
        graph2.addEdge(1, 3);
        graph2.addEdge(5, 3);
        graph2.addEdge(3, 0);
        assertEquals(2, graph2.oddEdgeGraph());
    }

}