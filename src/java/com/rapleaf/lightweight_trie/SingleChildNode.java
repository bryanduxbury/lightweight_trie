package com.rapleaf.lightweight_trie;

public class SingleChildNode<V> extends AbstractNode<V> {

  private final char[] prefix;
  private final AbstractNode<V> child;

  protected SingleChildNode(char[] prefix, V value, AbstractNode<V> child) {
    super(value);
    this.prefix = prefix;
    this.child = child;
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
    int commonLength = Utils.getCommonLength(toInsert, startOffset, prefix);
    if (commonLength > 0) {
      return child.get(toInsert, startOffset + commonLength);
    }
    return null;
  }
}
