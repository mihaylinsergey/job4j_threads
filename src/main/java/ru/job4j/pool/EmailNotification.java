package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String subject = "Notification %s to email %s";
        String body = "Add a new event to %s";
        String formatSubject = String.format(subject, user.getUsername(), user.getEmail());
        String formatBody = String.format(body, user.getUsername());
        pool.submit(new Runnable() {
            @Override
            public void run() {
                send(formatSubject, formatBody, user.getEmail());
                System.out.println(formatSubject + formatBody + user.getEmail()
                        + Thread.currentThread().getName());
            }
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Execute " + Thread.currentThread().getName());
    }

    private void send(String subject, String body, String email) {
    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        User user = new User("Ivanov", "ivan@mail.ru");
        emailNotification.emailTo(user);
        emailNotification.close();
    }
}

