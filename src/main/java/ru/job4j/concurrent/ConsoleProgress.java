package ru.job4j.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        List<String> list = new ArrayList<>(List.of("-", "\\", "|", "/"));
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (String i : list) {
                    System.out.print("\r load: " + i);
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
