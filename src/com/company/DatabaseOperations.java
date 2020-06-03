package com.company;

import com.microsoft.sqlserver.jdbc.ISQLServerBulkData;

import java.sql.*;
import java.util.*;

public class DatabaseOperations {
    private Connection connection;
    private int idWord = 1;

    public DatabaseOperations() {
        //установление соединения с БД
        try{
            String connectionUrl = "jdbc:sqlserver://HOME-PC\\SQLEXPRESS;databaseName=Project1DB";
            String user = "sa";
            String pass = "123";
            connection = DriverManager.getConnection(connectionUrl, user, pass);

            System.out.println("Database connection successfully");
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
    private void WriteWords(int idFile, HashMap<String, Double> map){
        try{
            for (Map.Entry<String, Double> item : map.entrySet()) {
                String query = "insert into WordFreq (wordKey, wordFreq) values (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, item.getKey());
                statement.setDouble(2, item.getValue());
                statement.execute();

                String queryFW = "insert into FileWords (fileId, wordFreqId) values (?, ?)";
                PreparedStatement statementFW = connection.prepareStatement(queryFW);
                statementFW.setInt(1, idFile);
                statementFW.setInt(2, idWord++);
                statementFW.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //запись Map в БД
    public void WriteMap(FreqParser[] freqParsers){
        for (int i = 0; i < freqParsers.length; i++){
            WriteIntoFiles(freqParsers[i].getPathFile());
            WriteWords(i + 1, freqParsers[i].getWordFreq());
        }

        System.out.println("Write to Database completed successfully");
    }


}