package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private static final int LIMIT = 10;
    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelSearch(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from + 1 <= LIMIT) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(value)) {
                    return i;
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, value, from, mid);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, value, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return left == -1 ? right : left;
    }

    public static <V> int search(V[] array, V value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, value, 0, array.length - 1));
    }
}
