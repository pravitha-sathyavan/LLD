package org.example.stockdatamonitoring.domain;

import java.io.Serializable;
import java.util.List;

public class StockData implements Serializable {
    private static final long serialVersionUID = 1L;
    private MetaData metaData;
    private List<StockPrice> stockPrices;

    public List<StockPrice> getStockPrices() {
        return stockPrices;
    }

    public void setStockPrices(List<StockPrice> stockPrices) {
        this.stockPrices = stockPrices;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
