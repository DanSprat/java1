package ru.progwards.java1.lessons.sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductAnalytics {
    private List<Shop> shops;
    private List<Product> products;

    public ProductAnalytics(List<Product> products,List<Shop> shops) {
        this.shops = shops;
        this.products = products;
    }
    public Set<Product> existInAll(){
        Set<Product> existSet = new HashSet<>(products);
        for(Shop shop:shops){
            existSet.retainAll(shop.getProducts());
        }
        return existSet;
    }
    public Set<Product> existAtListInOne(){
        Set<Product> existSet = new HashSet<>();
        for(Shop shop:shops){
            existSet.addAll(shop.getProducts());
        }
        return existSet;
    }
    public Set<Product> notExistInShops(){
        Set<Product> notExistSet = new HashSet<>(products);
        for(Shop shop:shops){
            notExistSet.removeAll(shop.getProducts());
        }
        return notExistSet;
    }
    public Set<Product> existOnlyInOne(){
        Set<Product> existOnlyOneSet = new HashSet<>();
       for (Product product: products){
           int count =0;
           for (Shop shop: shops){
               if (shop.getProducts().contains(product))
                   count++;
               if (count>1)
                   break;
           }
           if (count==1){
               existOnlyOneSet.add(product);
           }
       }
        return existOnlyOneSet;
    }
    public static void main(String[] args) {
        Product p1 = new Product("p1");
        Product p2 = new Product("p2");
        Product p3 = new Product("p3");
        Product p4 = new Product("p4");
        Product p5 = new Product("p5");
        Product p6 = new Product("p6");
        List<Product> forSH1 = new ArrayList<>();
        List<Product> forSH2= new ArrayList<>();
        List<Product> forSH3 = new ArrayList<>();
        List<Product> allProd = new ArrayList<>();
        forSH1.add(p1); forSH1.add(p2);forSH1.add(p3);
        forSH2.add(p2); forSH2.add(p3);forSH2.add(p4);
        forSH3.add(p3); forSH3.add(p4);forSH3.add(p5);
        allProd.add(p1); allProd.add(p2); allProd.add(p3); allProd.add(p4);allProd.add(p5);allProd.add(p6);
        Shop shop1 = new Shop(forSH1);
        Shop shop2 = new Shop(forSH2);
        Shop shop3 = new Shop(forSH3);
        List<Shop> shops = new ArrayList<>();
        shops.add(shop1);
        shops.add(shop2);
        shops.add(shop3);
        ProductAnalytics productAnalytics = new ProductAnalytics(allProd,shops);
        System.out.println(productAnalytics.existInAll());
        System.out.println(productAnalytics.existAtListInOne());
        System.out.println(productAnalytics.existOnlyInOne());
        System.out.println(productAnalytics.notExistInShops());

    }
}
