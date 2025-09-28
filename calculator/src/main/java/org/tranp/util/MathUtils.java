package org.tranp.util;

public class MathUtils {

    /**
     * highestOneBit(n) returns the largest power of 2 ≤ n.
     * Subtract 1 and shift left to get the next power of 2.
     * All operations are bitwise no allocations, no garbage.
     * @param n
     * @return
     */
    public static int nextPowerOfTwo(final int n) {
        if (n <= 1) return 1;
        final int highest = Integer.highestOneBit(n - 1);
        return highest << 1;
    }

    public static int nextPowerOfTwoTwo(int n) {
        n--;
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return n + 1;
    }
}
