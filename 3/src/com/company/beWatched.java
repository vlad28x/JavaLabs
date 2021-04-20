package com.company;

import java.util.Observable;

//Класс наблюдаемого объекта
public class beWatched extends Observable {
    //Событие - изменение указанной переменной pathInput
    void changingVariable() {
        setChanged();
        notifyObservers("изменение указанной переменной - pathInput.");
    }

    //Событие - обращение к потоку вывода в указанный файл
    void accessingOutput() {
        setChanged();
        notifyObservers("обращение к потоку вывода в указанный файл - " + Main.pathLog);
    }

    //Событие - в массиве число элементов равно указанному
    void equalityOfElements() {
        setChanged();
        notifyObservers("в массиве число элементов равно указанному - " + Main.ARRAY_SIZE);
    }
}
