package ru.progwards.java1.lessons.files;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    public String shopId;
    public String orderId;
    public String customerId;
    public LocalDateTime datetime;
    public List<OrderItem> items;
    public double sum;

    public Order(String shopId, String orderId, String customerId, LocalDateTime datetime, List<OrderItem> items, double sum) {
        this.shopId = shopId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.datetime = datetime;
        this.items = items;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Order{" +
                "shopId='" + shopId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", datetime=" + datetime +
                ", sum=" + sum +
                '}';
    }
}
