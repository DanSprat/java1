package ru.progwards.java2.lessons.patterns;

import javassist.compiler.ast.Pair;
import ru.progwards.java2.lessons.basetypes.DoubleHashTable;

import java.util.ArrayList;

public class FilterGPS {
    private GPS gps; // Предыдущее измерение
    private int count; // Количество измерений
    private double dispersion; // Дисперсия
    private double sigma; // Сигма
    private double expectation; // Математическое ожидание

    private final static double earthRadius = 6371.0;

    private void addToGps(double currentSpeed){
        expectation = ((count -1) / (double)count) * expectation + currentSpeed/count;
        dispersion = (count - 1) / (double)count * dispersion + Math.pow(currentSpeed - expectation,2) / count;
        sigma =  Math.sqrt(dispersion);
    }
    private double calculateSpeed(GPS newGPS){
        double radLatB = newGPS.lat * Math.PI / 180; // Широта в радианах текущего положения
        double radLonB = newGPS.lon *Math.PI / 180; // Долгота в радианах текущего положения
        double radLatA = gps.lat * Math.PI / 180; // Широта в радианах предыдущего местоположения
        double radLonA = gps.lon * Math.PI / 180;// Долготоа в радианах предыдущего местоположения
        double d =  Math.acos(Math.sin(radLatA)*Math.sin(radLatB) + Math.cos(radLatA)*Math.cos(radLatB)*Math.cos(radLonB - radLonA));
        double L = d * earthRadius;
        double currentSpeed = L / (newGPS.time - gps.time);
        return currentSpeed;
    }
    public boolean addPoint(GPS newGPS){
        // Можем предположить, что в начальной точке скорость равна нулю,но мат ожидание и дисперсию начнем считать
        // со второй точки.
        double currentSpeed = 0;
        if (count < 50){
            if (count != 0){
                currentSpeed = calculateSpeed(newGPS);
                addToGps(currentSpeed);
            }
            gps = newGPS;
            count++;
            return true;
        } else {
            currentSpeed = calculateSpeed(newGPS);
            if (currentSpeed > expectation - 3*sigma && currentSpeed < expectation + 3*sigma){
                addToGps(currentSpeed);
                gps = newGPS;
                count++;
                return true;
            }
            return false;
        }

    }

    public static void main(String[] args) {
        FilterGPS filterGPS = new FilterGPS();
        for (int i = 0; i<50;i++){
            GPS gps = new GPS(i,i,i);
            filterGPS.addPoint(gps);
        }
        System.out.println("Математическое ожидание: "+ filterGPS.expectation);
        System.out.println("Дисперсия: "+ filterGPS.dispersion);
        System.out.println("3 sigma: "+ filterGPS.sigma*3);
        GPS gps = new GPS(20,20,60);
        filterGPS.addPoint(gps);
        gps = new GPS(30,30,70);
        filterGPS.addPoint(gps);

    }

}
