package com.company;

public class Start implements Runnable{

    CashLRU<Double, Double> cash = new CashLRU(10);
    Thread thread;

    Start() {
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
            double a = Math.random()*100;
            double b = Math.random()*100;

            cash.put(a,b);
//            System.out.println(i);
            i++;
//            System.out.println(a + ": " + b);
        }

    }

}
