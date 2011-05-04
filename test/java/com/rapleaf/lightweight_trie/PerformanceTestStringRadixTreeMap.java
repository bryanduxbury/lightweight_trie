package com.rapleaf.lightweight_trie;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PerformanceTestStringRadixTreeMap {

  public static void main(String[] args) throws IOException {
    BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));

    List<String> strings = new ArrayList<String>();

    while (true) {
      String l = r.readLine();
      if (l == null) {
        break;
      }
      strings.add(l);
    }

    System.out.println("Read " + strings.size() + " strings.");

    StringRadixTreeMap<Boolean> map = new StringRadixTreeMap<Boolean>();

    // HashMap<String, Boolean> map = new HashMap<String, Boolean>();

    long start = System.currentTimeMillis();
    for (int i = 0; i < strings.size(); i++) {
      map.put(strings.get(i), Boolean.TRUE);
    }
    long end = System.currentTimeMillis();
    System.out.println("Took " + (end - start) + "ms to populate map.");

    System.out.println(map.size());

    start = System.currentTimeMillis();
    ImmutableStringRadixTreeMap<Boolean> immutableStringRadixTreeMap = new ImmutableStringRadixTreeMap<Boolean>(map);
    end = System.currentTimeMillis();
    System.out.println(immutableStringRadixTreeMap.size());
    System.out.println("Took " + (end - start) + "ms to convert to immutable.");
  }
}
