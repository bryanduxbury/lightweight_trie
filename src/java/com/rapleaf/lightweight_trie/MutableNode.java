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

import java.util.Set;

final class MutableNode<V> extends AbstractNode<V> {
  private final char[] chars;

  private MutableNode[] children;

  public MutableNode(char[] origChars, int off, int len, V value) {
    super(value);
    chars = new char[len];
    System.arraycopy(origChars, off, chars, 0, len);
  }

  public void insert(char[] origChars, int off, V value) {
    // precondition: origChars and this node share a common prefix
    // off is positioned at the first character that does not match this node's
    // prefix.

    // if there are no children, then add a new single child with all of the chars.
    if (children == null) {
      children = new MutableNode[] {
        new MutableNode(origChars, off, origChars.length - off, value)
      };
      return;
    }

    // there are existing children. scan them to see if there are any with
    // matching prefixes
    for (int i = 0; i < children.length; i++) {
      MutableNode childNode = children[i];
      int commonLength = Utils.getCommonLength(origChars, off, childNode.chars);
      if (commonLength == 0) {
        // no match. continue on.
        continue;
      }

      if (commonLength < childNode.chars.length) {
        // the child node and the new string share a prefix, but the child node
        // is currently longer than the match length. we need to split the child
        // node.

        MutableNode replacement = new MutableNode(childNode.chars, 0, commonLength, null);
        final MutableNode newChild = new MutableNode(childNode.chars, commonLength, childNode.chars.length - commonLength, childNode.value);
        if (childNode.children != null) {
          newChild.children = new MutableNode[childNode.children.length];
          System.arraycopy(childNode.children, 0, newChild.children, 0, childNode.children.length);
        }

        replacement.children = new MutableNode[] {
            newChild
        };
        children[i] = replacement;

        // if we still have any chars left, recurse
        if (commonLength < origChars.length - off) {
          replacement.insert(origChars, off + commonLength, value);
        } else {
          // the split used up the available chars, so we're here!
          replacement.value = value;
        }
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
    MutableNode[] oldChildren = children;
    children = new MutableNode[oldChildren.length + 1];
    System.arraycopy(oldChildren, 0, children, 0, oldChildren.length);
    children[children.length - 1] = new MutableNode(origChars, off, origChars.length - off, value);
  }

  public MutableNode[] getChildren() {
    return children;
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
      MutableNode<V> childNode = children[i];
      int commonLength = Utils.getCommonLength(charArray, off, childNode.chars);

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

  public char[] getPrefix() {
    return chars;
  }

  public V getValue() {
    return value;
  }

  @Override
  public void getPartialMatches(Set<String> partialMatches, char[] searchArr, int searchArrOffset) {
    throw new UnsupportedOperationException();
  }

  @Override
  public char getPrefixFirst() {
    return chars[0];
  }
}
