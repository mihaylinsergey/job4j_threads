package ru.job4j.thread;

import java.io.BufferedInputStream;
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
        try (BufferedInputStream in = new BufferedInputStream(new URL(this.url).openStream())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead != -1)) {
                long start = System.nanoTime();
                bytesRead = in.read(dataBuffer, 0, 1024);
                long finish = System.nanoTime();
                System.out.println(bytesRead);
                long delta = finish - start;
                long currentSpeed = finish - start;
                if (currentSpeed < this.speed) {
                    Thread.sleep((currentSpeed - (long) this.speed));
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}