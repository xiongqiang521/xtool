package com.xq.xtool.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 有向无环图（DAG,Directed Acyclic Graph）
 *
 * @author xiongqiang
 * @version 1.0
 * @projectname xtool
 * 2023/3/1 11:56
 */
public class DirectedAcyclicGraph<E> implements Graph<E> {

    private final Map<E, Node<E>> nodes = new ConcurrentHashMap<>();

    @Override
    public Node<E> addNode(E e) {
        return nodes.putIfAbsent(e, new Node<>(e));
    }

    @Override
    public Node<E> removeNode(E e) {
        return nodes.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return nodes.containsKey(e);
    }

    @Override
    public Collection<Node<E>> nodes() {
        return nodes.values();
    }

    /**
     * 无节点时返回 空集合？抛异常？
     * @param e
     * @return
     */
    @Override
    public Collection<Node<E>> adjacentNodes(E e) {
        Set<Node<E>> set = new HashSet<>();

        edges().forEach(edge -> {
            set.add(edge.getFrom());
            set.add(edge.getTo());
        });
        return set;
    }

    @Override
    public Collection<E> adjacentElement(E e) {
        return adjacentNodes(e).stream().map(Node::getLabel).collect(Collectors.toSet());
    }

    @Override
    public Collection<Edge<E>> edges(E e) {
        return Optional.ofNullable(e)
                .map(nodes::get)
                .map(Node::getEdges)
                .orElse(new HashSet<>());
    }

    @Override
    public boolean addEdge(E from, E to) {
        return false;
    }

    @Override
    public boolean addEdge(Edge<E> edge) {
        return false;
    }

    @Override
    public boolean removeEdge(E from, E to) {
        return false;
    }

    @Override
    public boolean removeEdge(Edge<E> edge) {
        return false;
    }

    @Override
    public boolean contains(E from, E to) {
        return false;
    }

    @Override
    public boolean contains(Edge<E> edge) {
        return false;
    }

    @Override
    public boolean linked(E from, E to) {
        return false;
    }

    @Override
    public Optional<? extends Edge<? extends E>> getEdge(E from, E to) {
        return Optional.empty();
    }

    @Override
    public Collection<Edge<E>> edges() {
        return null;
    }
}
