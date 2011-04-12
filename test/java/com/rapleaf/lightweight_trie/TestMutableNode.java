/**
 *  Copyright 2011 Rapleaf
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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

  public void testSplit() {
    MutableNode root = new MutableNode("".toCharArray(), 0, 0, null);
    root.insert("brittany".toCharArray(), 0, null);
    root.insert("brit".toCharArray(), 0, null);
    root.insert("bryan".toCharArray(), 0, null);

    // <null>
    // -> br
    //    -> it
    //       -> tany
    //    -> yan

    assertEquals(1, root.getChildren().length);
    final MutableNode br = root.getChildren()[0];
    assertEquals("br", new String(br.getPrefix()));
    assertEquals(2, br.getChildren().length);
    MutableNode it = br.getChildren()[0];
    assertEquals("it", new String(it.getPrefix()));
    assertEquals(1, it.getChildren().length);
    MutableNode tany = it.getChildren()[0];
    assertEquals("tany", new String(tany.getPrefix()));
    assertNull(tany.getChildren());

    MutableNode yan = br.getChildren()[1];
    assertEquals("yan", new String(yan.getPrefix()));
    assertNull(yan.getChildren());
  }
}
