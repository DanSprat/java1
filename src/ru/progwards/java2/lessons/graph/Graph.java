package ru.progwards.java2.lessons.graph;

import java.util.List;

public class Graph<N, E> {

    public static class Node<N,E> {
        N info; // информация об узле
        List<Edge<N, E>> in; // массив входящих ребер
        List<Edge<N, E>> out; // массив исходящих ребер
    }

    public static class Edge<N, E> {
        E info; // информация о ребре
        Node<N, E> out; // вершина, из которой исходит ребро
        Node<N, E> in; // вершина, в которую можно попасть
        // по этому ребру
        double weight; // стоимость перехода
    }

    List<Node<N, E>> nodes;
    List<Edge<N, E>> edges;

    static List<Edge> minTree(Graph graph){
        return  null;
    }

    public static void main(String[] args) {

    }
}