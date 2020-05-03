package com.company;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Container {
    private int lengthText;
    private HashMap<String, Double> wordFreqGlobal;

    public Container(){
        wordFreqGlobal = new HashMap<String, Double>();
    }

    public void setLengthText(int lengthText) {
        this.lengthText = lengthText;
    }

    //добавляем или заменяем слово с процентом его частоты
    public void AddWordFreq(String k, int count) {
        ReentrantLock mutex = new ReentrantLock();

        try{
            mutex.lock();

            String key = k;
            Double currentFreq = ((double) count/(double)lengthText) * 100;

            if (!wordFreqGlobal.containsKey(key)){
                wordFreqGlobal.put(key, currentFreq);
            }
            else{
                double freq = wordFreqGlobal.get(key) + currentFreq;
                wordFreqGlobal.replace(key, freq);
            }
        }
        finally {
            mutex.unlock();
        }
    }

    public void ShowMap(){
        DecimalFormat df = new DecimalFormat("##.#####");

        for (Map.Entry<String, Double> item : wordFreqGlobal.entrySet()) {
            System.out.println(item.getKey() + " - " + df.format(item.getValue()));
        }
    }

}
