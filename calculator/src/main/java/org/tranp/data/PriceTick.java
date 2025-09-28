package org.tranp.data;

import org.tranp.fx.CurrencyPair;

public interface PriceTick {
    double price();
    CurrencyPair ccyPair();
    long qty();
    long timestamp();
    PriceTick price(double price);
    PriceTick ccyPair(CurrencyPair ccyPair);
    PriceTick qty(long qty);
    PriceTick timestamp(long timestamp);
    CharSequence priceTick();
    void copy(PriceTick source);
}
