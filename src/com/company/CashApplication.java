package com.company;


public class CashApplication {
    public static void main(String[] args) {

        Start start = new Start(new CashService(10, new CashARC(10)));

    }

}



