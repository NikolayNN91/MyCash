package com.company;

public class Start implements Runnable{

    CashService<Integer, Integer> cashService;
    Thread thread;

    Start(CashService cashService) {
        this.cashService = cashService;
        thread = new Thread(this, "Potokich");
        thread.start();
        if(thread.isAlive()) {
            System.out.println("start");
        }
        System.out.println();

    }


    @Override
    public void run() {

        int i=0;
        while (i<20) {
            int a = (int)(Math.random()*15);
            int b = (int)(Math.random()*15);

            cashService.put(a,b);
            i++;

        }

            System.out.println("Invocate get:");
            if(cashService.get(4)==null) cashService.put(4, (int)(Math.random()*10));
            if(cashService.get(3)==null) cashService.put(3, (int)(Math.random()*10));
            if(cashService.get(3)==null) cashService.put(3, (int)(Math.random()*10));
            if(cashService.get(3)==null) cashService.put(3, (int)(Math.random()*10));
            if(cashService.get(7)==null) cashService.put(7, (int)(Math.random()*10));
            if(cashService.get(5)==null) cashService.put(5, (int)(Math.random()*10));
            if(cashService.get(0)==null) cashService.put(0, (int)(Math.random()*10));
            if(cashService.get(4)==null) cashService.put(4, (int)(Math.random()*10));
        System.out.println("get: " + cashService.get(3));
        System.out.println("get: " + cashService.get(2));
        System.out.println("get: " + cashService.get(3));
        System.out.println("get: " + cashService.get(3));

    }

}
