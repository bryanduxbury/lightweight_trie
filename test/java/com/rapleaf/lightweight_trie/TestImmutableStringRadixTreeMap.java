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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

  public void testEntrySet() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    map.put("blah", 1);
    map.put("blah2", 2);
    map.put("foo", 7);
    map.put("bar", 15);
    map.put("LONGGGG one", 250);
    ImmutableStringRadixTreeMap<Integer> imap = new ImmutableStringRadixTreeMap<Integer>(map);

    Map<String, Integer> otherMap = new HashMap<String, Integer>();
    for (Map.Entry<String, Integer> entry : imap.entrySet()) {
      otherMap.put(entry.getKey(), entry.getValue());
    }

    Map<String, Integer> expectedMap = new HashMap<String, Integer>() {
      {
        put("blah", 1);
        put("blah2", 2);
        put("foo", 7);
        put("bar", 15);
        put("LONGGGG one", 250);
      }
    };

    assertEquals(expectedMap, otherMap);
  }

  public void testKeySet() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    map.put("blah", 1);
    map.put("blah2", 2);
    map.put("foo", 7);
    map.put("bar", 15);
    map.put("LONGGGG one", 250);
    ImmutableStringRadixTreeMap<Integer> imap = new ImmutableStringRadixTreeMap<Integer>(map);

    Set<String> keys = new HashSet<String>();
    for (String k : imap.keySet()) {
      keys.add(k);
    }

    assertEquals(new HashSet<String>(Arrays.asList("blah", "blah2", "foo", "bar", "LONGGGG one")), keys);
  }

  public void testValues() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    map.put("blah", 1);
    map.put("blah2", 2);
    map.put("foo", 7);
    map.put("bar", 15);
    map.put("LONGGGG one", 250);
    ImmutableStringRadixTreeMap<Integer> imap = new ImmutableStringRadixTreeMap<Integer>(map);

    assertEquals(new HashSet(Arrays.asList(1, 2, 7, 15, 250)), new HashSet(imap.values()));
  }

  public void testGetPartialMatches() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    map.put("a", 1);
    map.put("ab", 2);
    map.put("abcd", 3);
    map.put("abcde", 4);
    map.put("ac", 5);
    ImmutableStringRadixTreeMap<Integer> imap = new ImmutableStringRadixTreeMap<Integer>(map);

    assertEquals(new TreeSet(Arrays.asList("a", "ab", "abcd", "abcde")), new TreeSet(imap.getPartialMatches("abcdef")));
    assertEquals(new TreeSet(Arrays.asList("a")), new TreeSet(imap.getPartialMatches("a")));
    assertEquals(new TreeSet(Arrays.asList("a")), new TreeSet(imap.getPartialMatches("azzzzzzzzz")));
    assertEquals(new TreeSet(Arrays.asList("a", "ac")), new TreeSet(imap.getPartialMatches("accc")));
    assertEquals(new TreeSet(Arrays.asList("a", "ab")), new TreeSet(imap.getPartialMatches("abc")));
  }
}
