package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(this.url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("10Mb.dat")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long start = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (downloadData >= speed) {
                    long finish = System.nanoTime();
                    long delta = finish - start;
                    if (delta < 1000000000) {
                        Thread.sleep((1000 - delta / 1000000));
                        downloadData = 0;
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        if (url.isEmpty() || speed <= 0) {
            throw new IllegalArgumentException("Argument is not correct");
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}