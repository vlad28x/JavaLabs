package com.company;

import java.util.Observable;
import java.util.Observer;

//Класс обозревателя
public class Watcher implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Перехват и обработка события: " + (String)arg);
        Main.logger.println("Перехват и обработка события: " + (String)arg);
    }
}