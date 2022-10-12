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



/**
 * Callable表示一类带有返回值的任务，这类任务在计算过程中可能抛出异常
 *
 * Callable类任务通常由【任务执行器】Executor来执行
 * 它可以与Runnable与Future协作
 *
 * 该接口已函数化：
 * Callable callable = new Callable<Result>() {
 *     @Override
 *     public Result call() throws Exception {
 *         System.out.println("Callable");
 *         return new Result();
 *     }
 * };
 * 可以简写为：
 * Callable callable = (Callable<Result>) () -> {
 *     System.out.println("Callable");
 *     return new Result();
 * };
 */
@FunctionalInterface
public interface Callable<V> {

    // 任务执行入口
    V call() throws Exception;
}
