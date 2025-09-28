package org.tranp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tranp.fx.CurrencyPair;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MarketDataGeneratorTest {
    @Mock
    final PricingConfig pricingConfig = mock(PricingConfig.class);

    final MarketDataGenerator marketDataGenerator = new MarketDataGenerator();

    @Test
    public void shouldGenerateMarketDataEntry() {
        when(pricingConfig.meanPriceForSymbol(CurrencyPair.AUDUSD)).thenReturn(0.65);
        when(pricingConfig.getStd()).thenReturn(0d);
        marketDataGenerator.setPricingConfig(pricingConfig);

        final String mdEntry = marketDataGenerator.generateMarketDataEntryForSymbol(CurrencyPair.AUDUSD);
        assertTrue(mdEntry.contains("0.65"));
        assertTrue(mdEntry.contains("AUDUSD"));
    }


}