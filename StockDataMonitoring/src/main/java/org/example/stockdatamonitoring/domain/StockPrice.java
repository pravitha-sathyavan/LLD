package org.example.stockdatamonitoring.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StockPrice implements Serializable {
    private static final long serialVersionUID = 1L;
    private Double open;
    private Double high;
    private Double low;
    private Double volume;
    private Double close;
    private LocalDateTime dateTime;

    public StockPrice(LocalDateTime localDateTime, String number, String number1, String number2, String number3, String number4) {
    }

    public StockPrice() {
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
