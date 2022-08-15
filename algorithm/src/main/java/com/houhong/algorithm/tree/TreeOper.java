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


    /**
     * 根据前序遍历和后序遍历 完成二叉树构建
     * <p>
     * 1: 找根节点
     * 2: 递归构建左,挂靠？
     * 3： 递归构建右，挂靠
     * <p>
     * 定义递归函数定义：根据前序遍历和中序遍历构建树 todo  错误版本
     **/
    public TreeNode buildTree(int[] preorder, int[] inorder) {


        TreeNode root = null;
        //base case
        if (preorder.length == 0) {
            return null;
        }
        int rootVal = preorder[0];
        root = new TreeNode();
        root.val = rootVal;

        int tempInIndex = 0;
        for (; tempInIndex < inorder.length; tempInIndex++) {

            int inRootVal = inorder[tempInIndex];
            if (inRootVal == rootVal) {
                break;
            }
        }

        int[] leftArr = new int[tempInIndex];
        int[] rightAr = new int[inorder.length - tempInIndex];

        for (int index = 0; index < tempInIndex; index++) {
            leftArr[index] = inorder[index];
        }

        for (int index = tempInIndex; index < inorder.length; index++) {
            rightAr[index] = inorder[index];
        }

        //在根据length 获取前序遍历
        int[] leftPreArr = new int[tempInIndex];
        int[] rightPreAr = new int[inorder.length - tempInIndex];

        for (int index = 1; index <= tempInIndex; index++) {
            leftPreArr[index] = preorder[index];
        }

        for (int index = tempInIndex; index < preorder.length; index++) {
            rightPreAr[index] = preorder[index];
        }


        //挂左孩子 -- 缩小范围
        TreeNode leftChild = buildTree(leftPreArr, leftArr);
        root.left = leftChild;
        //挂右孩子
        TreeNode rightChild = buildTree(rightPreAr, rightAr);
        root.right = rightChild;


        return root;
    }


}