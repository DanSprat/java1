package ru.progwards.java2.lessons.graph;

import ru.progwards.java2.lessons.graph.Graph.Edge;
import ru.progwards.java2.lessons.graph.Graph.Node;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Boruvka {
    // Можно было бы упростить алгоритм добавив поле parent  в java2.lessons.graph.Graph.Node, но я подумал что не очень хорошо менять ТЗ.
    public static class FamilyNode<N, E> {

        FamilyNode<N, E> parent;
        FamilyNode <N,E> child;
        Node<N, E> node;


        public FamilyNode(Node<N, E> node, Node<N, E> parent) {
            this.node = node;
            this.parent = this;
            this.child = this;
        }

    }

    static <N, E> FamilyNode<N, E> find(FamilyNode<N, E> node) {
        FamilyNode<N, E> tmp = node;
        while (tmp != tmp.parent) {
            tmp = tmp.parent;
        }
        return tmp;
    }

    static <N, E> void merge(FamilyNode<N, E> u, FamilyNode<N, E> v) {
        FamilyNode<N, E> uParent = find(u);
        uParent.parent = v;
    }

    static <N, E> List<Edge<N, E>> minTree(Graph<N, E> graph) {
        ArrayList<Edge<N, E>> ost = new ArrayList<>();
        ArrayList<FamilyNode<N, E>> sets = new ArrayList<>();
        HashMap<Node, FamilyNode> map = new HashMap<>();
        for (var node : graph.nodes) {
            FamilyNode<N, E> familyNode = new FamilyNode<N, E>(node, node);
            sets.add(familyNode);
            map.put(node, familyNode);
        }
        int sizeOfTree = graph.nodes.size() - 1;
        while (ost.size() < sizeOfTree) {
            HashMap<FamilyNode<N, E>, Edge<N, E>> minWights = new HashMap<>();
            for (var edge : graph.edges) {
                FamilyNode<N, E> nodeA = map.get(edge.in);
                FamilyNode<N, E> nodeB = map.get(edge.out);
                FamilyNode<N, E> parentA = find(nodeA);
                FamilyNode<N, E> parentB = find(nodeB);
                if (parentA != parentB) {
                   Edge <N,E> minWeightA = minWights.get(parentA);
                   Edge<N,E> minWeightB = minWights.get(parentB);
                    if (minWeightA ==null || edge.weight < minWeightA.weight)
                        minWights.put(parentA, edge);
                    if (minWeightB == null || edge.weight < minWeightB.weight)
                        minWights.put(parentB, edge);
                }
            }
            for (var mins : minWights.entrySet()) {
                Edge<N, E> edge = mins.getValue();
                FamilyNode<N, E> nodeA = map.get(edge.in);
                FamilyNode<N, E> nodeB = map.get(edge.out);
                if (find(nodeA) != find(nodeB)) {
                    merge(nodeA, nodeB);
                    ost.add(edge);
                }
            }
        }
        return ost;
    }

    public static void main(String[] args) {
        Graph<String, String> graph = new Graph<>();
        ArrayList<Node<String,String>> nodes = new ArrayList<>();
        ArrayList<Node<String,String>> edges = new ArrayList<>();
        Node<String, String> A = new Node<>("A");
        A.in = A.out;
        Node<String, String> B = new Node<>("B");
        B.in = B.out;
        Node<String, String> C = new Node<>("C");
        C.in = C.out;
        Node<String, String> D = new Node<>("D");
        D.in = D.out;
        Node<String, String> E = new Node<>("E");
        E.in = E.out;
        Node<String, String> F = new Node<>("F");
        F.in = F.out;
        Node<String, String> G = new Node<>("G");
        G.in = G.out;

        Edge<String, String> AB = new Edge<>("AB", A, B, 7.0);
        Edge<String, String> BC = new Edge<>("BC", B, C, 11.0);
        Edge<String, String> AD = new Edge<>("AD", A, D, 4.0);
        Edge<String, String> DF = new Edge<>("DF", D, F, 6.0);
        Edge<String, String> FG = new Edge<>("FG", F, G, 13.0);
        Edge<String, String> GE = new Edge<>("GE", G, E, 8.0);
        Edge<String, String> EC = new Edge<>("EC", E, C, 5.0);
        Edge<String, String> DB = new Edge<>("DB", D, B, 9.0);
        Edge<String, String> BE = new Edge<>("BE", B, E, 10.0);
        Edge<String, String> DE = new Edge<>("DE", D, E, 15.0);
        Edge<String, String> FE = new Edge<>("FE", E, C, 12.0);

        A.in.addAll(Arrays.asList(AB,AD));
        B.in.addAll(Arrays.asList(AB,BC,BE,DB));
        C.in.addAll(Arrays.asList(BC,EC));
        D.in.addAll(Arrays.asList(DB,AD,DE,DF));
        E.in.addAll(Arrays.asList(EC,GE,BE,DE));
        F.in.addAll(Arrays.asList(DF,FE,FG));
        G.in.addAll(Arrays.asList(FG,GE));

        graph.nodes = Arrays.asList(A,B,C,D,E,F,G);
        graph.edges = Arrays.asList(AB,BC,AD,DF,FG,GE,EC,DB,BE,DE,FE);
        System.out.println("All Done");

        List <Edge<String,String>> ost = minTree(graph);
        ost.forEach((x)-> System.out.printf("%5s",x.info));
        System.out.println();
        ost.forEach((x)-> System.out.printf("%5.0f",x.weight));
        URL iconURL = ClassLoader.getSystemResource("Boruvka.png");
        Icon icon = new ImageIcon(iconURL);
        JOptionPane.showMessageDialog(null,null,null,JOptionPane.INFORMATION_MESSAGE,icon);
    }
}