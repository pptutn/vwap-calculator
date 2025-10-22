package org.sample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.annotations.Benchmark;

public class VwapCalculatorBenchmark {
//  private static final Logger LOG = LogManager.getLogger(VwapCalculatorBenchmark.class);

  @Benchmark
  public void benchmarkAccept(VwapState state) {

    state.calculator.accept(state.tick);
  }

  @Benchmark
  public double benchmarkVwap(VwapState state) {
    return state.calculator.vwap();
  }

  @Benchmark
  public void benchmarkRemoveOldTicks(VwapState state) {
    state.calculator.removeOldTicks();
  }
}
