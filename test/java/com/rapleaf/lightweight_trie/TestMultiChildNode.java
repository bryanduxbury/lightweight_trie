package com.rapleaf.lightweight_trie;

import junit.framework.TestCase;

public class TestMultiChildNode extends TestCase {
  public void testIt() throws Exception {
    Object value1 = new Object();
    LeafNode l1 = new LeafNode("ab".toCharArray(), value1);
    Object value2 = new Object();
    LeafNode l2 = new LeafNode("cd".toCharArray(), value2);
    MultiChildNode n = new MultiChildNode("".toCharArray(), null, new AbstractNode[]{l1, l2});

    assertNull(n.get("a".toCharArray(), 0));
    assertNull(n.get("abc".toCharArray(), 0));
    assertNull(n.get("fff".toCharArray(), 0));
    assertEquals(value1, n.get("ab".toCharArray(), 0));
    assertEquals(value2, n.get("cd".toCharArray(), 0));
  }
}
