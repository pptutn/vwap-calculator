package org.tranp.data;

import java.util.function.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tranp.fx.CurrencyPair;

import static org.junit.jupiter.api.Assertions.*;

class RingBufferTest {
    private final Supplier<MutablePriceTick> mutablePriceTickFactory = MutablePriceTick::new;
    private RingBuffer<MutablePriceTick> priceTickRingBuffer;

    @BeforeEach
    void setUp() {
        priceTickRingBuffer = new RingBuffer<>(mutablePriceTickFactory, 64);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void claim() {
        final MutablePriceTick tick = priceTickRingBuffer.claim();

        assertEquals(0, tick.qty());
        assertEquals(0d, tick.price());
        assertEquals(null, tick.ccyPair()); // todo maybe initialise as unknown?

        tick.timestamp(123L)
                .ccyPair(CurrencyPair.EURJPY)
                .qty(123)
                .price(145.001);

        priceTickRingBuffer.publish();

        final MutablePriceTick polledTick = priceTickRingBuffer.poll();
        assertEquals(polledTick, tick);
    }
}