package com.yehui.starter.starter;

import java.io.Serializable;
import java.util.StringJoiner;


public class Student implements Serializable {
    
    private int id;
    private String name;

    public Student() {

    }

    public Student(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void init(){
        System.out.println("hello...........");
    }
    
    public static Student create(){
        return new Student(101,"KK101");
    }

    @Override
    public String toString() {

        return new StringJoiner(", ", Student.class.getSimpleName() + "[", "]").add("id=" + id)
            .add("name='" + name + "'")
            .toString();
    }
}
