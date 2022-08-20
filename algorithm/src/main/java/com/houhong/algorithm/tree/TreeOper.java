package com.houhong.algorithm.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @program: algorithm-work
 * @description: 树操作
 * @author: houhong
 * @create: 2022-08-14 15:48
 **/


class TreeNodes {

    int val;
    TreeNodes left;
    TreeNodes right;

    TreeNodes() {
    }

    TreeNodes(int val) {
        this.val = val;
    }

    TreeNodes(int val, TreeNodes left, TreeNodes right) {
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
    public Integer getHeight(TreeNodes root) {

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

    public Boolean isBalance(TreeNodes root) {


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
    public boolean hasPathSum(TreeNodes root, int targetSum) {


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
    public TreeNodes buildTree(int[] preorder, int[] inorder) {


        TreeNodes root = null;
        //base case
        if (preorder.length == 0) {
            return null;
        }
        int rootVal = preorder[0];
        root = new TreeNodes();
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
        TreeNodes leftChild = buildTree(leftPreArr, leftArr);
        root.left = leftChild;
        //挂右孩子
        TreeNodes rightChild = buildTree(rightPreAr, rightAr);
        root.right = rightChild;


        return root;
    }


    /**
     * [1  2 3 4 5 6] pre
     * [6  3  4 5  2] in
     * <p>
     * 假设 pos =  3 所以在 pos 后面的肯定是右子树
     */
    public TreeNodes buildTreeV2(int[] preorder, int[] inorder) {


        TreeNodes root = null;
        //base case
        if (preorder.length == 0) {
            return null;
        }
        int pos = 0;
        while (inorder[pos] != preorder[0]) {
            pos++;
        }


        /**
         *  切分左孩子还是右孩子
         *
         **/
        int[] leftPre = new int[pos];
        int[] leftIn = new int[pos];
        for (int i = 0; i < pos; i++) {

            leftPre[i] = preorder[i + 1];
            leftIn[i] = inorder[i];
        }

        int[] rightPre = new int[preorder.length - pos - 1];
        int[] rightIn = new int[preorder.length - pos - 1];
        for (int i = pos + 1, j = 0; i < preorder.length; i++, j++) {

            rightPre[j] = preorder[i];
            rightIn[j] = inorder[i];
        }

        root = new TreeNodes(preorder[0]);
        //获取左子树
        TreeNodes leftChild = buildTreeV2(leftPre, leftIn);
        root.left = leftChild;
        //获取右子树
        TreeNodes rightChild = buildTreeV2(rightPre, rightIn);
        root.right = rightChild;

        return root;
    }


    public static void main(String[] args) {

        TreeNodes test1 = new TreeNodes();
        TreeNodes test2 = new TreeNodes();
        TreeNodes test3 = new TreeNodes();
        test3.left = new TreeNodes();
        test3.right = new TreeNodes();

        testM(test3, test2);
        testM(test3, test1);
        System.out.println(test1 == test2);


    }

    public static void testM(TreeNodes ori, TreeNodes tar) {

        tar = ori;
    }


    /**
     * 前序遍历非递归
     **/
    public List<Integer> traverseByStack(TreeNodes root) {

        List<Integer> res = new ArrayList<>();

        if (root == null) {
            return res;
        }
        Stack<TreeNodes> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {

            TreeNodes pop = stack.pop();
            res.add(pop.val);

            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
        }
        return res;
    }


    /**
     * 前序遍历非递归
     **/
    public List<Integer> traverseInByStack(TreeNodes root) {

        List<Integer> res = new ArrayList<>();

        if (root == null) {
            return res;
        }
        Stack<TreeNodes> stack = new Stack<>();
        stack.push(root);


        //处理回溯的时候 又走左孩子
        boolean isLeft = true;

        while (!stack.isEmpty()) {

            TreeNodes pop = stack.pop();

            if (isLeft && pop.left != null) {

                stack.push(pop);
                stack.push(pop.left);
            } else {

                res.add(pop.val);
                //如果右孩子处理完毕后 表明 左孩子已经处理了。回溯的时候 就不应该再处理左孩子了
                if (isLeft = pop.right != null) {
                    stack.push(pop.right);
                }
            }
        }
        return res;
    }

    /**
     * 后序列遍历 == 左 右 根   --> 处理  根 右  左
     **/
    public List<Integer> postOrder(TreeNodes root) {

        List<Integer> res = new ArrayList<>();

        Stack<TreeNodes> result = new Stack<TreeNodes>();

        if (root == null) {
            return res;
        }

        Stack<TreeNodes> stack = new Stack<>();

        stack.push(root);
        while (!stack.isEmpty()) {

            root = stack.pop();
            result.push(root);
            if (root.left != null) {
                stack.add(root.left);
            }
            if (root.right != null) {
                stack.add(root.right);
            }
        }

        //倒叙
        while (!result.isEmpty()) {

            TreeNodes pop = result.pop();
            res.add(pop.val);
        }
        return res;
    }


    public boolean isCousins(TreeNodes root, int x, int y) {

        TreeNodes lPar = new TreeNodes();
        TreeNodes rPar = new TreeNodes();
        int xDepth = dfs(root, x, lPar);
        int yDepth = dfs(root, y, rPar);

        if (xDepth == yDepth || lPar != rPar) {
            return false;
        }
        return true;
    }


    public List<List<Integer>> levelTree(TreeNodes root, int k, List<List<Integer>> res) {

        if (root == null) {
            return res;
        }
        if (k == res.size()) {

            res.add(new ArrayList<>());
        }
        res.get(k).add(root.val);

        levelTree(root.left, k + 1, res);
        levelTree(root.right, k + 1, res);

        return res;

    }


    /**
     * 使用dfs 求出target的深度
     */
    public int dfs(TreeNodes root, int target, TreeNodes fatherNode) {

        if (root == null) {
            return -1;
        }
        if (root.val == target) {
            return 0;
        }

        fatherNode = root;
        //从左孩子去找
        int level = dfs(root.left, target, fatherNode);
        if (level != -1) {
            return level + 1;
        }
        //还原
        fatherNode = root;
        //从右孩子去找
        level = dfs(root.right, target, fatherNode);
        if (level != -1) {
            return level + 1;
        }
        return -1;

    }


    List<TreeNodes> father = new ArrayList<>();

    public boolean isCousins2(TreeNodes root, int x, int y) {
        int xDepth = depth(root, x);
        int yDepth = depth(root, y);

        if (xDepth != yDepth || father.get(0) == father.get(1)) return false;
        return true;

    }

    public int depth(TreeNodes root, int x) {
        if (root == null) {
            return -1;
        }

        if (root.val == x) {
            return 1;
        }

        int left = depth(root.left, x);
        int right = depth(root.right, x);

        int depth = Math.max(left, right);
        if (depth == 1) {
            father.add(root);
        }
        return depth == -1 ? -1 : depth + 1;
    }


    private class Data {

        TreeNodes node;
        TreeNodes parent;
        int depth;

        public Data(TreeNodes node, TreeNodes parent, int depth) {
            this.node = node;
            this.parent = parent;
            this.depth = depth;
        }
    }

    //广度遍历： 设计状态：
    public boolean isCounsins(TreeNodes root, int x, int y) {

        int depthX = 0;
        int dapthY = 0;

        TreeNodes xPar = new TreeNodes();
        TreeNodes yPar = new TreeNodes();

        Queue<TreeOper.Data> queue = new LinkedBlockingDeque<>();

        queue.add(new Data(root, null, 0));
        while (!queue.isEmpty()) {

            Data cur = queue.peek();

            if (cur.node.val == x) {
                depthX = cur.depth;
                xPar = cur.parent;
            }

            if (cur.node.val == y) {
                dapthY = cur.depth;
                yPar = cur.parent;
            }
            if (cur.node.left != null) {
                queue.add(new Data(cur.node.left, cur.node, cur.depth + 1));
            }
            if (cur.node.right != null) {
                queue.add(new Data(cur.node.right, cur.node, cur.depth + 1));
            }
            queue.remove();
        }
        return depthX == dapthY && xPar != yPar;
    }

    /**
     * 到达最近0的距离
     */

    private class ZeroData {
        int x;
        int y;
        int val;
        int legnth;

        public ZeroData(int x, int y, int val, int legnth) {
            this.x = x;
            this.y = y;
            this.val = val;
            this.legnth = legnth;
        }
    }

    public int[][] updateMatrix(int[][] mat) {

        int[][] res = new int[mat.length][mat[0].length];

        Queue<ZeroData> queue = new LinkedBlockingDeque<>();

        //从0,0 开始
        ZeroData zeroData = new ZeroData(0, 0, mat[0][0], 0);
        //设计状态
        queue.add(zeroData);
        //进行广搜
        while (!queue.isEmpty()) {

            ZeroData curNode = queue.peek();
            //当前值 == 0
            if (curNode.val == 0) {
                res[curNode.x][curNode.y] = curNode.legnth;
            }else {
                // 节点++
            }
            //向下走 -- 越界判断
            if (curNode.x + 1 < mat.length) {
                queue.add(new ZeroData(curNode.x + 1, curNode.y, mat[curNode.x + 1][curNode.y], 0));
            }
            //向右走 -- 越界判断
            if (curNode.y + 1 < mat[0].length) {
                queue.add(new ZeroData(curNode.x, curNode.y + 1, mat[curNode.x][curNode.y + 1], 0));
            }
            queue.remove(zeroData);
        }

        return res;
    }


}