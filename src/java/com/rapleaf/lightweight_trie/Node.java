package com.rapleaf.lightweight_trie;

import java.util.Arrays;


public class Node<V> {
  private final char[] chars;

  private V value;
  private Node[] children;

  public Node(char[] origChars, int off, int len, V value) {
    this.value = value;
    chars = new char[len];
    System.arraycopy(origChars, off, chars, 0, len);
  }

  public void insert(char[] origChars, int off, V value) {
    // precondition: origChars and this node share a common prefix
    // off is positioned at the first character that does not match this node's
    // prefix.

    // if there are no children, then add a new single child with all of the chars.
    if (children == null) {
      children = new Node[] {
        new Node(origChars, off, origChars.length - off, value)
      };
      return;
    }

    // there are existing children. scan them to see if there are any with
    // matching prefixes
    for (int i = 0; i < children.length; i++) {
      Node childNode = children[i];
      int commonLength = getCommonLength(origChars, off, childNode.chars);
      if (commonLength == 0) {
        // no match. continue on.
        continue;
      }

      if (commonLength < childNode.chars.length) {
        // the child node and the new string share a prefix, but the child node
        // is currently longer than the match length. we need to split the child
        // node.

        Node replacement = new Node(childNode.chars, 0, commonLength, null);
        replacement.children = new Node[] {
            new Node(childNode.chars, commonLength, childNode.chars.length - commonLength, childNode.value)
        };
        children[i] = replacement;
        replacement.insert(origChars, off + commonLength, value);
        return;
      }

      if (commonLength == origChars.length - off && commonLength == childNode.chars.length) {
        // it's an exact match. replace.
        childNode.value = value;
        return;
      }

      // we match on the whole length. recursively insert.
      childNode.insert(origChars, off + commonLength, value);
      return;
    }

    // if we get here, then we never found *any* match. insert a new child node.
    Node[] oldChildren = children;
    children = new Node[oldChildren.length + 1];
    System.arraycopy(oldChildren, 0, children, 0, oldChildren.length);
    children[children.length - 1] = new Node(origChars, off, origChars.length - off, value);
  }

  private static int getCommonLength(char[] origChars, int off, char[] prefixChars) {
    int extent = Math.min(origChars.length-off, prefixChars.length);
    int i = 0;
    for (; i < extent; i++) {
      if (origChars[off+i] != prefixChars[i]) {
        break;
      }
    }
    return i;
  }

  Node[] getChildren() {
    return children;
  }

  @Override
  public String toString() {
    return "Node [chars=" + Arrays.toString(chars) + ", children="
        + Arrays.toString(children) + ", value=" + value + "]";
  }

  public V get(char[] charArray, int off) {
    // precondition: charArray and this node share a common prefix which ended
    // before "off", but there's still more left in charArray.

    // no children, can't be a match
    if (children == null) {
      return null;
    }

    // there are existing children. scan them to see if there are any with
    // matching prefixes
    for (int i = 0; i < children.length; i++) {
      Node<V> childNode = children[i];
      int commonLength = getCommonLength(charArray, off, childNode.chars);

      // if there was any match...
      if (commonLength > 0) {
        // it could have been an exact match, which would indicate we found it
        if (commonLength == charArray.length - off && childNode.chars.length == commonLength) {
          // it's an exact match!
          return childNode.value;
        }
        // it could be a partial match, but short...
        if (commonLength < childNode.chars.length) {
          // the child node and the search string share a prefix, but the child
          // node is longer than the match length. the search string can't be in
          // the tree.
          return null;
        }
        // the search string is longer than the child's prefix, so we need to
        // recurse
        return childNode.get(charArray, off + commonLength);
      }
    }

    // we scanned all the children and found no matches, so the key is not in
    // the tree.
    return null;
  }
}
