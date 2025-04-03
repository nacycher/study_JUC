package com.study.core.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 创建资源类，定变量和操作资源的方法
class Number {
    private int number = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void addNumber() throws InterruptedException {
        lock.lock();
        while (number != 0) {
            condition.await();
        }
        try {
            ++number;
            System.out.println(Thread.currentThread().getName() + " : " + number);
        condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public void reduceNumber() throws InterruptedException {
        lock.lock();

        while (number != 1) {
            condition.await();
        }

        try {
            --number;
            System.out.println(Thread.currentThread().getName() + " : " + number);
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

}

public class DemoLock01 {
    public static void main(String[] args) {
        Number number = new Number();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    number.addNumber();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "A").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    number.reduceNumber();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "B").start();


    }
}
