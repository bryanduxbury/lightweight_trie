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

import java.util.Collection;
import java.util.Set;

abstract class UnmodifiableAbstractSet<E> implements Set<E> {
  @Override
  public boolean add(E arg0) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(Collection<? extends E> arg0) {
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
  public Object[] toArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T[] toArray(T[] arg0) {
    throw new UnsupportedOperationException();
  }
}
