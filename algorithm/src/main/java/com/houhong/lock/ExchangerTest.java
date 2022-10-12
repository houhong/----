package com.houhong.lock;

import java.util.concurrent.Exchanger;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 21:52
 **/
public class ExchangerTest {

    public static void main(String[] args) {

        Exchanger<String> exchanger = new Exchanger<>();

        ExchangeThreadA threadA = new ExchangeThreadA(exchanger);
        ExchangeThreadB threadB = new ExchangeThreadB(exchanger);

        threadA.start();
        threadB.start();
    }
}