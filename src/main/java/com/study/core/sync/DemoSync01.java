package com.study.core.sync;

// 创建资源类，定义变量和操作资源类方法
class Number {
    private int number = 0;

    public synchronized void addNumer() throws InterruptedException {
        // 1.线程wait条件
//            使用while避免虚假唤醒
//            if (number != 0) {
        while (number != 0) {
            this.wait();
        }
        // 2.操作资源、
        ++number;
        System.out.println(Thread.currentThread().getName() + " : " + number);
        // 3.唤醒其他线程
        this.notifyAll();
    }

    public synchronized void reduceNumer() throws InterruptedException {
        // 1.线程wait条件
//            if (number != 1) {
        while (number != 1) {
            this.wait();
        }
        // 2.操作资源、
        --number;
        System.out.println(Thread.currentThread().getName() + " : " + number);
        // 3.唤醒其他线程
        this.notifyAll();
    }
}

public class DemoSync01 {
    public static void main(String[] args) {
        Number number = new Number();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    number.addNumer();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "A").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    number.addNumer();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "B").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    number.reduceNumer();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "C").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    number.reduceNumer();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "D").start();
    }
}
