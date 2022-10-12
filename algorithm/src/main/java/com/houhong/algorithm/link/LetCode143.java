package com.houhong.algorithm.link;

/**
 * @program: algorithm-work
 * @description: 重排链表
 * @author: houhong
 * @create: 2022-10-11 20:46
 **/
public class LetCode143 {

    public static void main(String[] args) {

        ListNode f1 = new ListNode(1);
        ListNode f2 = new ListNode(2);
        ListNode f3 = new ListNode(3);
        ListNode f4 = new ListNode(4);
        ListNode f5 = new ListNode(5);
        f1.next = f2;
        f2.next = f3;
        f3.next = f4;
        f4.next = f5;

        ListNode listNode = reverseNode(f1);

        System.out.println(listNode);

    }

    public static void reorderList(ListNode head) {

        //中间节点
        ListNode middleNode = middleNode(head);
        //切分节点
        ListNode leftHead = head;
        ListNode rightHead = middleNode.next;
        //断开
        middleNode.next = null;
        //反转右边的链表
        rightHead = reverseNode(rightHead);
        //合并 将这两个链表进行合并操作，即进行【交错拼接】
        // 遍历链表合并
        while (leftHead != null && rightHead != null) {

            // 5、先记录左区域、右区域【接下来将有访问的两个节点】
            ListNode leftHeadNext = leftHead.next;

            ListNode rightHeadNext = rightHead.next;

            // 6、左边连接右边的开头
            leftHead.next = rightHead;

            // 7、leftHead 已经处理好，移动到下一个节点，即刚刚记录好的节点
            leftHead = leftHeadNext;

            // 8、右边连接左边的开头
            rightHead.next = leftHead;

            // 9、rightHead 已经处理好，移动到下一个节点，即刚刚记录好的节点
            rightHead = rightHeadNext;


        }


    }


    /**
     * @param root
     * @return {@link ListNode}
     * @Author houhong
     * @Description //TODO 用快慢指针获取中间节点
     * @Date 8:50 下午 2022/10/11
     **/
    public static ListNode middleNode(ListNode root) {

        if (root == null || root.next == null) {
            return root;
        }

        ListNode slowPtr = root, fastPtr = root;

        while (fastPtr != null && fastPtr.next != null) {

            slowPtr = slowPtr.next;
            fastPtr = fastPtr.next.next;
        }

        return slowPtr;
    }


    /**
     * 设计递归反转链表
     **/
    public static ListNode reverseNode(ListNode head) {


        //终止条件
        if (head == null || head.next == null) {
            return head;
        }

        ListNode cur = reverseNode(head.next);
        /**
         *  进行反转
         **/
        head.next.next = head;

        head.next = null;
        return cur;
    }


    static class ListNode {

        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
