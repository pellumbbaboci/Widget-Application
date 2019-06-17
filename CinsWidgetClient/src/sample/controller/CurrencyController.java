package sample.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import sample.service.CurrencyService;
import sample.socket.SocketClient;

import java.math.BigDecimal;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.ResourceBundle;

public class CurrencyController implements Initializable {

    private double usdB;
    private double usdS;
    private double euroB;
    private double euroS;

    @FXML
    private Text usdValue;

    @FXML
    private Text euroValue;

    @FXML
    private Text tlValue;

    @FXML
    private JFXTextField usdInput;

    @FXML
    private JFXTextField euroInput;

    @FXML
    private JFXTextField tlInput;

    @FXML
    private Text usdValueConvert;

    @FXML
    private Text euroValueConvert;

    @FXML
    private Text tlValueConvert;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            SocketClient socketClient = new SocketClient();
            CurrencyService currencyService = new CurrencyService(socketClient);
            Map<String, String> currencyMap = currencyService.getCurrency();

            usdInput.setOnKeyReleased(event -> {

                if (usdInput.getText().isEmpty()) {
                    usdValueConvert.setText("");
                    return;
                }


                double usd = Double.parseDouble(usdInput.getText());
                double euroValueBuy = usdToEuro(usd, "buy");
                String euroValueBuyString = new BigDecimal(String.valueOf(euroValueBuy))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

                double euroValueSell = usdToEuro(usd, "sell");
                String euroValueSellString = new BigDecimal(String.valueOf(euroValueSell))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

                double tlValueBuy = usdToTl(usd, "buy");
                String tlValueBuyString = new BigDecimal(String.valueOf(tlValueBuy))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

                double tlValueSell = usdToTl(usd, "sell");
                String tlValueSellString = new BigDecimal(String.valueOf(tlValueSell))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();


                usdValueConvert.setText("Euro Buy : " + euroValueBuyString + " €\n" +
                        "Euro Sell : " + euroValueSellString + " €\n" +
                        "Turkish Lira Buy : " + tlValueBuyString + " ₺\n" +
                        "Turkish Lira Sell : " + tlValueSellString + " ₺");

            });

            euroInput.setOnKeyReleased(event -> {
                if (euroInput.getText().isEmpty()) {
                    euroValueConvert.setText("");
                    return;
                }

                double euro = Double.parseDouble(euroInput.getText());
                double usdValueBuy = euroToUsd(euro, "buy");
                String usdValueBuyString = new BigDecimal(String.valueOf(usdValueBuy))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

                double usdValueSell = euroToUsd(euro, "sell");
                String usdValueSellString = new BigDecimal(String.valueOf(usdValueSell))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

                double tlValueBuy = euroToTl(euro, "buy");
                String tlValueBuyString = new BigDecimal(String.valueOf(tlValueBuy))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

                double tlValueSell = euroToTl(euro, "sell");
                String tlValueSellString = new BigDecimal(String.valueOf(tlValueSell))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();


                euroValueConvert.setText("USD Buy : " + usdValueBuyString + " $\n" +
                        "USD Sell : " + usdValueSellString + " $\n" +
                        "Turkish Lira Buy : " + tlValueBuyString + " ₺\n" +
                        "Turkish Lira Sell : " + tlValueSellString + " ₺");

            });

            tlInput.setOnKeyReleased(event -> {

                if (tlInput.getText().isEmpty()) {
                    tlValueConvert.setText("");
                    return;
                }

                double tl = Double.parseDouble(tlInput.getText());

                double euroValueBuy = tlToEuro(tl, "buy");
                String euroValueBuyString = new BigDecimal(String.valueOf(euroValueBuy))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

                double euroValueSell = tlToEuro(tl, "sell");
                String euroValueSellString = new BigDecimal(String.valueOf(euroValueSell))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

                double usdValueBuy = tlToUsd(tl, "buy");
                String usdValueBuyString = new BigDecimal(String.valueOf(usdValueBuy))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

                double usdValueSell = tlToUsd(tl, "sell");
                String usdValueSellString = new BigDecimal(String.valueOf(usdValueSell))
                        .setScale(4, BigDecimal.ROUND_HALF_UP).toString();


                tlValueConvert.setText("USD Buy : " + usdValueBuyString + " $\n" +
                        "USD Sell : " + usdValueSellString + " $\n" +
                        "Euro Buy : " + euroValueBuyString + " €\n" +
                        "Euro Sell : " + euroValueSellString + " €");
            });


