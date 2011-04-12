package com.rapleaf.lightweight_trie;

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
}
