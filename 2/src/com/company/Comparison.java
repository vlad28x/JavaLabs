package com.company;

import java.util.ArrayList;

public class Comparison implements IConst, IFunction{
    public String[] args;//Аргументы командной строки
    ArrayList<Integer> evenPositiveInts;//Четные положительные числа
    ArrayList<Integer> oddNegativeInts;//Нечетные отрицательные числа

    Comparison(String[] args) {
        this.args = args;
        evenPositiveInts = new ArrayList<>();
        oddNegativeInts = new ArrayList<>();
    }

    void arrayMinimumSize() throws ArrayMinimumSize{//В массиве число элементов меньше, чем некоторое число
        if(args.length < minimumSize) throw new ArrayMinimumSize();
    }

    void arrayHasNumber() throws ArrayHasNumber{//Массив содержит некоторое число
        for(String str : args) {
            if(Integer.parseInt(str) == number) throw new ArrayHasNumber();
        }
    }

    void arraySize() throws ArraySize{
        if(args.length == size) throw new ArraySize();
    }

    @Override
    public void compare() {
        try{
            arrayMinimumSize();
            arrayHasNumber();
            arraySize();
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
        catch (ArrayMinimumSize | ArrayHasNumber | ArraySize e) {
            System.out.println(e);
        }
    }

    @Override
    public void print() {
        int evenCount = evenPositiveInts.size();
        int oddCount = oddNegativeInts.size();
        String comp;
        if(evenCount < oddCount) comp = "<";
        else if(evenCount > oddCount) comp = ">";
        else comp = "=";
        System.out.println("Четные положительные числа " + comp + " нечетные отрицательные числа.");
        System.out.print("Четные положительные числа: ");
        System.out.println(evenPositiveInts);
        System.out.print("Нечетные отрицательные числа: ");
        System.out.println(oddNegativeInts);
    }
}
