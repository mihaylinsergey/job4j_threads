package ru.job4j.pool;

import org.junit.Test;
import ru.job4j.pool.RolColSum.*;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void sum() {
        int[][] numbers = {{1, 2},
                           {3, 4}};
    Sums[] expected = new Sums[] {new Sums(3, 4), new Sums(7, 6)};
    assertThat(expected, is(RolColSum.sum(numbers)));
    }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        int[][] numbers = {{1, 2},
                           {3, 4}};
        Sums[] expected = new Sums[] {new Sums(3, 4), new Sums(7, 6)};
        assertThat(expected, is(RolColSum.asyncSum(numbers)));
    }
}