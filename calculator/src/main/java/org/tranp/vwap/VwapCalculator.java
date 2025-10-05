package org.tranp.vwap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tranp.data.MutablePriceTick;
import org.tranp.data.PriceTick;
import org.tranp.data.PriceTickPool;

import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * Optimisations:
 * 1. Ring Buffer
 */
public class VwapCalculator implements Consumer<PriceTick> {
    private static final int TIME_WINDOW_MILLIS = 5000;
    private static final Logger LOG = LogManager.getLogger(VwapCalculator.class);

    private final PriceTickPool pool;
    private final Queue<MutablePriceTick> queue;
    private long sumQty;
    private double sumQtyTimesPrice;

    public VwapCalculator(final PriceTickPool priceTickPool,
                          final Queue<MutablePriceTick> queue) {
        this.pool = Objects.requireNonNull(priceTickPool);
        this.queue = Objects.requireNonNull(queue);
    }

    public void accept(final PriceTick priceTick) {
        LOG.debug("Accepting Tick {}", priceTick);
        this.sumQtyTimesPrice += priceTick.price() * priceTick.qty();
        this.sumQty += priceTick.qty();

        final MutablePriceTick tick = pool.borrow();
        tick.copy(priceTick);
        queue.add(tick);
    }

    /**
     *
     * @return returns the next timestamp in the queue so we might be able to optimise
     */
    public long removeOldTicks() {
        while (!queue.isEmpty() && queue.peek().timestamp() + TIME_WINDOW_MILLIS < System.currentTimeMillis()) {
            final MutablePriceTick tick = queue.remove(); // TODO:
            sumQtyTimesPrice -= tick.price() * tick.qty();
            sumQty -= tick.qty();
            pool.release(tick);
        }

        if (!queue.isEmpty()) {
            return queue.peek().timestamp();
        }

        return 0;
    }

    public double vwap() {
        return sumQty == 0 ? 0.0 : sumQtyTimesPrice / sumQty;
    }

    public String vwapDetail() {
        return sumQty == 0 ? "0.0, 0" : String.format("%.6f, %d", sumQtyTimesPrice / sumQty, sumQty);
    }
}
