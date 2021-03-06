package com.luxoft.logeek.benchmark.string.maps;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class HashMapVsEnumMapIterationBenchmark {

  @Benchmark
  @SuppressWarnings("ALL")
  public void hashMap(Data data, Blackhole bh) {
    Map<String, Integer> map = data.hashMap;
    Set<String> keys = map.keySet();
    for (String key : keys) {
      bh.consume(map.get(key));
    }
  }

  @Benchmark
  @SuppressWarnings("ALL")
  public void enumMap(Data data, Blackhole bh) {
    Map<ConstantsEnum, Integer> map = data.enumMap;
    Set<ConstantsEnum> keys = map.keySet();
    for (ConstantsEnum key : keys) {
      bh.consume(map.get(key));
    }
  }

  @State(Scope.Thread)
  public static class Data {
    private HashMap<String, Integer> hashMap;
    private EnumMap<ConstantsEnum, Integer> enumMap;

    @Setup
    @SuppressWarnings("ALL")
    public void setup() {
      var hashMap = new HashMap<String, Integer>();
      hashMap.put(Constants.MarginLeft, 1);
      hashMap.put(Constants.MarginRight, 2);
      hashMap.put(Constants.MarginTop, 3);
      hashMap.put(Constants.MarginBottom, 4);

      var enumMap = new EnumMap<ConstantsEnum, Integer>(ConstantsEnum.class);
      enumMap.put(ConstantsEnum.MarginLeft, 1);
      enumMap.put(ConstantsEnum.MarginRight, 2);
      enumMap.put(ConstantsEnum.MarginTop, 3);
      enumMap.put(ConstantsEnum.MarginBottom, 4);

      this.hashMap = hashMap;
      this.enumMap = enumMap;
    }
  }
}
