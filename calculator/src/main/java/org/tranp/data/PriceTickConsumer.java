package org.tranp.data;

import org.tranp.vwap.VwapCalculator;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class PriceTickConsumer implements Runnable {
    private final RingBuffer<MutablePriceTick> buffer;
    private final VwapCalculator vwapCalculator;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private Thread workerThread;

    public PriceTickConsumer(final RingBuffer<MutablePriceTick> priceTickRingBuffer,
                             final VwapCalculator vwapCalculator) {
        this.buffer = Objects.requireNonNull(priceTickRingBuffer);
        this.vwapCalculator = Objects.requireNonNull(vwapCalculator);
    }

    public void stop() {
        running.set(false);
        if (workerThread != null) {
            workerThread.interrupt();
        }
    }

    @Override
    public void run() {
        while (running.get()) {
            final PriceTick tick = buffer.poll();
            if (tick != null) {
                vwapCalculator.accept(tick);
            }
            // TODO: returns the last time in millis
            vwapCalculator.removeOldTicks();
        }
    }
}
