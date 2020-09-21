package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductAnalytics {
    private List<Shop> shops;
    private List<Product> products;

    public ProductAnalytics(List<Shop> shops, List<Product> products) {
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
        Set<Product> tmp = new HashSet<>();
        for(Shop shop:shops){
            tmp.retainAll(shop.getProducts());
            existOnlyOneSet.addAll(tmp);
            tmp.clear();
        }
        return existOnlyOneSet;
    }

}
