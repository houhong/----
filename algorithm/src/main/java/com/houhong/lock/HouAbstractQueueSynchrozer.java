package com.houhong.lock;

import sun.misc.Unsafe;

import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.LockSupport;

/**
 * @program: algorithm-work
 * @description: 同步器
 * @author: houhong
 * @create: 2022-09-15 16:23
 **/
public abstract class HouAbstractQueueSynchrozer extends AbstractOwnableSynchronizer implements java.io.Serializable {

    static final class Node {

        /*
         *   代表当前节点所持有的线程可被唤醒。
         * */
        static final int SIGNAL = -1;
        /**
         * 需要进行取消
         **/
        static final int CANCELLED = 1;
        //独占模式
        static final Node EXCLUSIVE = null;
        volatile Thread thread;

        volatile Node pre;
        volatile Node next;

        Node nextWaiter;
        volatile int waitStatus;

        Node() {
        }

        Node(Thread thread, Node mode) {
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus) {
            this.waitStatus = waitStatus;
            this.thread = thread;
        }


        final Node predecessor() throws NullPointerException {
            Node p = pre;
            if (p == null) {
                throw new NullPointerException();
            } else {
                return p;
            }
        }

    }

    private volatile Node head;
    private volatile Node tail;


    public final void acquire(int arg) {
        if (!tryAcquire(arg)
                && acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) {

        }
    }

    public final boolean acquireQueued(Node node, int arg) {

        boolean failed = false;
        try {
            boolean interrupted = false;
            for (; ; ) {
                Node predecessor = node.predecessor();
                if (predecessor == head && tryAcquire(arg)) {
                    setHead(node);
                    // help GC
                    predecessor.next = null;
                    failed = false;
                    return interrupted;
                }
                // 在这里 没有获取到锁的情况。 需要park 线程
                if (shouldParkAfterFailedAcquire(predecessor, node) &&
                        parkAndCheckInterrupt()) {
                    interrupted = true;
                }
            }
        } finally {
            if (failed) {
                cancelAcquire(node);
            }
        }


    }


    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {

        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL) {
            return true;
        }
        if (ws > 0) {
            do {
                node.pre = pred = pred.pre;
            } while (pred.waitStatus > 0);
        } else {
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }

    private void cancelAcquire(Node node) {

        if (node == null) {
            return;
        }
        Node pred = node.pre;
        node.thread = null;
        while (pred.waitStatus > 0) {
            node.pre = pred = pred.pre;
        }
        Node predNext = pred.next;
        node.waitStatus = Node.CANCELLED;

        if (node == tail && compareAndSetTail(node, pred)) {
            compareAndSetNext(pred, predNext, null);
        } else {
            int ws = 0;

            if (pred != head &&
                    (ws = pred.waitStatus) == Node.SIGNAL
                    || (ws < 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) {

            }
        }

    }


    private void unparkSuccessor(Node node) {
        int ws = node.waitStatus;
        if (ws < 0) {
            compareAndSetWaitStatus(node, ws, 0);
        }

        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            s = null;
            for (Node t = tail; t != null && t != node; t = t.pre) {
                if (t.waitStatus <= 0) {
                    s = t;
                }
            }
        }

        if(s != null){
            LockSupport.unpark(s.thread);
        }

    }

    private boolean parkAndCheckInterrupt() {

        LockSupport.park(this);
        return Thread.interrupted();
    }


    public abstract boolean tryAcquire(int arg);

    private Node addWaiter(Node mode) {

        Node node = new Node(Thread.currentThread(), mode);
        Node pred = tail;
        if (pred != null) {
            node.pre = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);

        return node;
    }

    private Node enq(Node node) {

        for (; ; ) {
            Node t = tail;
            if (t == null) {
                if (compareAndSetHead(node)) {
                    tail = head;
                }
            } else {
                node.pre = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }


    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.pre = null;
    }


    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long headOffset;
    private static final long tailOffset;
    private static final long statusOffset;
    private static final long nextWaiterOffset;

    static {
        try {
            headOffset = unsafe.objectFieldOffset(HouAbstractQueueSynchrozer.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset(HouAbstractQueueSynchrozer.class.getDeclaredField("tail"));
            statusOffset = unsafe.objectFieldOffset(HouAbstractQueueSynchrozer.class.getDeclaredField("waitStatus"));
            nextWaiterOffset = unsafe.objectFieldOffset(HouAbstractQueueSynchrozer.class.getField("nextWaiter"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    private final boolean compareAndSetState(Node except, Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, except, update);
    }

    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    private final boolean compareAndSetTail(Node except, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, except, update);
    }

    private final static boolean compareAndSetWaitStatus(Node node, int except, int update) {
        return unsafe.compareAndSwapInt(node, statusOffset, except, update);
    }

    private final static boolean compareAndSetNext(Node node, Node expect, Node update) {
        return unsafe.compareAndSwapObject(node, nextWaiterOffset, expect, update);
    }

}