/*
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

import java.util.Iterator;
import java.util.Map;

final class KeySet<V> extends UnmodifiableAbstractSet<String> {
  private final Map<String, V> backingMap;
  private final AbstractNode<V> root;

  public KeySet(Map<String, V> backingMap, AbstractNode<V> root) {
    this.backingMap = backingMap;
    this.root = root;
  }

  @Override
  public boolean contains(Object obj) {
    return backingMap.containsKey(obj);
  }

  @Override
  public boolean isEmpty() {
    return backingMap.isEmpty();
  }

  @Override
  public Iterator<String> iterator() {
    return new KeySetIterator<V>(root);
  }

  @Override
  public int size() {
    return backingMap.size();
  }
}