package com.rapleaf.lightweight_trie;

import java.util.HashMap;

import junit.framework.TestCase;

public class TestMutableStringRadixTreeMap extends TestCase {
  public void testSize() throws Exception {
    MutableStringRadixTreeMap<Integer> map = new MutableStringRadixTreeMap<Integer>();
    assertEquals(0, map.size());
    assertTrue(map.isEmpty());
    map.put("blah", 1);
    assertEquals(1, map.size());
    assertFalse(map.isEmpty());

    // TODO:
//    map.put("blah", 2);
//    assertEquals(1, map.size());
  }

  public void testGetPut() {
    MutableStringRadixTreeMap<Integer> map = new MutableStringRadixTreeMap<Integer>();
    assertFalse(map.containsKey("blah"));
    assertNull(map.get("blah"));
    map.put("blah", 1);
    assertTrue(map.containsKey("blah"));
    assertEquals(Integer.valueOf(1), map.get("blah"));
  }

  public void testClear() {
    MutableStringRadixTreeMap<Integer> map = new MutableStringRadixTreeMap<Integer>();
    map.put("blah", 1);
    map.put("blah2", 2);

    map.clear();
    assertNull(map.get("blah"));
    assertNull(map.get("blah2"));
    assertTrue(map.isEmpty());
  }

  public void testPutAll() {
    MutableStringRadixTreeMap<Integer> map = new MutableStringRadixTreeMap<Integer>();
    map.putAll(new HashMap<String, Integer>(){{put("blah1", 1); put("blah2", 2);}});
    assertEquals(2, map.size());
    assertEquals(Integer.valueOf(1), map.get("blah1"));
    assertEquals(Integer.valueOf(2), map.get("blah2"));
  }

  public void testLotsOfElements() {
    MutableStringRadixTreeMap<Integer> map = new MutableStringRadixTreeMap<Integer>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 50; i++) {
      sb.append("a");
      map.put(sb.toString(), i);
    }

    sb = new StringBuilder();
    for (int i = 0; i < 50; i++) {
      sb.append("a");
      assertEquals(Integer.valueOf(i), map.get(sb.toString()));
    }
  }

  public void testReplaceValue() {
    MutableStringRadixTreeMap<Integer> map = new MutableStringRadixTreeMap<Integer>();
    assertFalse(map.containsKey("blah"));
    assertNull(map.get("blah"));
    map.put("blah", 1);
    assertTrue(map.containsKey("blah"));
    assertEquals(Integer.valueOf(1), map.get("blah"));
    map.put("blah", 2);
    assertEquals(Integer.valueOf(2), map.get("blah"));
  }
}
