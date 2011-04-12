package com.rapleaf.lightweight_trie;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * An immutable String-keyed radix tree that is especially optimized for memory
 * use. To instantiate, you must pass in a MutableStringRadixTreeMap.
 * 
 * @param <V>
 */
public class ImmutableStringRadixTreeMap<V> implements Map<String, V> {
  private final AbstractNode<V> root;
  private final int size;

  public ImmutableStringRadixTreeMap(StringRadixTreeMap mutableRadixTree) {
    root = optimize(mutableRadixTree.root);
    size = mutableRadixTree.size();
  }

  private AbstractNode<V> optimize(MutableNode<V> n) {
    if (n.getChildren() == null) {
      // this is a leaf node
      return new LeafNode<V>(n.getPrefix(), n.getValue());
    }

    if (n.getChildren().length == 1) {
      // this is a single-child node
      return new SingleChildNode<V>(n.getPrefix(), n.getValue(), optimize(n.getChildren()[0]));
    }

    // it's a multi-child node
    AbstractNode<V>[] optimizedChildren = new AbstractNode[n.getChildren().length];
    for (int i = 0; i < optimizedChildren.length; i++) {
      optimizedChildren[i] = optimize(n.getChildren()[i]);
    }
    return new MultiChildNode<V>(n.getPrefix(), n.getValue(), optimizedChildren);
  }

  @Override
  public boolean containsKey(Object arg0) {
    return get(arg0) != null;
  }

  @Override
  public boolean containsValue(Object arg0) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<java.util.Map.Entry<String, V>> entrySet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public V get(Object key) {
    if (key instanceof String) {
      String skey = (String) key;
      return root.get(skey.toCharArray(), 0);
    }
    throw new IllegalArgumentException("Keys must be of type String");
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public Set<String> keySet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Collection<V> values() {
    throw new UnsupportedOperationException();
  }

  //
  // unsupported operations
  //

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public V remove(Object arg0) {
    throw new UnsupportedOperationException();
  }

  @Override
  public V put(String arg0, V arg1) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void putAll(Map<? extends String, ? extends V> arg0) {
    throw new UnsupportedOperationException();
  }
}
