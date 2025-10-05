package org.tranp;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tranp.fx.CurrencyPair;

import java.util.Random;

/**
 * Generates a Market Data Entry for a given symbol
 */
public class MarketDataGenerator {
    private static final Logger LOG = LogManager.getLogger();
    private static final int MIN_QTY = 10_000;
    private static final int MAX_QTY = 1_000_000;

    private final StringBuilder sb = new StringBuilder();
    private final Random random = new Random();

    private PricingConfig pricingConfig;

    public MarketDataGenerator() {
        this.pricingConfig = new PricingConfig();
    }

    /**
     * Generates a MarketDataEntry given a symbol
     * Qty is between 10_000 and 1_000_000 units
     * Price random and follows gaussian distribution
     *
     * @param symbol CurrencyPair
     * @return MarketDataEntry as string in the following format: "time|sym|px|qty"
     */
    public String generateMarketDataEntryForSymbol(final CurrencyPair symbol) {
        sb.setLength(0);

        final double meanPrice = pricingConfig.meanPriceForSymbol(symbol);
        final double stdDev = pricingConfig.getStd(); // Fetch standard deviation
        final long generatedQty = generateRandomQty();
        final double generatedPrice = generatePrice(meanPrice, stdDev);
        return sb.append(System.currentTimeMillis()).append("|")
                .append(symbol).append("|")
                .append(generatedPrice).append("|")
                .append(generatedQty).toString();
    }

    /**
     * Use a random Gaussian distribution to generate a price
     * @param mean average px to begin with
     * @param stdDev standard deviation of px
     * @return a generated px
     */
    private double generatePrice(final double mean, final double stdDev) {
        return mean + (mean * stdDev * random.nextGaussian());
    }

    /**
     * Generates a random Qty between MIN_QTY and MAX_QTY
     * @return random Qty
     */
    private long generateRandomQty() {
        return MIN_QTY + (long) (Math.random() * (MAX_QTY - MIN_QTY + 1));
    }

    void setPricingConfig(final PricingConfig pricingConfig) {
        this.pricingConfig = pricingConfig;
    }

}
