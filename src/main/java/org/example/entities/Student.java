package org.example.entities;

import org.example.jpa.imp.PK;

public class Student {
    @PK
    public Long id;
    public String name;
    public String afterName;

    public String getAfterName() {
        return afterName;
    }

    public void setAfterName(String afterName) {
        this.afterName = afterName;
    }

    public Student(String name,String afterName) {
        this.name = name;
        this.afterName=afterName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
