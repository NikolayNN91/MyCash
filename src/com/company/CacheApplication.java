package com.company;


public class CacheApplication {
    public static void main(String[] args) {

        CacheService<Integer, Integer> cacheService = new CacheService(10, new CacheLRU(10));

        int i=0;
        while (i<1000) {
            int a = (int)(Math.random()*15);
            int b = (int)(Math.random()*15);

            cacheService.put(a,b);
            i++;
        }

        System.out.println("Invocate get:");
        if(cacheService.get(4)==null) cacheService.put(4, (int)(Math.random()*10));
        if(cacheService.get(3)==null) cacheService.put(3, (int)(Math.random()*10));
        if(cacheService.get(3)==null) cacheService.put(3, (int)(Math.random()*10));
        if(cacheService.get(3)==null) cacheService.put(3, (int)(Math.random()*10));
        if(cacheService.get(7)==null) cacheService.put(7, (int)(Math.random()*10));
        if(cacheService.get(5)==null) cacheService.put(5, (int)(Math.random()*10));
        if(cacheService.get(0)==null) cacheService.put(0, (int)(Math.random()*10));
        if(cacheService.get(4)==null) cacheService.put(4, (int)(Math.random()*10));
        System.out.println("get: " + cacheService.get(3));
        System.out.println("get: " + cacheService.get(2));
        System.out.println("get: " + cacheService.get(3));
        System.out.println("get: " + cacheService.get(3));


    }

}



