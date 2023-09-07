package com.xq.xtool.graph;

import com.sun.istack.NotNull;

import java.util.Objects;

/**
 * 图的边描述
 *
 * @author xiongqiang
 * @version 1.0
 * @projectname xtool
 * 2023/3/1 16:00
 */
public class Edge<E> {

    private final Node<E> from;
    private final Node<E> to;

    public Edge(@NotNull Node<E> from, @NotNull Node<E> to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        this.from = from;
        this.to = to;
    }


    public Node<E> getFrom() {
        return from;
    }

    public Node<E> getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;
        return from.equals(edge.from) && to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

}
