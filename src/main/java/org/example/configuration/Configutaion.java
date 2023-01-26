package org.example.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configutaion {
    Properties prop = new Properties();


    public Configutaion() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("/local.properties");
        try {
            prop.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProp(String s){
        return prop.getProperty(s);
    }


}
