package org.example.stockdatamonitoring.exception;

public class InvalidSymbolException extends Throwable {
    public InvalidSymbolException(String msg){
        super(msg);
    }
}
