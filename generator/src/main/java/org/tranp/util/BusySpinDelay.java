package org.tranp.util;

public class BusySpinDelay {

    /**
     * Busy spin for a set time in millis
     * @param millis duration of busy spin
     */
    public static void busySpin(final long millis) {
        final long waitUntil = System.nanoTime() + millis * 1_000_000L;
        while (System.nanoTime() < waitUntil) {
            Thread.onSpinWait();
        }
    }
}
