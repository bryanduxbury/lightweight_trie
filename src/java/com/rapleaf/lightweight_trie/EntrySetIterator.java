/**
 * 
 */
package com.rapleaf.lightweight_trie;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map.Entry;

final class EntrySetIterator<V> implements Iterator<Entry<String, V>> {
  private final class IteratorEntry implements Entry<String, V> {
    private final String key;
    private final AbstractNode<V> nextNode;

    private IteratorEntry(String key, AbstractNode<V> nextNode) {
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

  private LinkedList<Queue<AbstractNode<V>>> search = new LinkedList<Queue<AbstractNode<V>>>();
  private LinkedList<AbstractNode<V>> path = new LinkedList<AbstractNode<V>>();
  private Entry<String, V> cachedNext;

  public EntrySetIterator(AbstractNode<V> root) {
    Queue<AbstractNode<V>> q = new LinkedList<AbstractNode<V>>();
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
      final Queue<AbstractNode<V>> lowestQ = search.getLast();
      // if there are no more items to investigate at the lowest level, then
      // go back up a level.
      if (lowestQ.isEmpty()) {
        search.removeLast();
        path.pop();
        continue;
      }

      // there's at least one item left in this search level.
      final AbstractNode<V> nextNode = lowestQ.remove();

      // push the new node onto the path. we need this to reconstruct the
      // keys.
      path.push(nextNode);

      // enqueue all the new node's children to be searched
      final LinkedList<AbstractNode<V>> nextQ = new LinkedList<AbstractNode<V>>();
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