package com.rapleaf.lightweight_trie;

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
}
