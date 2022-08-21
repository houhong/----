package com.houhong.springResource;

/**
 * @program: algorithm-work
 * @description: 汽车
 * @author: houhong
 * @create: 2022-08-22 00:15
 **/
public class Car {

    private String name;

    private Integer moeny;

    public Car(String name, Integer moeny) {
        this.name = name;
        this.moeny = moeny;
    }

    public Car() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMoeny() {
        return moeny;
    }

    public void setMoeny(Integer moeny) {
        this.moeny = moeny;
    }
}