package org.example.stockdatamonitoring.util;

public class StockDataConstants {
    public static final String META_DATA = "Meta Data";
    public static final String INFORMATION = "1. Information";
    public static final String SYMBOL = "2. Symbol";
    public static final String LAST_REFRESHED = "3. Last Refreshed";
    public static final String INTERVAL = "4. Interval";
    public static final String OUTPUT_SIZE = "5. Output Size";
    public static final String TIME_ZONE = "6. Time Zone";

    public static final String TIME_SERIES_PREFIX = "Time Series (";
    public static final String OPEN = "1. open";
    public static final String HIGH = "2. high";
    public static final String LOW = "3. low";
    public static final String CLOSE = "4. close";
    public static final String VOLUME = "5. volume";

    public static final String BEST_MATCHES = "bestMatches";
    public static final String STOCK_NAME = "2. name";
    public static final String STOCK_SYMBOL = "1. symbol";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MAILTEXT = "There were some changes in the stock values of below symbols in past one hour. \n\nFor more details please login to StockData Monitoring application. \n \n";
    public static final String SENDER  = "stockdatamonitoring@gmail.com";
    public static final String SUBJECT = "Stock Data Changes Notification ";

    private StockDataConstants() {
        // Prevent instantiation
    }
}
