/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package com.houhong.lock.pool;


import java.security.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.ref.Reference.reachabilityFence;


// 【任务执行框架】的工厂，该类负责生成：【任务执行框架】对象、Callable类型的任务对象、线程工厂对象
public class Executors {

    /**
     * Cannot instantiate.
     */
    private Executors() {
    }



    /*▼ 【工作池】 ████████████████████████████████████████████████████████████████████████████████┓ */


    // 并行度与处理器数量相同的【工作池】
    public static java.util.concurrent.ExecutorService newWorkStealingPool() {
        return new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
    }

    // 并行度为parallelism的【工作池】
    public static java.util.concurrent.ExecutorService newWorkStealingPool(int parallelism) {
        return new ForkJoinPool(parallelism, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
    }

    /*▲ 【工作池】 ████████████████████████████████████████████████████████████████████████████████┛ */



    /*▼ 【任务执行框架代理】 ████████████████████████████████████████████████████████████████████████████████┓ */


    // 不可配置
    public static java.util.concurrent.ExecutorService unconfigurableExecutorService(java.util.concurrent.ExecutorService executor) {
        if (executor == null) {
            throw new NullPointerException();
        }

        return new DelegatedExecutorService(executor);
    }

    /*▲ 【任务执行框架代理】 ████████████████████████████████████████████████████████████████████████████████┛ */



    /*▼ 【线程池】 ████████████████████████████████████████████████████████████████████████████████┓ */


    /**
     * 【缓冲线程池】
     * <p>
     * 提交新任务之后，会创建一个新线程执行它，该新线程在空闲期的存活时长为60秒。
     * 换句话说，每个线程在空闲60秒之后就被销毁了，所以适合做缓冲(不是缓存)。
     * <p>
     * 配置：
     * - 阻塞队列   : SynchronousQueue
     * -【核心阙值】: 0
     * -【最大阙值】: 无限
     */
    public static java.util.concurrent.ExecutorService newCachedThreadPool() {
        return new java.util.concurrent.ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }


    /**
     * 【缓冲线程池】，允许自行指定线程工厂
     * <p>
     * 提交新任务之后，会创建一个新线程执行它，该新线程在空闲期的存活时长为60秒。
     * 换句话说，每个线程在空闲60秒之后就被销毁了，所以适合做缓冲(不是缓存)。
     * <p>
     * 配置：
     * - 阻塞队列   : SynchronousQueue
     * -【核心阙值】: 0
     * -【最大阙值】: 无限
     */
    public static java.util.concurrent.ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        return new java.util.concurrent.ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
    }


    /**
     * 【固定容量线程池】
     * <p>
     * 线程池中常驻线程数量为nThreads
     * <p>
     * 配置：
     * - 阻塞队列   : LinkedBlockingQueue
     * -【核心阙值】: nThreads
     * -【最大阙值】: nThreads
     */
    public static java.util.concurrent.ExecutorService newFixedThreadPool(int nThreads) {
        return new java.util.concurrent.ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }


