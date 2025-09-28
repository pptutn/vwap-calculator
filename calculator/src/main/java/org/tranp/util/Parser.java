package org.tranp.util;

import org.tranp.data.MutablePriceTick;
import org.tranp.data.PriceTick;
import org.tranp.fx.CurrencyPair;

import java.util.Arrays;
import java.util.List;

public class Parser {
    public static void parseEntry(final String entry, final PriceTick tick) {
        final int p1 = entry.indexOf('|');
        final int p2 = entry.indexOf('|', p1 + 1);
        final int p3 = entry.indexOf('|', p2 + 1);

        final long timestamp = parseLong(entry, 0, p1);
        final String ccyPair = entry.substring(p1 + 1, p2); // substring still allocates
        final double price = parseDouble(entry, p2 + 1, p3);
        final long qty = parseLong(entry, p3 + 1, entry.length());

        tick.timestamp(timestamp)
                .ccyPair(CurrencyPair.valueOf(ccyPair))
                .price(price)
                .qty(qty);
    }

    private static long parseLong(final String s, final int start, final int end) {
        long result = 0;
        boolean negative = false;
        int i = start;
        if (s.charAt(i) == '-') {
            negative = true;
            i++;
        }
        while (i < end) {
            result = result * 10 + (s.charAt(i++) - '0');
        }
        return negative ? -result : result;
    }

    private static double parseDouble(String s, int start, int end) {
        // simplest: fall back to Double.parseDouble(s.substring(start, end))
        // but that allocates a substring
        // zero-GC way: use a fast path parser (like Agrona or Chronicle libraries)
        return Double.parseDouble(s.substring(start, end));
    }

}
