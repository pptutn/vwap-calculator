package org.tranp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tranp.config.AppConfig;

public class Main {
    public static void main(String[] args) {
        final Logger LOG = LogManager.getLogger(Main.class);
        // Initialize Spring context using the configuration class
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
//            System.out.println("VWAP Calculator Application Started...");
            LOG.info("VWAP Calculator Application Started...");
            Thread.currentThread().join();
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}