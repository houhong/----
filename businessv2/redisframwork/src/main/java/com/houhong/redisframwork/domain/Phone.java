package com.houhong.redisframwork.domain;

import java.io.Serializable;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-26 23:39
 **/
public class Phone implements Serializable {


    private static final long serialVersionUID = 8369604848395123304L;

    private Integer id;

    private String name;

    private String ranking;

    public Phone() {
    }

    public Phone(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
}