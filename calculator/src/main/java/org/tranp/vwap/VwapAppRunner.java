package org.tranp.vwap;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.tranp.subscription.MarketDataReceiver;
import org.tranp.data.PriceTickConsumer;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class VwapAppRunner {
    private static final Logger LOG = LogManager.getLogger(VwapAppRunner.class);
    private final MarketDataReceiver marketDataReceiver;
    private final PriceTickConsumer priceTickConsumer;
    private final VwapCalculator vwapCalculator;

    public VwapAppRunner(final MarketDataReceiver marketDataReceiver,
                         final PriceTickConsumer priceTickConsumer,
                         final VwapCalculator vwapCalculator) {
        this.marketDataReceiver = Objects.requireNonNull(marketDataReceiver);
        this.priceTickConsumer = Objects.requireNonNull(priceTickConsumer);
        this.vwapCalculator = Objects.requireNonNull(vwapCalculator);
    }

    @PostConstruct
    public void startApp() {
        final Thread receiverThread = new Thread(marketDataReceiver, "MarketDataReceiver-Thread");
        final Thread consumerThread = new Thread(priceTickConsumer, "PriceTickConsumer-Thread");

        receiverThread.start();
        consumerThread.start();

        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
//            System.out.println("VWAP: " + vwapCalculator.vwapDetail());
            LOG.info("VWAP: {} ", vwapCalculator.vwapDetail());
        }, 0, 5, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.info("---- STOPPING ----");
//            System.out.println("---- STOPPING ----");
            marketDataReceiver.stop();
            priceTickConsumer.stop();
            scheduler.shutdownNow();
            try {
                receiverThread.join();
                consumerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
    }
}
