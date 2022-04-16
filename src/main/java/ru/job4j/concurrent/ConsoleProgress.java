package ru.job4j.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        List<String> list = new ArrayList<>(List.of("-", "\\", "|", "/"));
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < 4; i++) {
                    System.out.print("\r load: " + list.get(i));
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ConsoleProgress());
        thread.start();
        Thread.sleep(10000);
        thread.interrupt();
    }
}
