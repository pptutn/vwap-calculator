package org.tranp;

import org.springframework.context.annotation.Configuration;
import org.tranp.fx.CurrencyPair;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class PricingConfig {
    private final Map<CurrencyPair, Double> symbolToMeanPrice = new HashMap<>();
    private final double std;

    public PricingConfig() {
        final Properties props = new Properties();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("pricing.properties")) {
            if (is == null) throw new RuntimeException("pricing.properties not found in resources");
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load pricing.properties", e);
        }

        final String pricingRaw = props.getProperty("pricing.properties");
        std = Double.parseDouble(props.getProperty("pricing.std"));

        if (pricingRaw != null) parsePricing(pricingRaw);
    }

    private void parsePricing(String raw) {
        if (raw == null) return;

        // Strip curly braces
        raw = raw.trim();
        if (raw.startsWith("{") && raw.endsWith("}")) {
            raw = raw.substring(1, raw.length() - 1);
        }

        // Split by commas
        final String[] entries = raw.split(",");

        for (final String entry : entries) {
            final String[] parts = entry.split("=");
            if (parts.length != 2) continue;

            final String symbol = parts[0].trim().replace("'", "");
            final double meanPrice = Double.parseDouble(parts[1].trim());
            symbolToMeanPrice.put(CurrencyPair.valueOf(symbol), meanPrice);
        }
    }

    public double meanPriceForSymbol(final CurrencyPair symbol) {
        return symbolToMeanPrice.get(symbol);
    }

    public double getStd() {
        return std;
    }
}
