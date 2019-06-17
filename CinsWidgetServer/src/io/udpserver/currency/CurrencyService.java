package io.udpserver.currency;

public class CurrencyService {

    private final CurrencyScanThread currencyScanThread;

    public CurrencyService(CurrencyScanThread currencyScanThread) {
        this.currencyScanThread = currencyScanThread;
    }

    public String getCurrencyString() {

        if (this.currencyScanThread.getEuroB() == null ||
                this.currencyScanThread.getEuroS() == null ||
                this.currencyScanThread.getUsdB() == null ||
                this.currencyScanThread.getUsdS() == null) {
            return "Error receiving the data from the service " + this.currencyScanThread.getUrl();
        }

        StringBuilder sendData = new StringBuilder();
        sendData.append("euroB:" + this.currencyScanThread.getEuroB() + ",");
        sendData.append("euroS:" + this.currencyScanThread.getEuroS() + ",");
        sendData.append("usdB:" + this.currencyScanThread.getUsdB() + ",");
        sendData.append("usdS:" + this.currencyScanThread.getUsdS());
        return sendData.toString();
    }
}
