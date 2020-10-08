package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderProcessor {
    ArrayList<Order> orders;
    private enum Mode {LEFT_INTERVAL,RIGHT_INTERVAL,ALL};
    Mode mode;
    private Path dir;
    public OrderProcessor(String startPath){
        orders = new ArrayList<>();
        dir = Paths.get(startPath);
        mode=Mode.ALL;
    }
    public int loadOrders(LocalDate start, LocalDate finish, String shopId){
        if (start==null){
            mode=Mode.LEFT_INTERVAL;
            if (finish==null){
                mode = Mode.ALL;
            }
        } else {
            mode = Mode.RIGHT_INTERVAL;
        }
        final Integer[] count = {0};
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.csv");
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    if (pathMatcher.matches(path)) {
                        List<String> arrayList;
                        try {
                            String[] strings = path.getFileName().toString().replace(".csv", "").split("-");
                            if (strings.length != 3 || strings[0].length() != 3 || strings[1].length() != 6 || strings[2].length() != 4) {
                                return FileVisitResult.CONTINUE;
                            }
                            if (mode == Mode.ALL ||
                                    mode == Mode.LEFT_INTERVAL && ZonedDateTime.parse(Files.getLastModifiedTime(path).toString()).toLocalDateTime().isBefore(LocalDateTime.from(finish)) ||
                                    mode == Mode.RIGHT_INTERVAL && ZonedDateTime.parse(Files.getLastModifiedTime(path).toString()).toLocalDateTime().isAfter(LocalDateTime.from(start))) {
                                if (shopId == null || shopId == strings[0]) {
                                    arrayList = Files.readAllLines(path, Charset.forName("windows-1251"));
                                    List<OrderItem> orderItems = new ArrayList<>();
                                    double sum = 0;
                                    for (String s : arrayList) {
                                        String[] strs = s.split(",");
                                        orderItems.add(new OrderItem(strs[0], Integer.parseInt(strs[1].trim()), Double.parseDouble(strs[2].trim())));
                                        sum += Integer.parseInt(strs[1].trim()) * Double.parseDouble(strs[2].trim());
                                    }
                                    orders.add(new Order(strings[0], strings[1], strings[2], ZonedDateTime.parse(Files.getLastModifiedTime(path).toString()).toLocalDateTime(), orderItems, sum));
                                }
                            }
                        } catch (Exception ex){
                            count[0]++;
                            return FileVisitResult.CONTINUE;
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return orders.size();//count[0];
    }
    public List<Order> process(String shopId){
        ArrayList<Order> processList= new ArrayList<>();
        for (Order x: orders){
            if (shopId==null || x.shopId.equals(shopId)){
                processList.add(x);
            }
        }
        processList.sort(new Comparator<Order>() {
            @Override
            public int compare(Order order, Order t1) {
                if (order.datetime.isBefore(t1.datetime))
                    return -1;
                if (order.datetime.isAfter(t1.datetime))
                    return 1;
                return 0;
            }
        });
        return processList;
    }
    public Map<String, Double> statisticsByShop(){
        TreeMap<String,Double> treeMap = new TreeMap<>();
        for (Order x : orders){
            if (treeMap.containsKey(x.shopId)){
                treeMap.replace(x.shopId,treeMap.get(x.shopId)+x.sum);
            }
            else {
                treeMap.put(x.shopId,x.sum);
            }
        }
        return treeMap;
    }
    public Map<String, Double> statisticsByGoods(){
        TreeMap<String,Double> treeMap = new TreeMap<>();
        for (Order x: orders){
            for (OrderItem y:x.items){
                if (treeMap.containsKey(y.googsName)){
                    treeMap.replace(y.googsName,treeMap.get(y.googsName)+y.price*y.count);
                } else {
                    treeMap.put(y.googsName,y.price*y.count);
                }
            }
        }
        return treeMap;
    }
    public Map<LocalDate, Double> statisticsByDay(){
        TreeMap<LocalDate,Double> treeMap = new TreeMap<>();
        for (Order x: orders){
            if (treeMap.containsKey(x.datetime)){
                treeMap.replace(x.datetime.toLocalDate(),treeMap.get(x.datetime)+x.sum);
            } else {
                treeMap.put(x.datetime.toLocalDate(),x.sum);
            }
        }
        return treeMap;
    }

    public static void main(String[] args){
        OrderProcessor orderProcessor= new OrderProcessor("C:\\Users\\Work\\IdeaProjects\\Progwards\\test");
        System.out.println(orderProcessor.loadOrders(null,null,null));
        System.out.println(orderProcessor.process(null));
        System.out.println((orderProcessor.statisticsByShop()));
        System.out.println(orderProcessor.statisticsByGoods());
    }


}
