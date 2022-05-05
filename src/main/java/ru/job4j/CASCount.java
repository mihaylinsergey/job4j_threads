package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int oldCount;
        int newCount;
        do {
            oldCount = count.get();
            newCount = oldCount++;
            } while (!count.compareAndSet(oldCount, newCount));
        }

    public int get() {
        return count.get();
    }
}