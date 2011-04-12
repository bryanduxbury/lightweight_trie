package com.rapleaf.lightweight_trie;

public abstract class AbstractMultiChildNode<V> extends AbstractNode<V> {

  private final AbstractNode<V>[] children;

  protected AbstractMultiChildNode(V value, AbstractNode<V>[] children) {
    super(value);
    this.children = children;
  }

  @Override
  public final V get(char[] searchArr, int startOffset) {
    // there are existing children. scan them to see if there are any with
    // matching prefixes
    for (int i = 0; i < children.length; i++) {
      AbstractNode<V> childNode = children[i];
      int commonLength = Utils.getCommonLength(searchArr, startOffset, childNode.getPrefix());

      // if there was any match...
      if (commonLength > 0) {
        // it could have been an exact match, which would indicate we found it
        if (commonLength == searchArr.length - startOffset && childNode.getPrefix().length == commonLength) {
          // it's an exact match!
          return childNode.value;
        }
        // it could be a partial match, but short...
        if (commonLength < childNode.getPrefix().length) {
          // the child node and the search string share a prefix, but the child
          // node is longer than the match length. the search string can't be in
          // the tree.
          return null;
        }
        // the search string is longer than the child's prefix, so we need to
        // recurse
        return childNode.get(searchArr, startOffset + commonLength);
      }
    }

    // didn't find a sub-match
    return null;
  }

  @Override
  public AbstractNode<V>[] getChildren() {
    return children;
  }
}
