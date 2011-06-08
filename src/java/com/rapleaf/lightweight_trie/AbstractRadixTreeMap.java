package com.rapleaf.lightweight_trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class AbstractRadixTreeMap<V> implements Map<String, V> {
  protected abstract AbstractNode<V> getRoot();

  @Override
  public final Set<java.util.Map.Entry<String, V>> entrySet() {
    return new EntrySet<V>(this, getRoot());
  }

  @Override
  public final Set<String> keySet() {
    return new KeySet<V>(this, getRoot());
  }

  @Override
  public final Collection<V> values() {
    ArrayList<V> l = new ArrayList<V>(size());
    for (Map.Entry<String, V> entry : entrySet()) {
      l.add(entry.getValue());
    }
    return l;
  }
}
