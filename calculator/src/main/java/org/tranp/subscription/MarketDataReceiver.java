package org.tranp.subscription;

import org.tranp.MarketDataGenerator;
import org.tranp.data.MutablePriceTick;
import org.tranp.data.RingBuffer;
import org.tranp.fx.CurrencyPair;
import org.tranp.util.BusySpinDelay;
import org.tranp.util.Parser;

import java.util.Objects;
import java.util.Random;

public class MarketDataReceiver implements Runnable {
    private final Random random = new Random();
    private final MarketDataGenerator marketDataGenerator;
    private final RingBuffer<MutablePriceTick> buffer;

    private volatile boolean running = true;

    public MarketDataReceiver(final MarketDataGenerator marketDataGenerator,
                              final RingBuffer<MutablePriceTick> priceTickRingBuffer) {
        this.marketDataGenerator = Objects.requireNonNull(marketDataGenerator);
        this.buffer = Objects.requireNonNull(priceTickRingBuffer);
    }

    /**
     * Simulate receiving data and publishing to internal data structure
     * Parse MD entry, write into MutablePriceTick claimed from buffer
     * Publish
     */
    @Override
    public void run() {
        while(running) {
            Parser.parseEntry(marketDataGenerator.generateMarketDataEntryForSymbol(CurrencyPair.AUDUSD), buffer.claim());
            buffer.publish();
            final int delay = 30 + random.nextInt(50);
            BusySpinDelay.busySpin(delay);
        }
    }

    public void stop() {
        running = false;
    }
}
