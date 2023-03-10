package org.example.jpa;

import java.sql.SQLException;
import java.util.List;

public interface OperationJPA<T> {

    boolean insert(T object) throws SQLException;

    T get(T object) throws SQLException;

    List<T> getBy(T object,String colName,String value);

    boolean delete(T object);

    boolean edit(T object);

}
