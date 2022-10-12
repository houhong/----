package com.houhong.domain;

import java.io.Serializable;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-28 16:28
 **/
public class User implements Serializable {

    private Integer id;

    private String name;
    private Integer age;

    public User(Integer id, Integer age) {
        this.id = id;
        this.age = age;
    }

    public User(Integer id, String name, Integer age) {

        this.name = name;
        this.id = id;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}