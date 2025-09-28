package org.tranp.data;

import org.tranp.fx.CurrencyPair;

public class MutablePriceTick implements PriceTick, Reusable {
    private double price;
    private CurrencyPair ccyPair; // maybe use Sb
    private long qty;
    private long timestamp;
    private final StringBuilder sb = new StringBuilder(64);

    @Override
    public double price() {
        return price;
    }

    @Override
    public CurrencyPair ccyPair() {
        return ccyPair;
    }

    @Override
    public long qty() {
        return qty;
    }

    @Override
    public long timestamp() {
        return timestamp;
    }

    @Override
    public PriceTick price(final double price) {
        this.price = price;
        return this;
    }

    @Override
    public PriceTick ccyPair(final CurrencyPair ccyPair) {
        this.ccyPair = ccyPair;
        return this;
    }

    @Override
    public PriceTick qty(final long qty) {
        this.qty = qty;
        return this;
    }

    @Override
    public PriceTick timestamp(final long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public CharSequence priceTick() {
        sb.setLength(0);
        sb.append(timestamp).append("|")
                .append(ccyPair).append("|")
                .append(price).append("|")
                .append(qty);
        return sb;
    }

    @Override
    public void copy(final PriceTick source) {
        this.timestamp(source.timestamp())
                .ccyPair(source.ccyPair())
                .price(source.price())
                .qty(source.qty());
    }

    @Override
    public String toString() {
        return priceTick().toString();
    }

    @Override
    public void reset() {
        this.timestamp = 0;
        this.ccyPair = CurrencyPair.UNKNOWN; //make this less garbagy
        this.price = 0;
        this.qty = 0;
    }
}
