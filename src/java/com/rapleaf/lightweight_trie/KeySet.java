/**
 * 
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
  public boolean contains(Object arg0) {
    return backingMap.containsKey(arg0);
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