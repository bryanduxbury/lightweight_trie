package com.rapleaf.lightweight_trie;

import java.util.Set;

public class ContiguousRangeNode<V> extends AbstractNode<V> {

  private final char prefix;
  private final AbstractNode<V>[] children;
  private final char low;
  private final char high;

  protected ContiguousRangeNode(char prefix, V value, AbstractNode<V>[] children) {
    super(value);
    this.prefix = prefix;
    this.children = children;
    low = children[0].getPrefixFirst();
    high = children[children.length-1].getPrefixFirst();
  }

  @Override
  public V get(char[] searchArr, int startOffset) {
    // if the first char of the search key is within the range of prefixes this
    // node contains, then we are able to jump to exactly that node and
    // then do further comparison.
    
    final char keyFirst = searchArr[startOffset];
    if (keyFirst >= low && keyFirst <= high) {
      final AbstractNode<V> childNode = children[keyFirst - low];

      // a quick check we can do is to see if this current child prefix is
      // longer than what's left in the search key, as that would guarantee a
      // non-match.
      // TODO: evaluate if this is actually beneficial
      final char[] childPrefix = childNode.getPrefix();
      if (childPrefix.length > searchArr.length - startOffset) {
        return null;
      }

      // now let's check if the next bit of searchArr matches the prefix. we
      // already checked the first char, so we can start with 1. note that this
      // will short-circuit itself right away if the child prefix is only 1
      // char.
      for (int i = 1; i < childPrefix.length; i++) {
        if (childPrefix[i] != searchArr[startOffset + i]) {
          return null;
        }
      }

      // if we reach this point, then we've got a child match. 
      // if there is no more searchArr left, then return the value we found.
      if (searchArr.length == childPrefix.length + startOffset) {
        return childNode.value;
      }

      // if there's more then we need to recurse.
      return childNode.get(searchArr, startOffset + childPrefix.length);
    }

    return null;
  }

  @Override
  public AbstractNode<V>[] getChildren() {
    return children;
  }

  @Override
  public void getPartialMatches(Set<String> partialMatches, char[] searchArr, int searchArrOffset) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }

  @Override
  public char[] getPrefix() {
    return new char[]{prefix};
  }

  @Override
  public char getPrefixFirst() {
    return prefix;
  }

}
