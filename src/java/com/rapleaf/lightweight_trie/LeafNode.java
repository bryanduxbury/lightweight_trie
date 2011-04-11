package com.rapleaf.lightweight_trie;

/**
 * Node implementation that doesn't allow for children (to save bytes)
 * 
 * @param <V>
 */
public class LeafNode<V> extends AbstractNode<V> {

  protected LeafNode(V value) {
    super(value);
  }


  @Override
  public AbstractNode<V>[] getChildren() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public char[] getPrefix() {
    // TODO Auto-generated method stub
    return null;
  }

}
