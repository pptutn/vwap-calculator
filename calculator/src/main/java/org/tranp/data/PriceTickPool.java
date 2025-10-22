package org.tranp.data;

import java.util.concurrent.ArrayBlockingQueue;

public class PriceTickPool {
    private final ArrayBlockingQueue<MutablePriceTick> pool;

    public PriceTickPool(final int size) {
        pool = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            pool.offer(new MutablePriceTick());
        }
    }

    public MutablePriceTick borrow() {
//      return pool.poll();
      // garbagy
      final MutablePriceTick tick = pool.poll();
      return tick != null ? tick : new MutablePriceTick();
    }

    public void release(final MutablePriceTick tick) {
        tick.reset();
        pool.offer(tick);
    }
}
