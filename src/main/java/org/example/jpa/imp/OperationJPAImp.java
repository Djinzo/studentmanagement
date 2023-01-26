package org.example.jpa.imp;

import org.example.dbconnection.DataBaseConnection;
import org.example.jpa.OperationJPA;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OperationJPAImp<T> implements OperationJPA<T> {
    private DataBaseConnection dataBaseConnection;
    private Connection connection;


    private RequestFactory<T> requestFactory;


    public OperationJPAImp(){
        dataBaseConnection= DataBaseConnection.getDataConnection();
        connection = dataBaseConnection.getConnection();
        requestFactory = new RequestFactory<>();
    }




    @Override
    public boolean insert(T object) throws SQLException {
        String request = requestFactory.insertRequest(object);
        Statement statement=connection.createStatement();
        statement.execute(request);
        return false;
    }

    @Override
    public T get(T object) {
        String reqeust = requestFactory.selectRequest(object);
        return null;
    }

    @Override
    public List<T> getBy(String colName, String value) {
        return null;
    }

    @Override
    public boolean delete(T object) {
        return false;
    }

    @Override
    public boolean edit(T object) {
        return false;
    }
}
