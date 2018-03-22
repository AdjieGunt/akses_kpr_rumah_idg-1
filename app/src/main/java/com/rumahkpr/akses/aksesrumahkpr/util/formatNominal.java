package com.rumahkpr.akses.aksesrumahkpr.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by JEMMY CALAK on 3/19/2018.
 */

public class formatNominal {
    public String nominal(int mount) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(""); // Don't use null.
        formatter.setDecimalFormatSymbols(symbols);
        String value = formatter.format(mount);
        return value;
    }
}
