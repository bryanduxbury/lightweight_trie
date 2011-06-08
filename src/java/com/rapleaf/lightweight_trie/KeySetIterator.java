/**
 * 
 */
package com.rapleaf.lightweight_trie;

import java.util.Iterator;

final class KeySetIterator<V> implements Iterator<String> {
  private final EntrySetIterator<V> iterator;

  public KeySetIterator(AbstractNode<V> root) {
    iterator = new EntrySetIterator<V>(root);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public String next() {
    return iterator.next().getKey();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }
}