package com.houhong.gc;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-10-11 11:19
 **/
public class ReferebceCouningGc {

    public Object instence = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {

        while (true){
            ReferebceCouningGc objA = new ReferebceCouningGc();
            ReferebceCouningGc objB = new ReferebceCouningGc();

            objB.instence = objA;
            objA.instence = objB;
            System.gc();
        }



    }

}