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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * A String-keyed Map implementation backed by a radix tree. This implementation
 * is substantially more memory efficient than alternatives like HashMap.
 * 
 * Null keys are not allowed in this Map implementation.
 * 
 * @param <V>
 */
public class StringRadixTreeMap<V> implements Map<String, V> {
  private final class KeySetIterator implements Iterator<String> {
    private final EntrySetIterator iterator;

    public KeySetIterator() {
      iterator = new EntrySetIterator();
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

  private final class KeySet implements Set<String> {

    @Override
    public boolean add(String arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends String> arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object arg0) {
      return StringRadixTreeMap.this.containsKey(arg0);
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
      return StringRadixTreeMap.this.isEmpty();
    }

    @Override
    public Iterator<String> iterator() {
      return new KeySetIterator();
    }

    @Override
    public boolean remove(Object arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
      return StringRadixTreeMap.this.size();
    }

    @Override
    public Object[] toArray() {
      throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
      throw new UnsupportedOperationException();
    }
  }

  private final class EntrySetIterator implements Iterator<Entry<String, V>> {
    private final class IteratorEntry implements Entry<String, V> {
      private final String key;
      private final MutableNode<V> nextNode;

      private IteratorEntry(String key, MutableNode<V> nextNode) {
        this.key = key;
        this.nextNode = nextNode;
      }

      @Override
      public String getKey() {
        return key;
      }

      @Override
      public V getValue() {
        return nextNode.getValue();
      }

      @Override
      public V setValue(V value) {
        throw new UnsupportedOperationException();
      }
    }

    private LinkedList<Queue<MutableNode<V>>> search = new LinkedList<Queue<MutableNode<V>>>();
    private LinkedList<MutableNode<V>> path = new LinkedList<MutableNode<V>>();
    private Entry<String, V> cachedNext;

    public EntrySetIterator() {
      Queue<MutableNode<V>> q = new LinkedList<MutableNode<V>>();
      q.add(root);
      search.add(q);
      // special placeholder.
      path.push(root);
      next();
    }

    @Override
    public boolean hasNext() {
      return cachedNext != null;
    }

    @Override
    public Entry<String, V> next() {
      // this method is going to return the current "next" item, then traverse
      // ahead to the next "next" item.
      java.util.Map.Entry<String, V> last = cachedNext;
      // blank out the "cached" next.
      cachedNext = null;

      while (! search.isEmpty()) {
        // see where we left off in our search
        final Queue<MutableNode<V>> lowestQ = search.getLast();
        // if there are no more items to investigate at the lowest level, then
        // go back up a level.
        if (lowestQ.isEmpty()) {
          search.removeLast();
          path.pop();
          continue;
        }

        // there's at least one item left in this search level.
        final MutableNode<V> nextNode = lowestQ.remove();

        // push the new node onto the path. we need this to reconstruct the
        // keys.
        path.push(nextNode);

        // enqueue all the new node's children to be searched
        final LinkedList<MutableNode<V>> nextQ = new LinkedList<MutableNode<V>>();
        if (nextNode.getChildren() != null) {
          for (int i = 0; i < nextNode.getChildren().length; i++) {
            nextQ.add(nextNode.getChildren()[i]);
          }
        }
        search.addLast(nextQ);

        // check if this is a node with a value. if so, then we've found the
        // "next" value, and we should cache it and exit.
        if (nextNode.getValue() != null) {
          // we need to reconstruct the actual string key from all the nodes
          // above us in the path.

          StringBuilder sb = new StringBuilder();
          for (int i = path.size() - 2; i >= 0; i--) {
            sb.append(path.get(i).getPrefix());
          }
          final String key = sb.toString();

          // now that we have the key and the value, store the "next" entry so
          // that the next "next" call can return it.
          cachedNext = new IteratorEntry(key, nextNode);

          break;
        }
      }

      // if we reached here by traversing the entire trie, then cachedNext will
      // be null, which is perfectly fine - it just mean's we're done iterating.
      // hooray!

      // finally, return the last item we iterated to.
      return last;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private class EntrySet implements Set<Entry<String, V>> {
    @Override
    public Iterator<java.util.Map.Entry<String, V>> iterator() {
      return new EntrySetIterator();
    }

    @Override
    public boolean add(java.util.Map.Entry<String, V> arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends java.util.Map.Entry<String, V>> arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
      return StringRadixTreeMap.this.isEmpty();
    }

    @Override
    public boolean remove(Object arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
      throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
      return StringRadixTreeMap.this.size();
    }

    @Override
    public Object[] toArray() {
      throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
      throw new UnsupportedOperationException();
    }
  }

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
      final String string = (String) key;
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
    return new EntrySet();
  }

  @Override
  public V get(Object key) {
    if (key instanceof String) {
      final String string = (String) key;
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
    return new KeySet();
  }

  @Override
  /**
   * Key may not be null.
   */
  public V put(String key, V value) {
    if (key == null) {
      throw new NullPointerException(getClass().getSimpleName()
          + " does not allow null keys!");
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
    ArrayList<V> l = new ArrayList<V>(size());
    for (Map.Entry<String, V> entry : entrySet()) {
      l.add(entry.getValue());
    }
    return l;
  }
}
