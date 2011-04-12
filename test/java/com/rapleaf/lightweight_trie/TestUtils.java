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

public class TestUtils extends TestCase {
  public void testIt() throws Exception {
    assertEquals(0, Utils.getCommonLength("abc".toCharArray(), 0, "bcd".toCharArray()));
    assertEquals(2, Utils.getCommonLength("abc".toCharArray(), 1, "bcd".toCharArray()));
    assertEquals(2, Utils.getCommonLength("abc".toCharArray(), 1, "bc".toCharArray()));
    assertEquals(0, Utils.getCommonLength("abc".toCharArray(), 0, "bc".toCharArray()));
  }
}
