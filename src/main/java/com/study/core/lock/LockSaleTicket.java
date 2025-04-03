package com.study.core.lock;

/*
 * 使用lock实现售票
 * */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 1.创建资源类，创建变量和需要访问的资源方法
class Ticket {
    public int number = 30;
    private final Lock saleLock = new ReentrantLock();

    public void saleTicket() {
        saleLock.lock();
        if (number > 0) {
            try {
                System.out.println(Thread.currentThread().getName() + " sale ticket:" + (number--) + " remaining:" + number);
            }
            // lock需要手动释放锁，使用finally修饰，即使代码出现异常也会释放锁，避免死锁的可能性
            finally {
                saleLock.unlock();
            }
        }
    }
}


public class LockSaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.saleTicket();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.saleTicket();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.saleTicket();
            }
        }, "C").start();

    }
}
