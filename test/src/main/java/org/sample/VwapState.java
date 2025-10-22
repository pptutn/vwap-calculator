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
    PriceTickPool pool = new PriceTickPool(100_000_000);
    Queue<MutablePriceTick> queue = new ArrayDeque<>();
    calculator = new VwapCalculator(pool, queue);
    tick = new MutablePriceTick();
    LOG.info("Tick before {}", tick);
    MarketDataGenerator gen = new MarketDataGenerator();
    Parser.parseEntry(gen.generateMarketDataEntryForSymbol(CurrencyPair.AUDUSD), tick);
    LOG.info("Tick after {}", tick);
  }

  @TearDown(Level.Trial)
  public void tearDown() {
    tick.reset();
  }
}

