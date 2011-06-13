package com.rapleaf.lightweight_trie;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformanceTestGet {
  public static void main(String[] args) throws Exception {
    BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));

    List<String> strings = new ArrayList<String>();

    while (true) {
      String l = r.readLine();
      if (l == null) {
        break;
      }
      strings.add(l);
    }

    Map<String, Integer> hashMap = new HashMap<String, Integer>();
    StringRadixTreeMap<Integer> trieMap = new StringRadixTreeMap<Integer>();

    for (int i = 0; i < strings.size(); i++) {
      hashMap.put(strings.get(i), i);
      trieMap.put(strings.get(i), i);
    }

    Map<String, Integer> optTrieMap = new ImmutableStringRadixTreeMap<Integer>(trieMap);

    Collections.shuffle(strings);

    perfTest("hashmap", hashMap, strings);
    perfTest("trie", trieMap, strings);
    perfTest("immutable trie", optTrieMap, strings);
  }

  private static void perfTest(String title, Map<String, Integer> map, List<String> strings) {
    long start = System.currentTimeMillis();
    for (int i = 0; i < strings.size(); i++) {
      map.get(strings.get(i));
    }
    long end = System.currentTimeMillis();
    System.out.println(title + ": " + (end - start) + "ms");
  }
}
