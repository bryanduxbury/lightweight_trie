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

public class TestSingleChildNode extends TestCase {
  public void testIt() throws Exception {
    AbstractNode<Integer> child = new LeafNode<Integer>("abc".toCharArray(), 7);
    SingleChildNode<Integer> node = new SingleChildNode<Integer>("".toCharArray(), 5, child);
    assertEquals(Integer.valueOf(7), node.get("abc".toCharArray(), 0));
    assertNull(node.get("abd".toCharArray(), 0));
    assertNull(node.get("ab".toCharArray(), 0));
  }
}
