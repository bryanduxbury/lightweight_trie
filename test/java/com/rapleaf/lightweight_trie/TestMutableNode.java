package com.rapleaf.lightweight_trie;

import junit.framework.TestCase;

public class TestMutableNode extends TestCase {
  private static final Object MARKER_OBJECT = new Object();
  private static final Object MARKER_OBJECT2 = new Object();
  private static final Object MARKER_OBJECT3 = new Object();

  public void testIt() {
    MutableNode n = new MutableNode("".toCharArray(), 0, 0, MARKER_OBJECT);
    n.insert("xyz".toCharArray(), 0, MARKER_OBJECT2);
    assertEquals(1, n.getChildren().length);
    n.insert("xya".toCharArray(), 0, MARKER_OBJECT3);
    assertEquals(1, n.getChildren().length);

    assertEquals(null, n.get("xy".toCharArray(), 0));
    assertEquals(MARKER_OBJECT2, n.get("xyz".toCharArray(), 0));
    assertEquals(MARKER_OBJECT3, n.get("xya".toCharArray(), 0));
  }
}
