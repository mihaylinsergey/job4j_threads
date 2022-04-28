package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            wait();
        }
        if (queue.size() == 0) {
            notifyAll();
        }
        queue.add(value);
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        if (queue.size() == limit) {
            notifyAll();
        }
        return queue.poll();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue queue = new SimpleBlockingQueue(10);
        Thread producer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        for (int i = 0; i < 11; i++) {
                            queue.offer(i);
                            System.out.println(queue);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                },
                "Producer"
        );
        Thread consumer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        for (int i = 0; i < 11; i++) {
                            System.out.println(queue.poll());
                        }

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                },
                "Consumer"
        );
        producer.start();
        consumer.start();
    }
}