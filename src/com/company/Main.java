package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){
        try{
            String file = "";
            String pathFile = "text1.txt";
            HashMap<String, Double> wordFreq = new HashMap<String, Double>();

            //чтение текстового файла
            BufferedReader reader = new BufferedReader(new FileReader(pathFile));
            String tmpStr = "";

            while ((tmpStr = reader.readLine()) != null){
                file += tmpStr;
            }

            //форматирование текста и удаление ненужных символов
            String[] elems = {",", "!", "\\?", "–", "-", "%", "\"", "«", "»", ":"};

            file = file.trim();
            file = file.toLowerCase();

            for (int i = 0; i < elems.length; i++)
                file = file.replaceAll(elems[i] , "");

            file = file.replaceAll("\\." , " ");
            file = file.replaceAll("[\\s]{2,}", " ");

            //подсчет частоты слов
            String[] words = file.split(" ");

            for (int i = 0; i < words.length; i++){
                int count = 0;
                //если это слово ранее встречалось, то берем другое
                boolean nextWord = false;

                if (wordFreq.containsValue(words[i]))
                        nextWord = true;

                if (nextWord)
                    continue;

                for (int j = 0; j < words.length; j++){
                    if (words[i].equals(words[j]))
                        count++;
                }

                double freq = ((double) count/(double) words.length)*100;
                wordFreq.put(words[i], freq);
            }

            //вывод таблицы
            DecimalFormat df = new DecimalFormat("##.#####");

            for(Map.Entry<String, Double> item:wordFreq.entrySet()){
                System.out.println(item.getKey() + " - " + df.format(item.getValue()));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
