package com.rapleaf.analysis_lib.radix_tree;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class StringRadixTree<V> implements Map<String, V> {

  private Node<V> root = new Node<V>("".toCharArray(), 0, 0, null);
  private int size = 0;

  @Override
  public void clear() {
    root = new Node<V>("".toCharArray(), 0, 0, null);
  }

  @Override
  public boolean containsKey(Object key) {
    if (key instanceof String) {
      final String string = (String)key;
      return root.get(string.toCharArray(), 0) != null;
    }
    throw new IllegalArgumentException("get() cannot be called with non-String arguments!");
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
      final String string = (String)key;
      return root.get(string.toCharArray(), 0);
    }
    throw new IllegalArgumentException("get() cannot be called with non-String arguments!");
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
  public V put(String key, V value) {
    root.insert(key.toCharArray(), 0, value);
    return value;
  }

  @Override
  public void putAll(Map<? extends String, ? extends V> arg0) {
    for (Map.Entry<? extends String, ? extends V> entry : arg0.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public V remove(Object arg0) {
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
}
