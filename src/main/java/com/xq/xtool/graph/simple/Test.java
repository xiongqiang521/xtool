package com.xq.xtool.graph.simple;

/**
 * @author xiongqiang
 * @version 1.0
 * @projectname xtool
 * 2023/3/2 09:06
 */
public class Test {
    public static void main(String[] args) {
        DirectedAcyclicGraph<String> graph = new DirectedAcyclicGraph<>();

        graph.addNode("1");
        graph.addNode("2");
        graph.addNode("3");
        graph.addNode("4");
        graph.addNode("1");

        System.out.println(graph.nodes());

        graph.addEdge("1", "2");
        graph.addEdge("2", "2");
        graph.addEdge("1", "3");
        graph.addEdge("3", "4");
        System.out.println(graph.nodes());

        System.out.println(graph.linked("1", "4"));
        System.out.println(graph.linked("2", "4"));
        System.out.println(graph.linked("4", "1"));

    }
}
