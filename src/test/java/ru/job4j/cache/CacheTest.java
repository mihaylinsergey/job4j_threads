package ru.job4j.cache;

import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base one = new Base(0, 1);
        cache.add(one);
        assertThat(cache.get(0), is(one));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base one = new Base(0, 1);
        cache.add(one);
        cache.update(one);
        assertThat(cache.get(0), is(new Base(0, 2)));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base one = new Base(0, 1);
        Base two = new Base(1, 2);
        cache.add(one);
        cache.add(two);
        cache.delete(one);
        assertThat(cache.get(1), is(two));
    }

    @Test(expected = OptimisticException.class)
    public void whenException() {
        Cache cache = new Cache();
        Base one = new Base(0, 1);
        Base newOne = new Base(0, 2);
        cache.add(one);
        cache.update(newOne);
    }
}