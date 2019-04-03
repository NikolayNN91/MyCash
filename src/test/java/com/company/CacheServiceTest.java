package com.company;

import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class CacheServiceTest {

    private CacheService cacheService;

    @BeforeEach
    public void init() {
        cacheService = new CacheService(10, new CacheLRU(10));

        cacheService.put(1, 1);
        cacheService.put(2, 2);
        cacheService.put(3, 3);
        cacheService.put(4, 4);
        cacheService.put(5, 5);
        cacheService.put(6, 6);
        cacheService.put(7, 7);
        cacheService.put(8, 8);
        cacheService.put(9, 9);
        cacheService.put(10, 10);
        cacheService.put(11, 11);
    }

    @AfterEach
    public void end() {
        cacheService.clear();
    }

    @Test
    @Disabled
    void put() {


    }

    @Test
    void get() {
        assertEquals(1, cacheService.get(1));
        assertEquals(2, cacheService.get(2));
    }

    @Test
    void clear() {

        cacheService.clear();

        assertTrue(cacheService.get(1)==null);

    }
}