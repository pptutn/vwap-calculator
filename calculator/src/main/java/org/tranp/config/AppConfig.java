package org.tranp.config;

import java.util.ArrayDeque;
import java.util.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.tranp.MarketDataGenerator;
import org.tranp.data.MutablePriceTick;
import org.tranp.data.PriceTickPool;
import org.tranp.data.PriceTickConsumer;
import org.tranp.data.RingBuffer;
import org.tranp.subscription.MarketDataReceiver;
import org.tranp.vwap.VwapAppRunner;
import org.tranp.vwap.VwapCalculator;


@Configuration
public class AppConfig {
    private static final int DEFAULT_BUFFER_SIZE = 65536;
    private static final int DEFAULT_POOL_SIZE = 5_000_000;

    @Bean
    public MarketDataGenerator marketDataGenerator() {
        return new MarketDataGenerator();
    }

    @Bean
    public PriceTickPool priceTickPool() {
        return new PriceTickPool(DEFAULT_POOL_SIZE);
    }

    @Bean
    public RingBuffer<MutablePriceTick> priceTickRingBuffer() {
        return new RingBuffer<>(MutablePriceTick::new, DEFAULT_BUFFER_SIZE);
    }

    @Bean
    public MarketDataReceiver marketDataPublisher(final MarketDataGenerator marketDataGenerator,
                                                  final RingBuffer<MutablePriceTick> priceTickRingBuffer) {
        return new MarketDataReceiver(marketDataGenerator, priceTickRingBuffer);
    }

    @Bean
    public PriceTickConsumer priceTickConsumer(final RingBuffer<MutablePriceTick> priceTickRingBuffer,
                                               final VwapCalculator vwapCalculator) {
        return new PriceTickConsumer(priceTickRingBuffer, vwapCalculator);
    }

    @Bean
    public VwapCalculator vwapCalculator(final PriceTickPool priceTickPool) {
        final Queue<MutablePriceTick> queue = new ArrayDeque<>();
        return new VwapCalculator(priceTickPool, queue);
    }

    @Bean
    public VwapAppRunner vwapAppRunner(final MarketDataReceiver marketDataReceiver,
                                       final PriceTickConsumer priceTickConsumer,
                                       final VwapCalculator vwapCalculator) {
        return new VwapAppRunner(marketDataReceiver, priceTickConsumer, vwapCalculator);
    }
}