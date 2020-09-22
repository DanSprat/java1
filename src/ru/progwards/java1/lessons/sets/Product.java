package ru.progwards.java1.lessons.sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {
    private String code;

    public Product(String code){
        this.code=code;
    }
    public String getCode(){
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                '}';
    }
}
