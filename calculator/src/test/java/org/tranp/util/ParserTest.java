package org.tranp.util;

import junit.framework.TestCase;
import org.tranp.data.MutablePriceTick;
import org.tranp.data.PriceTick;
import org.tranp.fx.CurrencyPair;

public class ParserTest extends TestCase {

    public void testParseEntry() {
        final String entry = "1758937702053|AUDUSD|0.65|18279";
        final PriceTick tick = new MutablePriceTick();

        Parser.parseEntry(entry, tick);
        assertEquals(1758937702053L, tick.timestamp());
        assertEquals(CurrencyPair.AUDUSD, tick.ccyPair());
        assertEquals(0.65, tick.price());
        assertEquals(18279, tick.qty());
    }

}