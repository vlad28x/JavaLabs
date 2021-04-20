package com.company;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Класс журнала
public class Logger {
    private final String PATH_LOG;//Путь до файла - журнал

    public Logger(String pathLog) {
        this.PATH_LOG = pathLog;
    }

    //Вывод строки в журнал
    public void println(String message) {
        try(PrintWriter printWriter = new PrintWriter(new FileWriter(PATH_LOG, true))) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");//Паттерн для текущей даты и времени
            LocalDateTime now = LocalDateTime.now();//Текущие дата и время
            printWriter.println("[" + dtf.format(now) + "] " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
