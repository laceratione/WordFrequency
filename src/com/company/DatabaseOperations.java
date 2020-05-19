package com.company;

import java.sql.*;
import java.util.*;

public class DatabaseOperations {
    private Connection connection;

    public DatabaseOperations() {
        try{
            String connectionUrl = "jdbc:sqlserver://HOME-PC\\SQLEXPRESS;databaseName=Project1DB";
            String user = "sa";
            String pass = "123";
            connection = DriverManager.getConnection(connectionUrl, user, pass);//открытие/закрытие соединения вынести в отдельный метод
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //запись в таблицу File
    private void WriteIntoFiles(String path){
        try{
            String query = "insert into Files (filePath) values (?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, path);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //запись в таблицу WordFreq и FilesWords
    private void WriteWords(int idFile, String path, HashMap<String, Double> map){
        try{
            int i = 1;
            for (Map.Entry<String, Double> item : map.entrySet()) {
                String query = "insert into WordFreq (wordKey, wordFreq) values (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, item.getKey());
                statement.setDouble(2, item.getValue());
                statement.execute();

                //подумать, как эффективнее: оставить так, или вынести в метод
                String queryFW = "insert into FileWords (fileId, wordFreqId) values (?, ?)";
                PreparedStatement statementFW = connection.prepareStatement(queryFW);
                statementFW.setInt(1, idFile);
                statementFW.setInt(2, i++);
                statementFW.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void WriteMap(FreqParser[] freqParsers){
        for (int i = 0; i < freqParsers.length; i++){
            WriteIntoFiles(freqParsers[i].getPathFile());
            WriteWords(i + 1, freqParsers[i].getPathFile(), freqParsers[i].getWordFreq());
        }
    }

}
