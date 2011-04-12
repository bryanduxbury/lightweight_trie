package com.rapleaf.lightweight_trie;

abstract class AbstractNode<V> {
  protected V value;

  protected AbstractNode(V value) {
    this.value = value;
  }

  public abstract V get(char[] toInsert, int startOffset);

  public abstract AbstractNode<V>[] getChildren();

  public abstract char[] getPrefix();
}
