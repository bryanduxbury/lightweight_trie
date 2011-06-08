/**
 * 
 */
package com.rapleaf.lightweight_trie;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

final class EntrySet<V> extends UnmodifiableAbstractSet<Entry<String, V>> {
  private final Map<String, V> backingMap;
  private final AbstractNode<V> root2;

  public EntrySet(Map<String, V> backingMap, AbstractNode<V> root) {
    this.backingMap = backingMap;
    this.root2 = root;
  }

  @Override
  public Iterator<java.util.Map.Entry<String, V>> iterator() {
    return new EntrySetIterator(root2);
  }

  @Override
  public boolean isEmpty() {
    return backingMap.isEmpty();
  }

  @Override
  public int size() {
    return backingMap.size();
  }
}