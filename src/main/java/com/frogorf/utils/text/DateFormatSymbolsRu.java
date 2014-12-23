package com.frogorf.utils.text;

import java.text.DateFormatSymbols;

/**
 * Created by Alex on 24.12.14.
 */
public class DateFormatSymbolsRu extends DateFormatSymbols {
    @Override
    public String[] getMonths() {
        return new String[]{"января", "Февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря"};
    }
}
