package com.company;

public class Start implements Runnable{

    Cash<Integer, Integer> cash;
    Thread thread;

    Start(Cash cash) {
        this.cash = cash;
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

            cash.put(a,b);
            i++;

        }

            System.out.println("Invocate get:");
            if(cash.get(4)==null) cash.put(4, (int)(Math.random()*10));
            if(cash.get(3)==null) cash.put(3, (int)(Math.random()*10));
            if(cash.get(3)==null) cash.put(3, (int)(Math.random()*10));
            if(cash.get(3)==null) cash.put(3, (int)(Math.random()*10));
            if(cash.get(7)==null) cash.put(7, (int)(Math.random()*10));
            if(cash.get(5)==null) cash.put(5, (int)(Math.random()*10));
            if(cash.get(0)==null) cash.put(0, (int)(Math.random()*10));
            if(cash.get(4)==null) cash.put(4, (int)(Math.random()*10));

    }

}
