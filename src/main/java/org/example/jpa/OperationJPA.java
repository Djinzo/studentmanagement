package org.example.jpa;

import java.sql.SQLException;
import java.util.List;

public interface OperationJPA<T> {

    boolean insert(T object) throws SQLException;

    T get(T object);

    List<T> getBy(String colName,String value);

    boolean delete(T object);

    boolean edit(T object);

}
