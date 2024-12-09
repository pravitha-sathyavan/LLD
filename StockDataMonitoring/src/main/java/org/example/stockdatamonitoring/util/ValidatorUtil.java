package org.example.stockdatamonitoring.util;

import java.util.regex.Pattern;

public class ValidatorUtil {
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("^[A-Za-z0-9 ]+$");

    public static boolean isValidSymbol(String symbol) {
        return SYMBOL_PATTERN.matcher(symbol).matches();
    }
}
