package com.houhong.algorithm.tree;

import java.lang.reflect.Proxy;

/**
 * @program: algorithm-work
 * @description: 满二叉树
 * @author: houhong
 * @create: 2022-08-14 10:36
 **/
public class FullBinaryTree {


    /**
     * 1
     * / \
     * 2  3
     * / \ /
     * 4  5 6
     * <p>
     * 定义： 2 * i 表示左孩子
     * 2* i + 1 表示右孩子 根据这个定义：可以用一段连续内存记录：
     **/

    public static void main(String[] args) {


        Integer[] fullBinaryTreeArr = new Integer[10];


        fullBinaryTreeArr[0] = 1;
        fullBinaryTreeArr[1] = 2;
        fullBinaryTreeArr[2] = 3;
        fullBinaryTreeArr[3] = 4;
        fullBinaryTreeArr[4] = 5;
        fullBinaryTreeArr[5] = 6;


    }


}