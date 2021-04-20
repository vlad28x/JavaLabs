package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static Logger logger;//Журнал
    public static String pathLog;//Путь до файла - журнал
    public static String pathInput;//Путь до файла с последовательностью целых чисел
    public static final int ARRAY_SIZE = 11;//Размер массива для события
    private static String[] arrayString;//Последовательность целых чисел

    private static void init() {
        BufferedReader bufferedReader = null;
        Scanner scanner = new Scanner(System.in);
        Watcher watcher = new Watcher();//Создать объект приемника
        beWatched beWatched = new beWatched();//Создать объект источника
        beWatched.addObserver(watcher);//Добавить приемник
        try {
            System.out.println("Введите путь до файла \"Журнал\":");
            pathLog = scanner.nextLine();
            File fileLog = new File(pathLog);
            if (!fileLog.exists()) {
               if(fileLog.createNewFile()) {
                   System.out.println("Создан файл - журнал.");
               }
            }
            logger = new Logger(pathLog);
            beWatched.accessingOutput();//Генерация события
            logger.println("Введен путь до файла \"Журнал\"");
            System.out.println("Введите путь до файла с последовательностью целых чисел:");
            beWatched.accessingOutput();
            beWatched.changingVariable();
            pathInput = scanner.nextLine();
            beWatched.accessingOutput();
            logger.println("Введен путь до файла с последовательностью целых чисел");
            File input = new File(pathInput);
            if (!input.exists()) {
                System.out.println("Файла не существует!");
                beWatched.accessingOutput();
                logger.println("Файл с последовательностью целых чисел не существует");
                System.exit(0);
            }

            //Считываем данные из файла
            bufferedReader = new BufferedReader(new FileReader(input));
            arrayString = bufferedReader.readLine().split(" ");
            if (arrayString.length == ARRAY_SIZE) {
                beWatched.accessingOutput();
                beWatched.equalityOfElements();
            }
        } catch (NumberFormatException e) {
            System.out.println("В файле с последовательностью целых чисел заданы неверные данны!");
            beWatched.accessingOutput();
            logger.println("В файле с последовательностью целых чисел заданы неверные данные");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Системе не удается найти указанный путь!");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
	    init();
        Comparison comparison = new Comparison(arrayString);
        comparison.compare();//Сравнение количества определенных чисел
    }
}
