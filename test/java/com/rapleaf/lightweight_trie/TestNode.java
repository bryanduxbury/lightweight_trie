package com.rapleaf.analysis_lib.radix_tree;

import com.rapleaf.analysis_lib.AnalysisLibTestCase;

public class TestNode extends AnalysisLibTestCase {
  private static final Object MARKER_OBJECT = new Object();
  private static final Object MARKER_OBJECT2 = new Object();
  private static final Object MARKER_OBJECT3 = new Object();

  public void testIt() {
    Node n = new Node("".toCharArray(), 0, 0, MARKER_OBJECT);
    n.insert("xyz".toCharArray(), 0, MARKER_OBJECT2);
    assertEquals(1, n.getChildren().length);
    n.insert("xya".toCharArray(), 0, MARKER_OBJECT3);
    assertEquals(1, n.getChildren().length);

    assertEquals(null, n.get("xy".toCharArray(), 0));
    assertEquals(MARKER_OBJECT2, n.get("xyz".toCharArray(), 0));
    assertEquals(MARKER_OBJECT3, n.get("xya".toCharArray(), 0));
  }
}
