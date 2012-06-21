package com.rapleaf.lightweight_trie;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    long hashMapCount = 0;
    long trieCount = 0;
    long immutableTrieCount = 0;

    Map<String, Integer> hashMap = new HashMap<String, Integer>();
    StringRadixTreeMap<Integer> trieMap = new StringRadixTreeMap<Integer>();

    for (int i = 0; i < strings.size(); i++) {
      hashMap.put(strings.get(i), i);
      trieMap.put(strings.get(i), i);
    }

    ImmutableStringRadixTreeMap<Integer> optTrieMap = new ImmutableStringRadixTreeMap<Integer>(trieMap);

//    final List<String> nodeAnalysis = optTrieMap.getNodeAnalysis();
//    for (String s : nodeAnalysis) {
//      System.out.println(s);
//    }
//    System.exit(1);
    
    Collections.shuffle(strings, new Random(1));

    for (int trial = 0; trial < 50; trial++) {
      hashMapCount += perfTest("hashmap", hashMap, strings);
      trieCount += perfTest("trie", trieMap, strings);
      immutableTrieCount += perfTest("immutable trie", optTrieMap, strings);
    }

    System.out.println("hashmap\t" + hashMapCount);
    System.out.println("trie\t" + trieCount);
    System.out.println("i-trie\t" + immutableTrieCount);
  }

  private static long perfTest(String title, Map<String, Integer> map, List<String> strings) {
    System.gc();System.gc();System.gc();System.gc();
    long start = System.currentTimeMillis();
    for (int i = 0; i < strings.size(); i++) {
      final String s = strings.get(i);
      map.get(s);
    }
    long end = System.currentTimeMillis();
    return (end - start);
  }
}
