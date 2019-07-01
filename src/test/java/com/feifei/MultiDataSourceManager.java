package com.qding.bigdata;


import java.io.IOException;


/**
 * @author yanpf
 * @date 2019/5/29 16:40
 * @description
 */
public class MultiDataSourceManager {


    private static Object a = new Object();
    private static Object b = a;


    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            synchronized (a){
                for(int i=0; i<5; i++){
                    System.out.println("thread a:" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        new Thread(() -> {
            synchronized (b){
                for(int i=0; i<5; i++){
                    System.out.println("thread b:" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        System.in.read();
    }
}
