package io.currency;



public class Main {


    public static void main(String[] args) {

        CurrencyScanThread currencyScanThread = new CurrencyScanThread();
        CurrencyServer currencyServer = new CurrencyServer(currencyScanThread);

        Thread threadScan = new Thread(currencyScanThread);
        threadScan.start();
        Thread threadServer = new Thread(currencyServer);
        threadServer.start();


        try {
            threadServer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
