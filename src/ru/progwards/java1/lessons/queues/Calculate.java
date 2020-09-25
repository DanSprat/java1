package ru.progwards.java1.lessons.queues;

public class Calculate {
    public static double calculation1(){
        // 2.2*(3+12.1)
        StackCalc stackCalc = new StackCalc();
        stackCalc.push(2.2);
        stackCalc.push((double) 3);
        stackCalc.push((double) 12.1);
        stackCalc.add();
        stackCalc.mul();
        return stackCalc.pop();
    }
    public static double calculation2(){
       // (737.22+24)/(55.6-12.1)+(19-3.33)*(87+2*(13.001-9.2))
        StackCalc stackCalc = new StackCalc();
        stackCalc.push(737.22);
        stackCalc.push(24);
        stackCalc.add();
        stackCalc.push(55.6);
        stackCalc.push(12.1);
        stackCalc.sub();
        stackCalc.div();
        stackCalc.push(19);
        stackCalc.push(3.33);
        stackCalc.sub();
        stackCalc.push(87);
        stackCalc.push(2);
        stackCalc.push(13.001);
        stackCalc.push(9.2);
        stackCalc.sub();
        stackCalc.mul();
        stackCalc.add();
        stackCalc.mul();
        stackCalc.add();
        return stackCalc.pop();
    }

    public static void main(String[] args) {
        System.out.println("Ожидалось: " + 2.2*(3+12.1));
        System.out.println("Получено: "+ Calculate.calculation1());
        System.out.println("Ожидалось: " + 1499.912650344826);
        System.out.println("Получено: "+ Calculate.calculation2());

    }
}
