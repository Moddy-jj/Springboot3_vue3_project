package com.example;

import org.junit.jupiter.api.Test;
public class ThreadLocalTest {
    // @Test
    // public void testThreadLocalSetAndGet() {
    //     ThreadLocal t1=new ThreadLocal();
        
    //     new Thread(()->{
    //         t1.set("消炎");
    //         System.out.println(Thread.currentThread().getName()+": "+t1.get());
    //         System.out.println(Thread.currentThread().getName()+": "+t1.get());
    //         System.out.println(Thread.currentThread().getName()+": "+t1.get());
    //     },"蓝").start();
    //     new Thread(()->{
    //         t1.set("药老");
    //         System.out.println(Thread.currentThread().getName()+": "+t1.get());
    //         System.out.println(Thread.currentThread().getName()+": "+t1.get());
    //         System.out.println(Thread.currentThread().getName()+": "+t1.get());
    //     },"绿").start();
    // }
}
