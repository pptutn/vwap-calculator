package org.tranp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tranp.config.AppConfig;

public class Main {
    public static void main(String[] args) {
        // Initialize Spring context using the configuration class
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            System.out.println("VWAP Calculator Application Started...");
            Thread.currentThread().join();
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}