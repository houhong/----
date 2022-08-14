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
    public List<List<Integer>> levelOrder(TreeNode root, int k, List<List<Integer>> ans) {

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

    public List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> ans = new ArrayList<>();
        levelOrder(root, 0, ans);
        return ans;

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
}










