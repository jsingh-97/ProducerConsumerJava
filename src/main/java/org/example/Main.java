package org.example;

import java.util.Deque;
import java.util.LinkedList;

public class Main {
    static Deque<Integer> dq=new LinkedList<>();
    static int capacity=2;

    static class Producer implements Runnable {
        @Override
        public void run() {
            System.out.println("Producer thread started!!!");
            while(true) {
                synchronized (dq) {
                    if(dq.size()==capacity){ //wait if queue is full
                        try {
                            dq.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    int val=(int) (Math.random()*10);
                    dq.offerLast(val);
                    System.out.println("Produced:"+val);
                    dq.notify();
                }
            }
        }
    }
    static class Consumer implements Runnable{
        @Override
        public void run(){
            System.out.println("Consumer thread started!!!");
            while(true){
                synchronized (dq){
                    if(dq.size()==0){
                        try {
                            dq.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("Consumed:"+dq.pollFirst());
                    dq.notify();
                }
            }

        }
    }
    public static void main(String[] args) {

        System.out.println("This is a producer consumer Java Problem");
        Producer producer=new Producer();
        Consumer consumer=new Consumer();
        Thread producerThread=new Thread(producer);
        Thread consumerThread=new Thread(consumer);
        producerThread.start();
        consumerThread.start();
    }
}