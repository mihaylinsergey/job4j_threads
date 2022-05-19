package ru.job4j.pool;

public class Task implements Runnable {

    private int number;

    public Task(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        System.out.println(number);
    }
}
