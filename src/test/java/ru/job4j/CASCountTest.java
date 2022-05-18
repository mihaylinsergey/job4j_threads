package ru.job4j;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void increment() throws InterruptedException {
        CASCount test = new CASCount();
        Thread one = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        test.increment();
                    }

                },
                "one"
        );
        Thread two = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        test.increment();
                    }
                },
                "two"
        );
        one.start();
        two.start();
        one.join();
        two.join();
        assertThat(110, is(test.get()));
    }
}