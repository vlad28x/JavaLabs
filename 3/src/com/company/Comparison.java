package com.company;

import java.util.ArrayList;

public class Comparison{
    public String[] args;//Последовательность целых чисел
    ArrayList<Integer> evenPositiveInts;//Четные положительные числа
    ArrayList<Integer> oddNegativeInts;//Нечетные отрицательные числа

    Comparison(String[] args) {
        this.args = args;
        evenPositiveInts = new ArrayList<>();
        oddNegativeInts = new ArrayList<>();
    }

    public void compare() {
        int i;
        evenPositiveInts.clear();//очищаем, так как можем вызвать несколько раз этот метод
        oddNegativeInts.clear();
        for(String str : args) {
            i = Integer.parseInt(str);
            if((i >= 0) && (i % 2 == 0)) evenPositiveInts.add(i);
            else if(i % 2 == -1) oddNegativeInts.add(i);
        }
        print();
    }

    public void print() {
        int evenCount = evenPositiveInts.size();
        int oddCount = oddNegativeInts.size();
        String comp;
        Watcher watcher = new Watcher();
        beWatched beWatched = new beWatched();
        beWatched.addObserver(watcher);
        if(evenCount < oddCount) comp = "<";
        else if(evenCount > oddCount) comp = ">";
        else comp = "=";
        System.out.println("Четные положительные числа " + comp + " нечетные отрицательные числа.");
        beWatched.accessingOutput();
        Main.logger.println("Четные положительные числа " + comp + " нечетные отрицательные числа.");
        System.out.println("Четные положительные числа: ");
        beWatched.accessingOutput();
        Main.logger.println("Четные положительные числа: ");
        System.out.println(evenPositiveInts);
        beWatched.accessingOutput();
        Main.logger.println(evenPositiveInts.toString());
        System.out.println("Нечетные отрицательные числа: ");
        beWatched.accessingOutput();
        Main.logger.println("Нечетные отрицательные числа: ");
        System.out.println(oddNegativeInts);
        beWatched.accessingOutput();
        Main.logger.println(oddNegativeInts.toString());
    }
}
