package org.tranp.fx;

import java.util.HashMap;
import java.util.Map;

public enum CurrencyPair {
    UNKNOWN(0),
    EURUSD(1),
    USDJPY(2),
    GBPUSD(3),
    AUDUSD(4),
    USDCAD(5),
    USDCHF(6),
    EURJPY(7),
    USDCNY(8),
    EURGBP(9),
    NZDUSD(10);


    // could use a primitive HM here
    private static final Map<Integer, CurrencyPair> pairsById = new HashMap<>();

    private final int id;
    CurrencyPair(final int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static CurrencyPair fromId(final int id) {
        return pairsById.get(id);
    }

    // Thread Safe
    static {
        final CurrencyPair[] ccyPairs = values();
        for (int i = 0; i < ccyPairs.length; i++ ) {
            if (pairsById.put(ccyPairs[i].id(), ccyPairs[i]) != null) {
                throw new IllegalArgumentException("Duplicate id: " + ccyPairs[i].id());
            }
        }
    }


}
