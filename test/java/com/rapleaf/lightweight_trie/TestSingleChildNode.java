package com.rapleaf.lightweight_trie;

import junit.framework.TestCase;

public class TestSingleChildNode extends TestCase {
  public void testIt() throws Exception {
    AbstractNode<Integer> child = new LeafNode<Integer>("abc".toCharArray(), 7);
    SingleChildNode<Integer> node = new SingleChildNode<Integer>("".toCharArray(), 5, child);
    assertEquals(Integer.valueOf(7), node.get("abc".toCharArray(), 0));
    assertNull(node.get("abd".toCharArray(), 0));
    assertNull(node.get("ab".toCharArray(), 0));
  }
}
