/*
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
import java.util.Map;
import java.util.Set;

abstract class AbstractRadixTreeMap<V> implements Map<String, V> {
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