            //1 dollar
            usdB = Double.parseDouble(currencyMap.get("usdB"));
            usdS = Double.parseDouble(currencyMap.get("usdS"));

            //1 euro
            euroB = Double.parseDouble(currencyMap.get("euroB"));
            euroS = Double.parseDouble(currencyMap.get("euroS"));


            exchange();


        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

    }


    private void exchange() {

        double usdBuy = usdB / euroB;
        String usdBuyString = new BigDecimal(String.valueOf(usdBuy))
                .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

        double usdSell = usdS / euroS;

        String usdSellString = new BigDecimal(String.valueOf(usdSell))
                .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

        usdValue.setText("Buy : " + usdB + " ₺\n" +
                "Sell : " + usdS + " ₺\n" +
                "Buy : " + usdBuyString + " €\n" +
                "Sell : " + usdSellString + " €");

        double euroBuy = euroB / usdB;
        String euroBuyString = new BigDecimal(String.valueOf(euroBuy))
                .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

        double euroSell = euroS / usdS;
        String euroSellString = new BigDecimal(String.valueOf(euroSell))
                .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

        euroValue.setText("Buy : " + euroB + " ₺\n" +
                "Sell : " + euroS + " ₺\n" +
                "Buy : " + euroBuyString + " $\n" +
                "Sell : " + euroSellString + " $");


        double tlUsdBuy = 1 / usdB;
        String tlUsdBuyString = new BigDecimal(String.valueOf(tlUsdBuy))
                .setScale(4, BigDecimal.ROUND_HALF_UP).toString();
        double tlUsdSell = 1 / usdS;
        String tlUsdSellString = new BigDecimal(String.valueOf(tlUsdSell))
                .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

        double tlEuroBuy = 1 / euroB;
        String tlEuroBuyString = new BigDecimal(String.valueOf(tlEuroBuy))
                .setScale(4, BigDecimal.ROUND_HALF_UP).toString();
        double tlEuroSell = 1 / euroS;
        String tlEuroSellString = new BigDecimal(String.valueOf(tlEuroSell))
                .setScale(4, BigDecimal.ROUND_HALF_UP).toString();

        tlValue.setText("Buy : " + tlUsdBuyString + " $\n" +
                "Sell : " + tlUsdSellString + " $\n" +
                "Buy : " + tlEuroBuyString + " €\n" +
                "Sell : " + tlEuroSellString + " €");


    }

    private double usdToEuro(double usd, String type) {

        if (type.equalsIgnoreCase("buy")) {
            double buyRatio = usdB / euroB;
            return usd * buyRatio;
        } else {
            double sellRatio = usdS / euroS;
            return usd * sellRatio;
        }

    }

    private double usdToTl(double usd, String type) {
        if (type.equalsIgnoreCase("buy")) {
            return usd * usdB;
        } else {
            return usd * usdS;
        }
    }

    private double tlToEuro(double tl, String type) {
        if (type.equalsIgnoreCase("buy")) {
            return tl / euroB;
        } else {
            return tl / euroS;
        }
    }

    private double tlToUsd(double tl, String type) {
        if (type.equalsIgnoreCase("buy")) {
            return tl / usdB;
        } else {
            return tl / usdS;
        }
    }

    private double euroToUsd(double euro, String type) {
        if (type.equalsIgnoreCase("buy")) {
            double buyRatio = euroB / usdB;
            return euro * buyRatio;
        } else {
            double sellRatio = euroS / usdS;
            return euro * sellRatio;
        }
    }

    private double euroToTl(double euro, String type) {
        if (type.equalsIgnoreCase("buy")) {
            return euro * euroB;
        } else {
            return euro * euroS;
        }
    }
}
