package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FiboMapCache {
    private Map<Integer, BigDecimal> fiboCache;
    public FiboMapCache(boolean cacheOn){
        if (cacheOn==true){
            fiboCache = new TreeMap<>();
        }
    }

}
