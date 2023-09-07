package com.xq.xtool.graph.simple;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
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
public class DirectedAcyclicGraph<E> {

    private final Map<E, Node<E>> nodes = new ConcurrentHashMap<>();

    public Node<E> addNode(E e) {
        return nodes.computeIfAbsent(e, Node::new);
    }


    public Node<E> removeNode(E e) {
        return nodes.remove(e);
    }


    public boolean contains(E e) {
        return nodes.containsKey(e);
    }

    @Nullable
    public Node<E> getNode(E e) {
        return nodes.get(e);
    }

    @Nullable
    public Node<E> getNode(boolean autoAdd, E e) {
        if (autoAdd) {
            return addNode(e);
        } else {
            return nodes.get(e);
        }
    }

    public Collection<Node<E>> nodes() {
        return nodes.values();
    }

    /**
     * 无节点时返回 空集合？抛异常？
     *
     * @param e
     * @return
     */

    public Collection<Node<E>> adjacentNodes(E e) {
        Set<Node<E>> set = new HashSet<>();
        Optional.ofNullable(e)
                .map(nodes::get)
                .ifPresent(node -> {
                    set.addAll(node.getParents());
                    set.addAll(node.getChildren());
                });
        return set;
    }

    public Collection<E> adjacentElement(E e) {
        return adjacentNodes(e).stream().map(Node::getLabel).collect(Collectors.toSet());
    }

    public boolean addEdge(boolean autoCreateNode, E from, E to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        if (from.equals(to)) {
            return false;
        }
        Node<E> fromNode = getNode(autoCreateNode, from);
        Node<E> toNode = getNode(autoCreateNode, to);

        if (fromNode == null || toNode == null) {
            return false;
        }

        // todo 校验是否有环
        if (linked(to, from)) {
            throw new RuntimeException("会产生环状图，[" + from + "]已经是[" + to + "]的子集");
        }

        fromNode.getChildren().add(toNode);
        toNode.getParents().add(fromNode);
        return true;
    }

    public boolean addEdge(E from, E to) {
        return addEdge(true, from, to);
    }

    public boolean removeEdge(E from, E to) {
        Node<E> fromNode = getNode(false, from);
        Node<E> toNode = getNode(false, to);

        if (fromNode == null || toNode == null) {
            return false;
        }
        boolean fromRemove = fromNode.getChildren().remove(toNode);
        boolean toRemove = toNode.getParents().remove(fromNode);
        return fromRemove && toRemove;
    }

    public boolean linked(E from, E to) {
        return linked(true, from, to);
    }

    public boolean linked(boolean useParents, E from, E to) {
        if (from == null || to == null || Objects.equals(from, to)) {
            return false;
        }
        Node<E> fromNode = getNode(false, from);
        Node<E> toNode = getNode(false, to);

        if (fromNode == null || toNode == null) {
            return false;
        }

        if (useParents) {
            return linkedByParents(fromNode, toNode);
        } else {
            return linkedByChildren(fromNode, toNode);
        }
    }

    /**
     * 判断两个元素是否存在 层级关系（带顺序）
     *
     * @param fromNode
     * @param toNode
     * @return
     */
    public boolean linkedByChildren(Node<E> fromNode, Node<E> toNode) {

        // 子级数量较少，采用向下查找模式
        Set<Node<E>> nodeSet = Collections.singleton(fromNode);
        while (!nodeSet.isEmpty()) {
            nodeSet = getChildren(nodeSet);
            if (nodeSet.contains(toNode)) {
                return true;
            }
        }

        return false;
    }

    public boolean linkedByParents(Node<E> fromNode, Node<E> toNode) {

        // 父级数量较少，采用向上查找模式
        Set<Node<E>> nodeSet = Collections.singleton(toNode);
        while (!nodeSet.isEmpty()) {
            nodeSet = getParents(nodeSet);
            if (nodeSet.contains(fromNode)) {
                return true;
            }
        }

        return false;
    }

    private Set<Node<E>> getChildren(Set<Node<E>> nodeSet) {
        return nodeSet.stream().map(Node::getChildren)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<Node<E>> getParents(Set<Node<E>> nodeSet) {
        return nodeSet.stream().map(Node::getParents)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

}
