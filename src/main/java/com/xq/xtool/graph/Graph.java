package com.xq.xtool.graph;

import java.util.Collection;
import java.util.Optional;

/**
 * 图 接口
 *
 * @author xiongqiang
 * @version 1.0
 * @projectname xtool
 * 2023/3/1 15:50
 */
public interface Graph<E> {

    Node<E> addNode(E e);

    Node<E> removeNode(E e);
    boolean contains(E e);

    Collection<Node<E>> nodes();

    /**
     * 相邻节点
     * @param e
     * @return
     */
    Collection<Node<E>> adjacentNodes(E e);

    /**
     * 相邻元素
     *
     * @param e
     * @return
     */
    Collection<E> adjacentElement(E e);

    Collection<Edge<E>> edges(E e);


    boolean addEdge(E from, E to);

    boolean addEdge(Edge<E> edge);

    boolean removeEdge(E from, E to);

    boolean removeEdge(Edge<E> edge);

    boolean contains(E from, E to);

    boolean contains(Edge<E> edge);

    /**
     * 节点之间是否存在关联
     * @param from
     * @param to
     * @return
     */
    boolean linked(E from, E to);

    Optional<? extends Edge<? extends E>> getEdge(E from, E to);

    Collection<Edge<E>> edges();



}
