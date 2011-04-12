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

public class TestImmutableStringRadixTreeMap extends TestCase {
  public void testSize() throws Exception {
    StringRadixTreeMap<Integer> mmap = new StringRadixTreeMap<Integer>();
    mmap.put("blah", 1);

    ImmutableStringRadixTreeMap<Integer> map = new ImmutableStringRadixTreeMap<Integer>(mmap);
    assertEquals(1, map.size());
    assertFalse(map.isEmpty());
  }

  public void testGetPut() {
    StringRadixTreeMap<Integer> mmap = new StringRadixTreeMap<Integer>();
    mmap.put("blah", 1);

    ImmutableStringRadixTreeMap<Integer> map = new ImmutableStringRadixTreeMap<Integer>(mmap);
    assertEquals(Integer.valueOf(1), map.get("blah"));
  }

  public void testLotsOfElements() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
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
