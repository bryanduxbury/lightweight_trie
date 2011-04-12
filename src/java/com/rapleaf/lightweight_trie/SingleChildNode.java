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
    int commonLength = Utils.getCommonLength(toInsert, startOffset, child.getPrefix());
    if (commonLength > 0) {
      if (commonLength == toInsert.length - startOffset && commonLength == child.getPrefix().length) {
        return child.value;
      }
      return child.get(toInsert, startOffset + commonLength);
    }
    return null;
  }
}
