package org.tranp.data;

import junit.framework.TestCase;
import org.tranp.fx.CurrencyPair;

public class MutablePriceTickTest extends TestCase {


    public void testShouldCorrectlySetAndGet() {
        final MutablePriceTick tick = new MutablePriceTick();

        tick.timestamp(123L)
                .ccyPair(CurrencyPair.EURGBP)
                .price(123.456)
                .qty(1234);

        assertEquals(123L, tick.timestamp());
        assertEquals(CurrencyPair.EURGBP, tick.ccyPair());
        assertEquals(123.456, tick.price());
        assertEquals(1234, tick.qty());
    }

    public void testCopy() {
        final MutablePriceTick tick = new MutablePriceTick();

        tick.timestamp(123L)
                .ccyPair(CurrencyPair.EURGBP)
                .price(123.456)
                .qty(1234);

        assertEquals(123L, tick.timestamp());
        assertEquals(CurrencyPair.EURGBP, tick.ccyPair());
        assertEquals(123.456, tick.price());
        assertEquals(1234, tick.qty());

        final MutablePriceTick tick2 = new MutablePriceTick();
        tick2.copy(tick);

        assertEquals(123L, tick2.timestamp());
        assertEquals(CurrencyPair.EURGBP, tick2.ccyPair());
        assertEquals(123.456, tick2.price());
        assertEquals(1234, tick2.qty());
    }


    public void testReset() {
        final MutablePriceTick tick = new MutablePriceTick();

        tick.timestamp(123L)
                .ccyPair(CurrencyPair.EURGBP)
                .price(123.456)
                .qty(1234);

        assertEquals(123L, tick.timestamp());
        assertEquals(CurrencyPair.EURGBP, tick.ccyPair());
        assertEquals(123.456, tick.price());
        assertEquals(1234, tick.qty());

        tick.reset();

        assertEquals(0, tick.timestamp());
        assertEquals(CurrencyPair.UNKNOWN, tick.ccyPair());
        assertEquals(0d, tick.price());
        assertEquals(0, tick.qty());
    }
}