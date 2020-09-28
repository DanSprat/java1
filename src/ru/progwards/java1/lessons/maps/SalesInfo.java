package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.util.*;

public class SalesInfo {
    ArrayList<Sale> saleList;
    private class Sale{
        String name;
        String item;
        int count;
        double price;

        public Sale(String name, String item, int count, double price) {
            this.name = name;
            this.item = item;
            this.count = count;
            this.price = price;
        }
    }

    public SalesInfo() {
        this.saleList = new ArrayList<>();
    }

    public int loadOrders(String fileName){
        int countOfLines = 0;
       try(FileReader fileReader = new FileReader(fileName)) {
           Scanner scanner = new Scanner(fileReader);

           while (scanner.hasNextLine()) {
               String s = scanner.nextLine();
               s = s.replaceAll("\\s+", " ");
               String[] strs = s.split(",");
               if (strs.length == 4) {
                   try {
                       int count = Integer.parseInt(strs[2].trim());
                       double price = Double.parseDouble(strs[3].trim());
                       saleList.add(new Sale(strs[0], strs[1], count, price));
                       countOfLines++;
                   } catch (Exception e) {
                       System.out.println(e.getMessage());
                   }
               }
           }
           return countOfLines;
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
       }
       return countOfLines;
    }
    public Map<String, Double> getGoods(){
        TreeMap<String,Double> goodsMap = new TreeMap<>();
        for (Sale sale:saleList){
            Double d = goodsMap.putIfAbsent(sale.item,sale.count*sale.price);
            if (d!=null){
                goodsMap.replace(sale.item,goodsMap.get(sale.item)+sale.count*sale.price);
            }
        }
        return goodsMap;
    }
    public Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers(){
        TreeMap<String,AbstractMap.SimpleEntry<Double, Integer>> entryTreeMap = new TreeMap<>();
        for (Sale sale:saleList){
            AbstractMap.SimpleEntry<Double, Integer> as = entryTreeMap.putIfAbsent(sale.name,new AbstractMap.SimpleEntry<>(sale.price*sale.count,sale.count));
            if (as!=null){
                entryTreeMap.replace(sale.name,new AbstractMap.SimpleEntry<>(entryTreeMap.get(sale.name).getKey()+sale.price*sale.count,entryTreeMap.get(sale.name).getValue()+sale.count));
            }
        }
        return entryTreeMap;
    }

    public static void main(String[] args){
       SalesInfo salesInfo = new SalesInfo();
      int i = salesInfo.loadOrders("s");
       System.out.println(salesInfo.getCustomers());
    }
}
