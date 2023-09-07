package com.xq.xtool.graph.simple;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 图 节点 接口
 *
 * @author xiongqiang
 * @version 1.0
 * @projectname xtool
 * 2023/3/1 15:59
 */
public class Node<E> {
    private final static Collector<CharSequence, ?, String> SET_JOINER = Collectors.joining(",", "[", "]");

    private final E label;

    private final Set<Node<E>> parents;

    private final Set<Node<E>> children;

    public Node(E label) {
        Objects.requireNonNull(label);
        this.label = label;
        parents = Collections.synchronizedSet(new HashSet<>());
        children = Collections.synchronizedSet(new HashSet<>());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("label: ").append(label).append(", ");

        String parentsString = parents.stream().map(Node::getLabel).map(Objects::toString).collect(SET_JOINER);
        sb.append("parents: ").append(parentsString).append(", ");
        String childrenString = children.stream().map(Node::getLabel).map(Objects::toString).collect(SET_JOINER);
        sb.append("children: ").append(childrenString);
        sb.append("}");
        return sb.toString();
    }

    public E getLabel() {
        return label;
    }

    public Set<Node<E>> getParents() {
        return parents;
    }

    public Set<Node<E>> getChildren() {
        return children;
    }
}
