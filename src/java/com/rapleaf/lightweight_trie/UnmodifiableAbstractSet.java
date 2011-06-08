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
