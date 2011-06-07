/**
 *  Copyright 2011 Rapleaf
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.rapleaf.lightweight_trie;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A String-keyed Map implementation backed by a radix tree. This implementation
 * is substantialy more memory efficient than alternatives like HashMap.
 * 
 * Null keys are not allowed in this Map implementation.
 * 
 * @param <V>
 */
public class StringRadixTreeMap<V> implements Map<String, V> {
  MutableNode<V> root = new MutableNode<V>("".toCharArray(), 0, 0, null);
  private int size = 0;

  @Override
  public void clear() {
    root = new MutableNode<V>("".toCharArray(), 0, 0, null);
    size = 0;
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
  /**
   * Key may not be null.
   */
  public V put(String key, V value) {
    if (key == null) {
      throw new NullPointerException(getClass().getSimpleName() + " does not allow null keys!");
    }
    size++;
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
