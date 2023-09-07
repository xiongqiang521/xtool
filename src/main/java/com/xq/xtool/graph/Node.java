package com.xq.xtool.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 图 节点 接口
 *
 * @author xiongqiang
 * @version 1.0
 * @projectname xtool
 * 2023/3/1 15:59
 */
public class Node<E> {
    private final E label;

    private final Set<Edge<E>> edges;

    public Node(E label) {
        Objects.requireNonNull(label);
        this.label = label;
        edges = Collections.synchronizedSet(new HashSet<>());
    }

    public class SingleEdge extends Edge<E> {

        private boolean top;

        public SingleEdge(Node<E> from, Node<E> to) {
            super(from, to);
        }

    }

    public E getLabel() {
        return label;
    }

    public Set<Edge<E>> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node<?> node = (Node<?>) o;
        return label.equals(node.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
