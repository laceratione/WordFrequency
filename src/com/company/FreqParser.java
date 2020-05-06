package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FreqParser implements Runnable {
    private String file = "";
    private String pathFile;
    private String[] words;
    private String[] unusedSymbols = {",", "!", "\\?", "–", "-", "%", "\"", "«", "»", ":"};

    private Container container;
    private ArrayList<String> uniqueWords;

    public FreqParser(String pathFile, Container container) {
        this.pathFile = pathFile;
        this.container = container;

        uniqueWords = new ArrayList<String>();
    }

    public int getLength(){
        return words.length;
    }

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

    @Override
    public void run(){
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

            uniqueWords.add(words[i]);
            container.AddWordFreq(words[i], count);
        }
    }


}
