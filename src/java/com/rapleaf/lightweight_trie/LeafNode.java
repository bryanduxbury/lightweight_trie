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

import java.util.Set;

/**
 * Node implementation that doesn't allow for children (to save bytes)
 * 
 * @param <V>
 */
final class LeafNode<V> extends AbstractNode<V> {
  private final char[] prefix;

  public LeafNode(char[] prefix, V value) {
    super(value);
    this.prefix = prefix;
  }

  @Override
  public AbstractNode<V>[] getChildren() {
    return null;
  }

  @Override
  public char[] getPrefix() {
    return prefix;
  }

  @Override
  public V get(char[] toInsert, int startOffset) {
    // if we got here, then we definitely didn't find it.
    return null;
  }

  @Override
  public void getPartialMatches(Set<String> partialMatches, char[] searchArr, int searchArrOffset) {
    // empty, because all the matching should occur a level higher
  }

  @Override
  public char getPrefixFirst() {
    return prefix[0];
  }
}
