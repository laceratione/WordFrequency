package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args){
        try{
            //находим все файлы из решения с форматом .txt
            String pathRoot = System.getProperty("user.dir");
            File dirRoot = new File(pathRoot);
            List<File> filesDirRoot = Arrays.asList(dirRoot.listFiles());

            List<String> filesText = new ArrayList<String>();

            for (File file:filesDirRoot) {
                String path = file.getAbsolutePath();
                if (path.contains(".txt"))
                    filesText.add(path);
            }

            //подготовка парсеров и инициализация потоков
            int lengthText = 0; //количество слов всего текста, а не конкретного файла
            Container container = new Container();//глобальный HashMap

            int countThreads = filesText.size();
            FreqParser[] parsers = new FreqParser[countThreads];
            Thread[] threads = new Thread[countThreads];

            for (int i = 0; i < parsers.length; i++){
                parsers[i] = new FreqParser(filesText.get(i), container);
                parsers[i].ReadTextFromFile();
                parsers[i].FormattingText();

                lengthText += parsers[i].getLength();

                threads[i] = new Thread(parsers[i]);
            }

            container.setLengthText(lengthText);

            //выполнение потоков
            for (Thread thread:threads) {
                thread.start();
            }

            //ожидание завершения потоков
            for (Thread thread:threads) {
                thread.join();
            }

            //выводим таблицу
            container.ShowMap();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
