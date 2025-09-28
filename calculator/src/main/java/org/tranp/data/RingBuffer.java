package org.tranp.data;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public class RingBuffer<T> {

    private final T[] buffer;
    private final int mask;
    private final AtomicLong writeIndex = new AtomicLong(0);
    private final AtomicLong readIndex = new AtomicLong(0);
    private final AtomicLong publishedIndex = new AtomicLong(-1);

    public RingBuffer(final Supplier<T> factory, final int size) {
        if (!isPowOfTwo(size)) {
            throw new IllegalArgumentException("RingBuffer size must be power of two");
        }
        buffer = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = factory.get();
        }
        mask = size - 1;
    }

    public T claim() {
        final long w = writeIndex.getAndIncrement();
        return buffer[(int) (w & mask)];
    }

    public void publish() {
        publishedIndex.incrementAndGet();
    }

    public T poll() {
        final long next = readIndex.get();

        if (next > publishedIndex.get()) {
            return null;
        }

        readIndex.getAndIncrement();
        return buffer[(int) (next & mask)];
    }

    private boolean isPowOfTwo(final int x) {
        return (x > 0) && ((x & (x - 1)) == 0);
    }

//    private int capacity() {
//        return
//    }
}
