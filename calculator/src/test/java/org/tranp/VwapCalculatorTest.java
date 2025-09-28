package org.tranp;

import org.junit.Before;
import org.junit.Test;
import org.tranp.data.MutablePriceTick;
import org.tranp.data.PriceTick;
import org.tranp.data.PriceTickPool;
import org.tranp.fx.CurrencyPair;
import org.tranp.vwap.VwapCalculator;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class VwapCalculatorTest {
    private static VwapCalculator vwapCalculator;
    private static Queue<MutablePriceTick> queue;
    private static final MutablePriceTick priceTick = new MutablePriceTick();

    @Before
    public void setup() {
        queue = new ArrayDeque<>();
        vwapCalculator = new VwapCalculator(new PriceTickPool(64), queue);
    }

    @Test
    public void testAccept() {
        final long currentTime = System.currentTimeMillis();
        final double price = 0.65;
        final CurrencyPair ccyPair = CurrencyPair.AUDUSD;
        final long qty = 1_000_000;

        final PriceTick tick = priceTick
                .price(price)
                .ccyPair(ccyPair)
                .qty(qty)
                .timestamp(currentTime);

        vwapCalculator.accept(tick);
        assertEquals(tick, queue.peek());
    }

    @Test
    public void testRemoveOldTicks() {
        final long currentTime = System.currentTimeMillis();
        final double price = 0.65;
        final CurrencyPair ccyPair = CurrencyPair.AUDUSD;
        final long qty = 1_000_000;

        final PriceTick tick = priceTick
                .price(price)
                .ccyPair(ccyPair)
                .qty(qty)
                .timestamp(currentTime - 3_600_001);

        final PriceTick tick2 = new MutablePriceTick()
                .price(price)
                .ccyPair(ccyPair)
                .qty(qty)
                .timestamp(currentTime);


        vwapCalculator.accept(tick);
        vwapCalculator.accept(tick2);

        assertEquals(queue.peek(), tick);

        vwapCalculator.removeOldTicks();
        assertEquals(1, queue.size());
        assertEquals(queue.peek(), tick2);

    }

    @Test
    public void removeOldTicksShouldReturnNextTimestampInQueueOrElseZero() {
        final long currentTime = System.currentTimeMillis();
        final double price = 0.65;
        final CurrencyPair ccyPair = CurrencyPair.AUDUSD;
        final long qty = 1_000_000;

        final PriceTick tick = priceTick
                .price(price)
                .ccyPair(ccyPair)
                .qty(qty)
                .timestamp(currentTime);

        vwapCalculator.accept(tick);
        assertEquals(queue.peek(), tick);

        final long nextTimeStamp = vwapCalculator.removeOldTicks();
        assertEquals(1, queue.size());
        assertEquals(currentTime, nextTimeStamp);
    }

    @Test
    public void testVwap() {
        final long currentTime = System.currentTimeMillis();
        final double price = 0.65;
        final CurrencyPair ccyPair = CurrencyPair.AUDUSD;
        final long qty = 1_000_000;

        final double price2 = 0.65;
        final CurrencyPair ccyPair2 = CurrencyPair.AUDUSD;
        final long qty2 = 1_000_000;

        final double price3 = 0.67;
        final CurrencyPair ccyPair3 = CurrencyPair.AUDUSD;
        final long qty3 = 2_000_000;

        final PriceTick tick = new MutablePriceTick()
                .price(price)
                .ccyPair(ccyPair)
                .qty(qty)
                .timestamp(currentTime);

        final PriceTick tick2 = new MutablePriceTick()
                .price(price2)
                .ccyPair(ccyPair2)
                .qty(qty2)
                .timestamp(currentTime);

        final PriceTick tick3 = new MutablePriceTick()
                .price(price3)
                .ccyPair(ccyPair3)
                .qty(qty3)
                .timestamp(currentTime);

        vwapCalculator.accept(tick);
        vwapCalculator.accept(tick2);

        assertEquals(0.65, vwapCalculator.vwap(), 0.0);

        vwapCalculator.accept(tick3);
        assertEquals(0.66, vwapCalculator.vwap(), 0.0);

    }
}