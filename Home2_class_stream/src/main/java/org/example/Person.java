package org.example;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private int age;

    public Person() {
        this.name = "NULL";
        this.age = 0;
    }

    public Person(String name, int age) {
        setName(name);
        setAge(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0 || age > 150){
            throw new IllegalArgumentException();
        }
        this.age = age;
    }

    @Override
    public String toString() {
        return  "name:" + name + ' ' +
                "age:" + age;
    }
}
