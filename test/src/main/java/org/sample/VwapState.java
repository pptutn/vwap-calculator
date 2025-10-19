package org.sample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.annotations.*;
import org.tranp.MarketDataGenerator;
import org.tranp.data.MutablePriceTick;
import org.tranp.data.PriceTickPool;
import org.tranp.fx.CurrencyPair;
import org.tranp.util.Parser;
import org.tranp.vwap.VwapCalculator;

import java.util.ArrayDeque;
import java.util.Queue;

@State(Scope.Thread)
public class VwapState {
  private static final Logger LOG = LogManager.getLogger(VwapState.class);
  VwapCalculator calculator;
  MutablePriceTick tick;

  @Setup(Level.Trial)
  public void setup() {
    PriceTickPool pool = new PriceTickPool(2048);
    Queue<MutablePriceTick> queue = new ArrayDeque<>();
    calculator = new VwapCalculator(pool, queue);
    tick = new MutablePriceTick();
//    tick.price(100);  // manually assign dummy values
//    tick.qty(1_000_000);
//    tick.timestamp(System.currentTimeMillis());
    LOG.info("Tick before {}", tick);
    MarketDataGenerator gen = new MarketDataGenerator();
    Parser.parseEntry(gen.generateMarketDataEntryForSymbol(CurrencyPair.AUDUSD), tick);
    LOG.info("Tick after {}", tick);
  }
//  @Setup(Level.Trial)
//  public void setup() {
//    PriceTickPool pool = new PriceTickPool(2048);
//    Queue<MutablePriceTick> queue = new ArrayDeque<>();
//    calculator = new VwapCalculator(pool, queue);
//
//    // Create a realistic PriceTick
//    MarketDataGenerator gen = new MarketDataGenerator();
//    tick = new MutablePriceTick();
//    Parser.parseEntry(gen.generateMarketDataEntryForSymbol(CurrencyPair.AUDUSD), tick);
//  }

  @TearDown(Level.Trial)
  public void tearDown() {
    tick.reset();
  }
}

