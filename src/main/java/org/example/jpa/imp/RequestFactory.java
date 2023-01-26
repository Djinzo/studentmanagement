package org.example.jpa.imp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestFactory<T> {


    private String getClassName(T object){
        String[] result = object.getClass().getName().split("\\.");
        String className = result[result.length-1];
        return className;
    }
    public String insertRequest(T object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into ")
                .append(getClassName(object)).append("(");
        for (Method m : object.getClass().getMethods()) {
            if (m.getName().contains("get") && !m.getName().equals("getClass")) {
                stringBuilder.append(m.getName().replace("get", "").toLowerCase()).append(",");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(") values (");
        for (Method m : object.getClass().getMethods()) {
            if (m.getName().contains("get") && !m.getName().equals("getClass")) {
                try {
                    stringBuilder.append(m.invoke(object)).append(",");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(")");
        return stringBuilder.toString();

    }

    public String selectRequest(T object) {
        StringBuilder stringBuilder = new StringBuilder();
        Field pkField = null;
        for (Field f : object.getClass().getFields()) {
            if (f.isAnnotationPresent(PK.class)) {
                pkField = f;
                break;
            }
        }
        if (pkField != null) {
            Method pkMethod = null;
            for (Method m : object.getClass().getMethods()) {
                if (m.getName().equalsIgnoreCase("get" + pkField.getName())) {
                    pkMethod = m;
                    break;
                }
            }
            if (pkMethod != null) {
                try {
                    stringBuilder.append("select * from " + getClassName(object) + "where " + pkField.getName() + "=" + pkMethod.invoke(object));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return stringBuilder.toString();
    }
}
