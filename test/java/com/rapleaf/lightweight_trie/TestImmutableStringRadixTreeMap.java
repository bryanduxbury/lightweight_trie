package com.rapleaf.lightweight_trie;

import junit.framework.TestCase;

public class TestImmutableStringRadixTreeMap extends TestCase {
  public void testSize() throws Exception {
    MutableStringRadixTreeMap<Integer> mmap = new MutableStringRadixTreeMap<Integer>();
    mmap.put("blah", 1);

    ImmutableStringRadixTreeMap<Integer> map = new ImmutableStringRadixTreeMap<Integer>(mmap);
    assertEquals(1, map.size());
    assertFalse(map.isEmpty());
  }

  public void testGetPut() {
    MutableStringRadixTreeMap<Integer> mmap = new MutableStringRadixTreeMap<Integer>();
    mmap.put("blah", 1);

    ImmutableStringRadixTreeMap<Integer> map = new ImmutableStringRadixTreeMap<Integer>(mmap);
    assertEquals(Integer.valueOf(1), map.get("blah"));
  }

  public void testLotsOfElements() {
    MutableStringRadixTreeMap<Integer> map = new MutableStringRadixTreeMap<Integer>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 50; i++) {
      sb.append("a");
      map.put(sb.toString(), i);
    }

    ImmutableStringRadixTreeMap<Integer> imm = new ImmutableStringRadixTreeMap<Integer>(map);

    sb = new StringBuilder();
    for (int i = 0; i < 50; i++) {
      sb.append("a");
      assertEquals(Integer.valueOf(i), imm.get(sb.toString()));
    }
  }

}
