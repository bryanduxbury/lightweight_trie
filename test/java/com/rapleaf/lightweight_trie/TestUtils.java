package com.rapleaf.lightweight_trie;

import junit.framework.TestCase;

public class TestUtils extends TestCase {
  public void testIt() throws Exception {
    assertEquals(0, Utils.getCommonLength("abc".toCharArray(), 0, "bcd".toCharArray()));
    assertEquals(2, Utils.getCommonLength("abc".toCharArray(), 1, "bcd".toCharArray()));
    assertEquals(2, Utils.getCommonLength("abc".toCharArray(), 1, "bc".toCharArray()));
    assertEquals(0, Utils.getCommonLength("abc".toCharArray(), 0, "bc".toCharArray()));
  }
}
