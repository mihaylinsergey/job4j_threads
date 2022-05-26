package ru.job4j.pool;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class ParallelSearchTest {

    @Test
    public void whenFound() {
        String[] array = new String[] {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "[", "]"};
        assertThat(ParallelSearch.search(array, "]"), is(11));
    }

    @Test
    public void whenNotFound() {
        String[] array = new String[] {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "[", "]"};
        assertThat(ParallelSearch.search(array, "a"), is(-1));
    }
}