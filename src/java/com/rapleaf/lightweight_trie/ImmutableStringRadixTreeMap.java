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

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An immutable String-keyed radix tree that is especially optimized for memory
 * use. To instantiate, you must pass in a MutableStringRadixTreeMap.
 * 
 * @param <V>
 */
public class ImmutableStringRadixTreeMap<V> extends AbstractRadixTreeMap<V> {
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
    Arrays.sort(optimizedChildren, new Comparator<AbstractNode<V>>(){
      @Override
      public int compare(AbstractNode<V> arg0, AbstractNode<V> arg1) {
        char[] prefix0 = arg0.getPrefix();
        char[] prefix1 = arg1.getPrefix();
        for (int i = 0; i < Math.min(prefix0.length, prefix1.length); i++) {
          if (prefix0[i] < prefix1[i]) {
            return -1;
          } else if (prefix0[i] > prefix1[i]) {
            return 1;
          }
        }
        throw new IllegalStateException("Nodes " + arg0 + " and " + arg1 + " have matching prefixes!");
      }
    });
    if (n.getPrefix().length == 1) {
      return new SingleLengthMultiChildNode<V>(n.getPrefix()[0], n.getValue(), optimizedChildren);
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
  public int size() {
    return size;
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

  @Override
  protected AbstractNode<V> getRoot() {
    return root;
  }

  public Set<String> getPartialMatches(String string) {
    Set<String> partialMatches = new HashSet<String>();
    root.getPartialMatches(partialMatches, string.toCharArray(), 0);
    return partialMatches;
  }
}
