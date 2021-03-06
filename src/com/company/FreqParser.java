package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FreqParser implements Runnable {
    private String file = "";
    private String pathFile;
    private String[] words;
    private String[] unusedSymbols = {",", "!", "\\?", "–", "-", "%", "\"", "«", "»", ":", "“", "”"};

    private HashMap<String, Double> wordFreq;

    public FreqParser(String pathFile) {
        this.pathFile = pathFile;
        wordFreq = new HashMap<String, Double>();
    }

    public HashMap<String, Double> getWordFreq() {
        return wordFreq;
    }

    public String getPathFile() {
        return pathFile;
    }

    //чтение текстового файла
    public void ReadTextFromFile(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(pathFile));
            String tmpStr;

            while ((tmpStr = reader.readLine()) != null) {
                file += tmpStr;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //форматирование текстового файла
    public void FormattingText(){
        try{
            for (int i = 0; i < unusedSymbols.length; i++){
                file = file.replaceAll(unusedSymbols[i], "");
            }

            file = file.replaceAll("\\.", " ");
            file = file.replaceAll("[\\s]{2,}", " ");
            file = file.toLowerCase();
            file = file.trim();

            words = file.split(" ");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //отобразить пары ключ-значение
    public void ShowMap(){
        DecimalFormat df = new DecimalFormat("##.#####");

        for (Map.Entry<String, Double> item : wordFreq.entrySet()) {
            System.out.println(item.getKey() + " - " + df.format(item.getValue()));
        }
    }

    //подсчет частоты слов
    @Override
    public void run(){
        ArrayList<String> uniqueWords = new ArrayList<String>();

        for (int i = 0; i < words.length; i++) {
            int count = 0;
            //если это слово ранее встречалось, то берем другое
            if (uniqueWords.contains(words[i]))
                continue;

            //считаем количество повторений
            for (int j = 0; j < words.length; j++) {
                if (words[i].equals(words[j]))
                    count++;
            }

            double freq = ((double)count/(double)words.length) * 100;
            uniqueWords.add(words[i]);
            wordFreq.put(words[i], freq);
        }
    }


}