    /**
     * 【固定容量线程池】，允许自行指定线程工厂
     * <p>
     * 线程池中常驻线程数量为nThreads
     * <p>
     * 配置：
     * - 阻塞队列   : LinkedBlockingQueue
     * -【核心阙值】: nThreads
     * -【最大阙值】: nThreads
     */
    public static java.util.concurrent.ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        return new java.util.concurrent.ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
    }


    /**
     * 【定时任务线程池】
     * <p>
     * 用于执行一次性或周期性的定时任务
     * <p>
     * 配置：
     * - 阻塞队列   : DelayedWorkQueue
     * -【核心阙值】: corePoolSize
     * -【最大阙值】: 无限
     */
    public static java.util.concurrent.ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new java.util.concurrent.ScheduledThreadPoolExecutor(corePoolSize);
    }


    /*
     *【定时任务线程池】，允许自行指定线程工厂
     *
     * 用于执行一次性或周期性的定时任务
     *
     * 配置：
     * - 阻塞队列   : DelayedWorkQueue
     * -【核心阙值】: corePoolSize
     * -【最大阙值】: 无限
     */
    public static java.util.concurrent.ScheduledExecutorService newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        return new java.util.concurrent.ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
    }


    /*
     *【定时任务线程池代理】
     *
     * 用于执行一次性或周期性的定时任务，线程池中只有一个常驻线程。
     *
     * 配置：
     * - 阻塞队列   : DelayedWorkQueue
     * -【核心阙值】: 1
     * -【最大阙值】: 无限
     */
    public static java.util.concurrent.ScheduledExecutorService newSingleThreadScheduledExecutor() {
        return new DelegatedScheduledExecutorService(new java.util.concurrent.ScheduledThreadPoolExecutor(1));
    }


    /**
     * 【定时任务线程池代理】，允许自行指定线程工厂
     * <p>
     * 用于执行一次性或周期性的定时任务，线程池中只有一个常驻线程。
     * <p>
     * 配置：
     * - 阻塞队列   : DelayedWorkQueue
     * -【核心阙值】: 1
     * -【最大阙值】: 无限
     */
    public static java.util.concurrent.ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
        return new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1, threadFactory));
    }


    /**
     * 【定时任务线程池代理】
     * <p>
     * 不可自定义配置，只是对指定的【定时任务执行框架】的简单代理
     */
    public static java.util.concurrent.ScheduledExecutorService unconfigurableScheduledExecutorService(java.util.concurrent.ScheduledExecutorService executor) {
        if (executor == null) {
            throw new NullPointerException();
        }

        return new DelegatedScheduledExecutorService(executor);
    }


    /**
     * 【Finalizable线程池】
     * <p>
     * 顺序执行普通任务，线程池中最多只有一个线程。
     * <p>
     * 配置：
     * - 阻塞队列   : LinkedBlockingQueue
     * -【核心阙值】: 1
     * -【最大阙值】: 1
     */
    public static java.util.concurrent.ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService(new java.util.concurrent.ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));
    }


    /**
     * 【Finalizable线程池】，允许自行指定线程工厂
     * <p>
     * 顺序执行普通任务，线程池中最多只有一个线程。
     * <p>
     * 配置：
     * - 阻塞队列   : LinkedBlockingQueue
     * -【核心阙值】: 1
     * -【最大阙值】: 1
     */
    public static java.util.concurrent.ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        return new FinalizableDelegatedExecutorService(new java.util.concurrent.ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory));
    }

    /*▲ 【线程池】 ████████████████████████████████████████████████████████████████████████████████┛ */



    /*▼ 任务对象 ████████████████████████████████████████████████████████████████████████████████┓ */

    /**
     * Returns a {@link java.util.concurrent.Callable} object that, when
     * called, runs the given task and returns {@code null}.
     *
     * @param task the task to run
     * @return a callable object
     * @throws NullPointerException if task null
     */
    // 包装Runnable任务，无返回值
    public static java.util.concurrent.Callable<Object> callable(Runnable task) {
        if (task == null) {
            throw new NullPointerException();
        }
        return new RunnableAdapter<Object>(task, null);
    }

    /**
     * Returns a {@link java.util.concurrent.Callable} object that, when
     * called, runs the given task and returns the given result.  This
     * can be useful when applying methods requiring a
     * {@code Callable} to an otherwise resultless action.
     *
     * @param task   the task to run
     * @param result the result to return
     * @param <T>    the type of the result
     * @return a callable object
     * @throws NullPointerException if task null
     */
    // 包装Runnable任务，有返回值
    public static <T> java.util.concurrent.Callable<T> callable(Runnable task, T result) {
        if (task == null) {
            throw new NullPointerException();
        }
        return new RunnableAdapter<T>(task, result);
    }

    /**
     * Returns a {@link java.util.concurrent.Callable} object that will, when called,
     * execute the given {@code callable} under the current access
     * control context. This method should normally be invoked within
     * an {@link AccessController#doPrivileged AccessController.doPrivileged}
     * action to create callables that will, if possible, execute
     * under the selected permission settings holding within that
     * action; or if not possible, throw an associated {@link
     * AccessControlException}.
     *
     * @param callable the underlying task
     * @param <T>      the type of the callable's result
     * @return a callable object
     * @throws NullPointerException if callable null
     */
    // 包装Callable任务，使用上下文访问权限控制器
    public static <T> java.util.concurrent.Callable<T> privilegedCallable(java.util.concurrent.Callable<T> callable) {
        if (callable == null) {
            throw new NullPointerException();
        }
        return new PrivilegedCallable<T>(callable);
    }

    /**
     * Returns a {@link java.util.concurrent.Callable} object that will, when called,
     * execute the given {@code callable} under the current access
     * control context, with the current context class loader as the
     * context class loader. This method should normally be invoked
     * within an
     * {@link AccessController#doPrivileged AccessController.doPrivileged}
     * action to create callables that will, if possible, execute
     * under the selected permission settings holding within that
     * action; or if not possible, throw an associated {@link
     * AccessControlException}.
     *
     * @param callable the underlying task
     * @param <T>      the type of the callable's result
     * @return a callable object
     * @throws NullPointerException   if callable null
     * @throws AccessControlException if the current access control
     *                                context does not have permission to both set and get context
     *                                class loader
     */
    // 包装Callable任务，使用上下文访问权限控制器和当前线程的类加载器
    public static <T> java.util.concurrent.Callable<T> privilegedCallableUsingCurrentClassLoader(java.util.concurrent.Callable<T> callable) {
        if (callable == null) {
            throw new NullPointerException();
        }
        return new PrivilegedCallableUsingCurrentClassLoader<T>(callable);
    }

    /**
     * Returns a {@link java.util.concurrent.Callable} object that, when
     * called, runs the given privileged action and returns its result.
     *
     * @param action the privileged action to run
     * @return a callable object
     * @throws NullPointerException if action null
     */
    // 包装PrivilegedAction任务
    public static java.util.concurrent.Callable<Object> callable(final PrivilegedAction<?> action) {
        if (action == null) {
            throw new NullPointerException();
        }

        return new java.util.concurrent.Callable<Object>() {
            public Object call() {
                return action.run();
            }
        };
    }

    /**
     * Returns a {@link java.util.concurrent.Callable} object that, when
     * called, runs the given privileged exception action and returns
     * its result.
     *
     * @param action the privileged exception action to run
     * @return a callable object
     * @throws NullPointerException if action null
     */
    // 包装PrivilegedExceptionAction任务
    public static java.util.concurrent.Callable<Object> callable(final PrivilegedExceptionAction<?> action) {
        if (action == null) {
            throw new NullPointerException();
        }

        return new java.util.concurrent.Callable<Object>() {
            public Object call() throws Exception {
                return action.run();
            }
        };
    }

    /*▲ 任务对象 ████████████████████████████████████████████████████████████████████████████████┛ */



    /*▼ 线程工厂 ████████████████████████████████████████████████████████████████████████████████┓ */

    /**
     * Returns a default thread factory used to create new threads.
     * This factory creates all new threads used by an Executor in the
     * same {@link ThreadGroup}. If there is a {@link
     * SecurityManager}, it uses the group of {@link
     * System#getSecurityManager}, else the group of the thread
     * invoking this {@code defaultThreadFactory} method. Each new
     * thread is created as a non-daemon thread with priority set to
     * the smaller of {@code Thread.NORM_PRIORITY} and the maximum
     * priority permitted in the thread group.  New threads have names
     * accessible via {@link Thread#getName} of
     * <em>pool-N-thread-M</em>, where <em>N</em> is the sequence
     * number of this factory, and <em>M</em> is the sequence number
     * of the thread created by this factory.
     *
     * @return a thread factory
     */
    // 默认线程工厂，创建默认线程优先级的非守护线程
    public static ThreadFactory defaultThreadFactory() {
        return new DefaultThreadFactory();
    }

    /**
     * Returns a thread factory used to create new threads that
     * have the same permissions as the current thread.
     * This factory creates threads with the same settings as {@link
     * Executors#defaultThreadFactory}, additionally setting the
     * AccessControlContext and contextClassLoader of new threads to
     * be the same as the thread invoking this
     * {@code privilegedThreadFactory} method.  A new
     * {@code privilegedThreadFactory} can be created within an
     * {@link AccessController#doPrivileged AccessController.doPrivileged}
     * action setting the current thread's access control context to
     * create threads with the selected permission settings holding
     * within that action.
     *
     * <p>Note that while tasks running within such threads will have
     * the same access control and class loader settings as the
     * current thread, they need not have the same {@link
     * ThreadLocal} or {@link
     * InheritableThreadLocal} values. If necessary,
     * particular values of thread locals can be set or reset before
     * any task runs in {@link java.util.concurrent.ThreadPoolExecutor} subclasses using
     * {@link ThreadPoolExecutor#beforeExecute(Thread, Runnable)}.
     * Also, if it is necessary to initialize worker threads to have
     * the same InheritableThreadLocal settings as some other
     * designated thread, you can create a custom ThreadFactory in
     * which that thread waits for and services requests to create
     * others that will inherit its values.
     *
     * @return a thread factory
     * @throws AccessControlException if the current access control
     *                                context does not have permission to both get and set context
     *                                class loader
     */
    // 特权线程工厂
    public static ThreadFactory privilegedThreadFactory() {
        return new PrivilegedThreadFactory();
    }

    /*▲ 线程工厂 ████████████████████████████████████████████████████████████████████████████████┛ */






    /*
     * 以下定义了各种类型的：
     *
     * - 任务执行框架
     * - 任务
     * - 线程工厂
     */

    /**
     * A wrapper class that exposes only the ExecutorService methods of an ExecutorService implementation.
     */
    // 【任务执行框架代理】，限制只能执行ExecutorService接口内的方法
    private static class DelegatedExecutorService implements java.util.concurrent.ExecutorService {
        private final java.util.concurrent.ExecutorService e;

        DelegatedExecutorService(java.util.concurrent.ExecutorService executor) {
            e = executor;
        }

        public void execute(Runnable command) {
            try {
                e.execute(command);
            } finally {
                reachabilityFence(this);
            }
        }

        public void shutdown() {
            e.shutdown();
        }

        public List<Runnable> shutdownNow() {
            try {
                return e.shutdownNow();
            } finally {
                reachabilityFence(this);
            }
        }

        public boolean isShutdown() {
            try {
                return e.isShutdown();
            } finally {
                reachabilityFence(this);
            }
        }

        public boolean isTerminated() {
            try {
                return e.isTerminated();
            } finally {
                reachabilityFence(this);
            }
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            try {
                return e.awaitTermination(timeout, unit);
            } finally {
                reachabilityFence(this);
            }
        }

        public java.util.concurrent.Future<?> submit(Runnable task) {
            try {
                return e.submit(task);
            } finally {
                reachabilityFence(this);
            }
        }

        public <T> java.util.concurrent.Future<T> submit(java.util.concurrent.Callable<T> task) {
            try {
                return e.submit(task);
            } finally {
                reachabilityFence(this);
            }
        }

        public <T> java.util.concurrent.Future<T> submit(Runnable task, T result) {
            try {
                return e.submit(task, result);
            } finally {
                reachabilityFence(this);
            }
        }

        public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends java.util.concurrent.Callable<T>> tasks) throws InterruptedException {
            try {
                return e.invokeAll(tasks);
            } finally {
                reachabilityFence(this);
            }
        }

        public <T> List<Future<T>> invokeAll(Collection<? extends java.util.concurrent.Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            try {
                return e.invokeAll(tasks, timeout, unit);
            } finally {
                reachabilityFence(this);
            }
        }

        public <T> T invokeAny(Collection<? extends java.util.concurrent.Callable<T>> tasks) throws InterruptedException, java.util.concurrent.ExecutionException {
            try {
                return e.invokeAny(tasks);
            } finally {
                reachabilityFence(this);
            }
        }

        public <T> T invokeAny(Collection<? extends java.util.concurrent.Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            try {
                return e.invokeAny(tasks, timeout, unit);
            } finally {
                reachabilityFence(this);
            }
        }
    }

    /**
     * A wrapper class that exposes only the ScheduledExecutorService
     * methods of a ScheduledExecutorService implementation.
     */
    // 【定时任务执行框架代理】，限制只能执行ExecutorService接口与ScheduledExecutorService接口内的方法
    private static class DelegatedScheduledExecutorService extends DelegatedExecutorService implements java.util.concurrent.ScheduledExecutorService {
        private final java.util.concurrent.ScheduledExecutorService e;

        DelegatedScheduledExecutorService(ScheduledExecutorService executor) {
            super(executor);
            e = executor;
        }

        public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            return e.schedule(command, delay, unit);
        }

        public <V> ScheduledFuture<V> schedule(java.util.concurrent.Callable<V> callable, long delay, TimeUnit unit) {
            return e.schedule(callable, delay, unit);
        }

        public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            return e.scheduleAtFixedRate(command, initialDelay, period, unit);
        }

        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            return e.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }
    }

    // 【Finalizable任务执行框架代理】
    private static class FinalizableDelegatedExecutorService extends DelegatedExecutorService {
        FinalizableDelegatedExecutorService(ExecutorService executor) {
            super(executor);
        }

        @SuppressWarnings("deprecation")
        protected void finalize() {
            super.shutdown();
        }
    }


    /**
     * A callable that runs given task and returns given result.
     */
    // 包装Runnable任务
    private static final class RunnableAdapter<T> implements java.util.concurrent.Callable<T> {
        private final Runnable task;
        private final T result;

        RunnableAdapter(Runnable task, T result) {
            this.task = task;
            this.result = result;
        }

        public T call() {
            task.run();
            return result;
        }

        public String toString() {
            return super.toString() + "[Wrapped task = " + task + "]";
        }
    }

    /**
     * A callable that runs under established access control settings.
     */
    // 包装Callable任务，使用上下文访问权限控制器
    private static final class PrivilegedCallable<T> implements java.util.concurrent.Callable<T> {
        final java.util.concurrent.Callable<T> task;
        final AccessControlContext acc;

        PrivilegedCallable(java.util.concurrent.Callable<T> task) {
            this.task = task;
            this.acc = AccessController.getContext();
        }

        public T call() throws Exception {
            try {
                return AccessController.doPrivileged(new PrivilegedExceptionAction<T>() {
                    public T run() throws Exception {
                        return task.call();
                    }
                }, acc);
            } catch (PrivilegedActionException e) {
                throw e.getException();
            }
        }

        public String toString() {
            return super.toString() + "[Wrapped task = " + task + "]";
        }
    }

    /**
     * A callable that runs under established access control settings and
     * current ClassLoader.
     */
    // 包装Callable任务，使用上下文访问权限控制器和当前线程的类加载器
    private static final class PrivilegedCallableUsingCurrentClassLoader<T> implements java.util.concurrent.Callable<T> {
        final java.util.concurrent.Callable<T> task;
        final AccessControlContext acc;
        final ClassLoader ccl;

        PrivilegedCallableUsingCurrentClassLoader(Callable<T> task) {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                // Calls to getContextClassLoader from this class
                // never trigger a security check, but we check
                // whether our callers have this permission anyways.
                sm.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);

                // Whether setContextClassLoader turns out to be necessary
                // or not, we fail fast if permission is not available.
                sm.checkPermission(new RuntimePermission("setContextClassLoader"));
            }
            this.task = task;
            this.acc = AccessController.getContext();
            this.ccl = Thread.currentThread().getContextClassLoader();
        }

        public T call() throws Exception {
            try {
                return AccessController.doPrivileged(new PrivilegedExceptionAction<T>() {
                    public T run() throws Exception {
                        Thread t = Thread.currentThread();
                        ClassLoader cl = t.getContextClassLoader();
                        if (ccl == cl) {
                            return task.call();
                        } else {
                            t.setContextClassLoader(ccl);
                            try {
                                return task.call();
                            } finally {
                                t.setContextClassLoader(cl);
                            }
                        }
                    }
                }, acc);
            } catch (PrivilegedActionException e) {
                throw e.getException();
            }
        }

        public String toString() {
            return super.toString() + "[Wrapped task = " + task + "]";
        }
    }


    /**
     * The default thread factory.
     */
    // 默认线程工厂，创建默认线程优先级的非守护线程
    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final ThreadGroup group;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }

            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }

            return t;
        }
    }

    /**
     * Thread factory capturing access control context and class loader.
     */
    // 特权线程工厂
    private static class PrivilegedThreadFactory extends DefaultThreadFactory {
        final AccessControlContext acc;
        final ClassLoader ccl;

        PrivilegedThreadFactory() {
            super();

            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                /*
                 * Calls to getContextClassLoader from this class never trigger a security check,
                 * but we check whether our callers have this permission anyways.
                 */
                sm.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);

                // Fail fast
                sm.checkPermission(new RuntimePermission("setContextClassLoader"));
            }

            this.acc = AccessController.getContext();
            this.ccl = Thread.currentThread().getContextClassLoader();
        }

        @Override
        public Thread newThread(final Runnable r) {
            return super.newThread(new Runnable() {
                public void run() {
                    AccessController.doPrivileged(new PrivilegedAction<>() {
                        public Void run() {
                            Thread.currentThread().setContextClassLoader(ccl);
                            r.run();
                            return null;
                        }
                    }, acc);
                }
            });
        }
    }
}
