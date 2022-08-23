package com.houhong.algorithm.tree;

import org.apache.commons.lang3.StringUtils;

import javax.swing.plaf.nimbus.State;
import java.util.*;
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
            } else {
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


    private class Truedata {

        int xPos;
        int yPos;
        int length;

        public Truedata(int xPos, int yPos, int length) {
            this.xPos = xPos;
            this.yPos = yPos;
            this.length = length;
        }

    }

    public int[][] updateMatrixV2(int[][] mat) {

        int m = mat.length;
        int n = mat[0].length;
        //定义一个方向数组  [(0,-1),()]
        int[][] dir = new int[][]{
                {0, 1}, {1, 0}, {0, -1}, {-1, 0}
        };

        Queue<Truedata> queue = new LinkedBlockingDeque<>();
        int[][] vis = new int[m][n];
        //初始化广搜队列 ---> 将所有的0号节点添加到广搜队列
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                vis[i][j] = -1;
            }
        }
        //遍历
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //不为0 跳过
                if (mat[i][j] != 0) {
                    continue;
                }
                //访问过
                vis[i][j] = 0;
                //添加进入队列
                queue.add(new Truedata(i, j, 0));
            }
        }

        //遍历队列
        while (!queue.isEmpty()) {

            Truedata cur = queue.peek();

            //状态扩展
            for (int k = 0; k < 4; k++) {
                int x = cur.xPos + dir[k][0];
                int y = cur.yPos + dir[k][1];

                // 越界处理
                if (x < 0 || y >= n) continue;
                if (y < 0 || x >= m) continue;
                //是否访问过
                if (vis[x][y] != -1) continue;
                vis[x][y] = cur.length + 1;

                //将第二次层放入
                queue.add(new Truedata(x, y, cur.length + 1));
            }
            queue.remove(cur);


        }

        return vis;
    }


    private class MinLengthData {


        private int x;
        private int y;
        private int length;

        public MinLengthData(int x, int y, int length) {
            this.x = x;
            this.y = y;
            this.length = length;
        }
    }


    public int shortestPathBinaryMatrix(int[][] grid) {

        int goalX = grid.length - 1;
        int goalY = grid[0].length - 1;

        Queue<MinLengthData> queue = new LinkedBlockingDeque<>();


        int[][] dir = new int[][]{
                {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}
        };

        if (grid[0][0] != 0 || grid[goalX][goalY] != 0) {
            return -1;
        }

        int[][] vis = new int[goalX + 1][goalY + 1];
        //初始化
        for (int i = 0; i <= goalX; i++) {
            for (int j = 0; j <= goalY; j++) {
                vis[i][j] = -1;
            }
        }


        queue.add(new MinLengthData(0, 0, 1));
        while (!queue.isEmpty()) {

            MinLengthData curNode = queue.peek();


            for (int i = 0; i < 8; i++) {

                int tempX = curNode.x + dir[i][0];
                int tempY = curNode.y + dir[i][1];

                //越界判断
                if (tempX < 0 || tempX > goalX) continue;
                if (tempY < 0 || tempY > goalY) continue;

                if (grid[tempX][tempY] == 1) continue;
                //是否访问过
                if (vis[tempX][tempY] != -1) continue;

                //状态扩展  1: 扩展的节点刚好是目标节点。结束 还要记录是否走过原来的节点
                vis[tempX][tempY] = 0;

                if (tempX == goalX && tempY == goalY) {
                    return curNode.length + 1;
                }
                //还没到目标点，扩展状态
                queue.add(new MinLengthData(tempX, tempY, curNode.length + 1));
            }

            queue.remove(curNode);
        }


        return -1;
    }


    /**
     *
     **/
    public class LockData {

        String curState;
        int length;

        public LockData(String curState, int length) {
            this.curState = curState;
            this.length = length;
        }
    }


    public int openLock(String[] deadends, String target) {

        if (target == null || target.length() == 0) {
            return -1;
        }

        //边界条件的处理
        Set<String> exitStateSet = new HashSet<>();
        for (int i = 0; i < deadends.length; i++) {
            exitStateSet.add(deadends[i]);
        }
        //表示如果在deadends 里面有 0000 是无论如和也扩展不了的
        if (exitStateSet.contains("0000")) {
            return -1;
        }
        Queue<LockData> queue = new LinkedBlockingDeque<>();
        queue.add(new LockData("0000", 0));
        //初始化
        while (!queue.isEmpty()) {

            LockData curStateNode = queue.peek();

            //判断是否成功
            if (target.equals(curStateNode.curState)) {
                return curStateNode.length;
            }
            //状态扩展 这是比较重要的 -- 这里才是自己发挥的地方。
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 2; y++) {
                    String newStateStr = genNewState(curStateNode.curState, x, y);
                    if (!exitStateSet.contains(newStateStr)) {
                        exitStateSet.add(newStateStr);
                        queue.add(new LockData(newStateStr, curStateNode.length + 1));
                    }
                }
            }
            queue.remove();

        }


        return -1;
    }

    public String genNewState(String curState, int x, int y) {


        String curIndex = curState.charAt(x) + "";
        int curInt = Integer.parseInt(curIndex);

        switch (y) {
            case 0:
                curInt++;
                break;
            case 1:
                curInt--;
                break;
            default:
                break;
        }
        if (curInt > 9) {
            curInt = 0;
        }
        if (curInt < 0) {
            curInt = 9;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(curState);
        sb.replace(x, x + 1, curInt + "");
        return sb.toString();
    }

    public static void main(String[] args) {

        int[][] testcor = new int[3][2];

        for (int i = 0; i < testcor.length; i++) {
            for (int j = 0; j < testcor[0].length; j++) {
                testcor[i][j] = 0;
            }
        }
        TreeOper treeOper = new TreeOper();
        System.out.println(treeOper.movingCount(3, 2, 17));
    }

    public class RobotData {
        int x;
        int y;
        int steps;

        public RobotData(int x, int y, int steps) {
            this.x = x;
            this.y = y;
            this.steps = steps;
        }

        @Override
        public int hashCode() {
            int hashcode = 29 + x + y;
            return hashcode;
        }

        @Override
        public boolean equals(Object obj) {

            if (!(obj instanceof RobotData)) {
                return false;
            }

            RobotData objData = (RobotData) obj;

            return this.x == objData.x && this.y == objData.y;

        }
    }


    public int movingCount(int m, int n, int k) {

        int steps = 0;

        int[][] hasVis = new int[m][n];



        for (int i = 0; i < hasVis.length; i++) {
            for (int j = 0; j < hasVis[0].length; j++) {
                hasVis[i][j] = -1;
            }
        }

        int[][] dir = new int[][]{
                {0, 1}, {1, 0}
        };
        Queue<RobotData> queue = new LinkedBlockingDeque<>();
        queue.add(new RobotData(0, 0, 1));


        if(m ==1 && n == 1){
            return  1;
        }
        while (!queue.isEmpty()) {

            RobotData curNode = queue.peek();



            for (int i = 0; i < dir.length; i++) {

                int tempX = curNode.x + dir[i][0];
                int tempY = curNode.y + dir[i][1];

                //边界判断
                if (tempX < 0 || tempX >= m) continue;
                if (tempY < 0 || tempY >= n) continue;
                //判断是否访问过
                if (hasVis[tempX][tempY] != -1) continue;

                //当前节点访问过
                hasVis[curNode.x][curNode.y] = 0;

                RobotData newData = null;
                //做业务操作
                newData = new RobotData(tempX,tempY,curNode.steps+1);
                //状态扩展
                queue.add(newData);
            }
            if(curNode.x + curNode.y <= k ) steps ++;
            //返回条件
            if (curNode.x == (m -1) && curNode.y == (n-1)) return  steps+1;
            queue.remove(curNode);
        }


        return steps;

    }

}