package org.example.dbconnection;

import org.example.configuration.Configutaion;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    private static DataBaseConnection connectionSingle=null;
    private Connection connection;

    private Configutaion configutaion = new Configutaion();
    private DataBaseConnection(){
        try{
            String url = configutaion.getProp("sql.database.url");
            String username = configutaion.getProp("sql.database.username");
            String password = configutaion.getProp("sql.database.password");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println(connection);
            System.out.println("connection ok");
        } catch (Exception exp) {

            exp.printStackTrace();
        }

    }
    public Connection getConnection() {
        return connection;
    }

    public static DataBaseConnection getDataConnection(){
        if(connectionSingle==null){
            connectionSingle=new DataBaseConnection();
        }
        return connectionSingle;
    }
}
