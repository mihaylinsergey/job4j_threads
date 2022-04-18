package ru.job4j.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        char[] symbols = {'-', '\\', '|', '/'};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (char symbol : symbols) {
                    System.out.print("\r load: " + symbol);
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ConsoleProgress());
        thread.start();
        Thread.sleep(10000);
        thread.interrupt();
    }
}
