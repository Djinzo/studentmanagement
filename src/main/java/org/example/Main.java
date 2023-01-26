package org.example;

import org.example.entities.Student;
import org.example.jpa.imp.RequestFactory;

public class Main {
    public static void main(String[] args) {
        RequestFactory<Student> studentRequestFactory = new RequestFactory<>();
        Student t = new Student("amine","goulzima");
        System.out.println(studentRequestFactory.insertRequest(t));
    }
}