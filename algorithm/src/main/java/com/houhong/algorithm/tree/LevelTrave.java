package com.houhong.algorithm.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: algorithm-work
 * @description: 递归层次遍历
 * @author: houhong
 * @create: 2022-08-14 14:19
 **/

class TreeNode {

    int val;
    TreeNodes left;
    TreeNodes right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNodes left, TreeNodes right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}


class Node {

    public int val;
    public List<Node> children;

    public Node() {
    }

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};

public class LevelTrave {

    /**
     * 二叉树层次遍历。
     * 【
     * <p>
     * [],
     * [],
     * []
     * <p>
     * 】
     **/
    public List<List<Integer>> levelOrder(TreeNodes root, int k, List<List<Integer>> ans) {

        if (root == null) {
            return null;
        }
        if (k == ans.size()) {
            ans.add(new ArrayList<Integer>());
        }
        ans.get(k).add(root.val);
        levelOrder(root.left, k + 1, ans);
        levelOrder(root.right, k + 1, ans);

        return ans;
    }

    public List<List<Integer>> levelOrder(TreeNodes root) {

        List<List<Integer>> ans = new ArrayList<>();
        levelOrder(root, 0, ans);
        return ans;

    }

    /**
     * 二叉树层次遍历。 自底向上输出结果
     * 【
     * <p>
     * [],
     * [],
     * []
     * <p>
     * 】
     **/
    public List<List<Integer>> levelOrderTravse(TreeNodes root) {

        List<List<Integer>> ans = new ArrayList<>();
        levelOrder(root, 0, ans);

        for (int start = 0, end = ans.size() - 1; start < end; start++, end--) {

            swap(start, end, ans);

        }
        return ans;

    }


    public List<List<Integer>> zigzagLevelOrder(TreeNodes root) {

        List<List<Integer>> ans = new ArrayList<>();
        levelOrder(root, 0, ans);

        for (int index = 0; index < ans.size(); index++) {

            if (index % 2 == 1) {

                List<Integer> tempList = ans.get(index);

                /**
                 *  双指针遍历
                 *
                 **/
                for (int start = 0, end = tempList.size() - 1; start < end; start++, end--) {

                    Integer startVal = tempList.get(start);
                    Integer endVal = tempList.get(end);

                    Integer temp = startVal;
                    startVal = endVal;
                    endVal = temp;

                    tempList.set(start, startVal);
                    tempList.set(end, endVal);
                }

            }

        }

        return null;
    }

    private static void swap(int first, int second, List<List<Integer>> ans) {

        List<Integer> temp = new ArrayList<>();

        List<Integer> fi = ans.get(first);
        List<Integer> end = ans.get(second);
        temp = fi;
        fi = end;
        end = temp;
        ans.set(first, fi);
        ans.set(second, end);

    }


    public List<List<Integer>> traceLevelForN(Node root, int k, List<List<Integer>> ans) {

        if (root == null) {
            return null;
        }
        if (k == ans.size()) {
            ans.add(new ArrayList<Integer>());
        }
        ans.get(k).add(root.val);
        for (Node node : root.children) {
            traceLevelForN(node, k + 1, ans);
        }
        return ans;
    }

    public List<List<Integer>> levelOrderForN(Node root) {

        List<List<Integer>> ans = new ArrayList<>();
        traceLevelForN(root, 0, ans);
        return ans;
    }

    public static void main(String[] args) {

        List<List<Integer>> ans = new ArrayList<>();

        List<Integer> fi = new ArrayList<>();
        fi.add(0);
        ans.add(fi);

        List<Integer> se = new ArrayList<>();
        se.add(0);
        se.add(1);
        ans.add(se);


        List<Integer> thi = new ArrayList<>();
        thi.add(0);
        thi.add(1);
        thi.add(2);
        ans.add(thi);

        for (int start = 0, end = ans.size() - 1; start < end; start++, end--) {

            swap(start, end, ans);

        }

        for (List<Integer> an : ans) {

            for (Integer integer : an) {
                System.out.print(integer);
            }
            System.out.println();
        }


    }
}










