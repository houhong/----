package com.houhong.algorithm.tree;

import javax.validation.constraints.Max;

/**
 * @program: algorithm-work
 * @description: 树操作
 * @author: houhong
 * @create: 2022-08-14 15:48
 **/


class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class TreeOper {

    /**
     * 递归函数 无非就是函数意义啊
     * 设计递归函数第一步 是设计函数的意义。
     * 判断正不正确，使用数学归纳法去验证
     **/
    public Integer getHeight(TreeNode root) {

        if (root == null) {
            return null;
        }

        Integer left = getHeight(root.left);
        Integer right = getHeight(root.right);

        if (left < 0 || right < 0) {
            return -2;
        }
        if (Math.abs(left - right) > 1) {

            return -2;
        }
        return Math.max(left, right) + 1;

    }

    public Boolean isBalance(TreeNode root) {


        return getHeight(root) >= 0;
    }

    /**
     * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。如果存在，返回 true ；否则，返回 false 。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/path-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     **/


    /**
     * 定义递归函数：hasPathSum（TreeNode root, int targetSum） 表示有路径是的sum  =target
     **/
    public boolean hasPathSum(TreeNode root, int targetSum) {


        //基本情况
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }

        if (root.left != null && hasPathSum(root.left, targetSum - root.val)) {
            return true;
        }
        if (root.right != null && hasPathSum(root.right, targetSum - root.val)) {
            return true;
        }

        return false;
    }


}