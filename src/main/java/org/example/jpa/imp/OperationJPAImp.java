package org.example.jpa.imp;

import org.example.dbconnection.DataBaseConnection;
import org.example.jpa.OperationJPA;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OperationJPAImp<T> implements OperationJPA<T> {
    private DataBaseConnection dataBaseConnection;
    private Connection connection;


    private RequestFactory<T> requestFactory;


    public OperationJPAImp() {
        dataBaseConnection = DataBaseConnection.getDataConnection();
        connection = dataBaseConnection.getConnection();
        requestFactory = new RequestFactory<>();
    }


    @Override
    public boolean insert(T object) {
        String request = requestFactory.insertRequest(object);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(request);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public T get(T object) {
        String reqeust = requestFactory.selectRequest(object);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(reqeust);
            resultSet.next();
            for (Method m : object.getClass().getMethods()) {
                if (m.getName().contains("set")) {
                    m.invoke(object, resultSet.getObject(m.getName().replace("set", "").toLowerCase()));
                }
            }
        } catch (SQLException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    @Override
    public List<T> getBy(T object, String colName, String value) {
        String reqeust = requestFactory.selectByCollRequest(object, colName, value);
        List<T> objectList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(reqeust);
            while (resultSet.next()) {
                T ob = (T) object.getClass().newInstance();
                for (Method m : ob.getClass().getMethods()) {
                    if (m.getName().contains("set")) {
                        m.invoke(m, resultSet.getObject(m.getName().replace("set", "").toLowerCase()));
                    }
                }
                objectList.add(ob);
            }

        } catch (SQLException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        return objectList;
    }

    @Override
    public boolean delete(T object) {
        String request = requestFactory.deleteRequest(object);
        try {
            Statement statement = connection.createStatement();
            statement.execute(request);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean edit(T object) {
        String request = requestFactory.updateRequest(object);
        try {
            Statement statement = connection.createStatement();
            statement.execute(request);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
