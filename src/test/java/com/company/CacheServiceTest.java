package com.company;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CacheServiceTest {

    private CacheService cacheServiceLRU;
    private Cache cacheLRU;
    private FileLevelCache fileLevelCache;


    @BeforeEach
    public void init() {

        cacheLRU = mock(CacheLRU.class);
        fileLevelCache = mock(FileLevelCache.class);

        cacheServiceLRU = new CacheService(10, cacheLRU);
    }

    @AfterEach
    public void end() {

    }

    @Test
    void put() {


        when(cacheLRU.put(5, 5)).thenReturn(new AbstractMap.SimpleEntry(1, 1));
        when(fileLevelCache.put(5, 5)).thenReturn(new AbstractMap.SimpleEntry(1, 1));
        assertEquals(1, cacheServiceLRU.put(5, 5).getValue());
//---------------------------------------------------------------------------------------
        when(cacheLRU.put(5, 5)).thenReturn(null);
        when(fileLevelCache.put(5, 5)).thenReturn(new AbstractMap.SimpleEntry(1, 1));
        assertEquals(null, cacheServiceLRU.put(5, 5));
    }

    @Test
    void get() {

        when(cacheLRU.get(5)).thenReturn(5);
        when(fileLevelCache.get(5)).thenReturn(5);
        assertEquals(5, fileLevelCache.get(5));
//---------------------------------------------------------------------------------------

        when(cacheLRU.get(5)).thenReturn(null);
        when(fileLevelCache.get(5)).thenReturn(5);
        assertEquals(5, fileLevelCache.get(5));
//---------------------------------------------------------------------------------------

        when(cacheLRU.get(5)).thenReturn(5);
        when(fileLevelCache.get(5)).thenReturn(5);
        assertEquals(5, fileLevelCache.get(5));
//---------------------------------------------------------------------------------------

        when(cacheLRU.get(5)).thenReturn(null);
        when(fileLevelCache.get(5)).thenReturn(null);
        assertEquals( null, fileLevelCache.get(5));
//---------------------------------------------------------------------------------------

    }

    @Test
    void clear() {

    }
}