package ness.edu.xmlandfirebase;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.URL;

public class CurrencyDataSource {
    public interface OnCurrencyArrivedListener{
        void onCurrencyArrived(List<Currency> data, Exception e);
    }
    public static void getCurrencies(final OnCurrencyArrivedListener listener){
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //URL url = new URL("....");
                    URL url = new URL("http://www.boi.org.il/currency.xml");
                    //con = url.openConnection()
                    URLConnection con = url.openConnection();
                    //in  = con.getInputStream (BINARY)
                    InputStream in = con.getInputStream();
                    //String xml = IO.read(in) // Convert the binary to String
                    String xml = IO.read(in);
                    //Method that parses the xml into Currency. Jsoup.parse()
                    List<Currency> result = parse(xml);
                    listener.onCurrencyArrived(result, null);;
                }
                catch (Exception e){
                    listener.onCurrencyArrived(null, e);
                }
            }
        });

        service.shutdown();
        //Currency?
    }

    private static List<Currency> parse(String xml) {
        Document document = Jsoup.parse(xml);
        List<Currency> currencies = new ArrayList<>();

        Elements allCurrencies = document.getElementsByTag("CURRENCY");
        for (Element c : allCurrencies) {
            String name = c.getElementsByTag("NAME").get(0).text();
            String currencyCode = c.getElementsByTag("CURRENCYCODE").get(0).text();
            String country = c.getElementsByTag("COUNTRY").get(0).text();
            int unit = Integer.valueOf(c.getElementsByTag("UNIT").get(0).text());
            double rate = Double.valueOf(c.getElementsByTag("RATE").get(0).text());
            double change = Double.valueOf(c.getElementsByTag("CHANGE").get(0).text());

            Currency currency = new Currency(name, unit, currencyCode, country, rate, change);
            currencies.add(currency);
        }

        return currencies;
    }

    //static is Simpler no reference to the outer class
    public static class Currency{
        private final String name;
        private final int unit;
        private final String currencyCode;
        private final String country;
        private final double rate;
        private final double change;

        //Constructor
        public Currency(String name, int unit, String currencyCode, String country, double rate, double change) {
            this.name = name;
            this.unit = unit;
            this.currencyCode = currencyCode;
            this.country = country;
            this.rate = rate;
            this.change = change;
        }

        //Getters
        public String getName() {
            return name;
        }
        public int getUnit() {
            return unit;
        }
        public String getCurrencyCode() {
            return currencyCode;
        }
        public String getCountry() {
            return country;
        }
        public double getRate() {
            return rate;
        }
        public double getChange() {
            return change;
        }

        //toString
        @Override
        public String toString() {
            return "Currency{" +
                    "name='" + name + '\'' +
                    ", unit=" + unit +
                    ", currencyCode='" + currencyCode + '\'' +
                    ", country='" + country + '\'' +
                    ", rate=" + rate +
                    ", change=" + change +
                    '}';
        }
    }
}
