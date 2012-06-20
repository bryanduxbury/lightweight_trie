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

import java.util.Set;

public abstract class AbstractMultiChildNode<V> extends AbstractNode<V> {

  private final AbstractNode<V>[] children;

  protected AbstractMultiChildNode(V value, AbstractNode<V>[] children) {
    super(value);
    this.children = children;
  }

  @Override
  public final V get(char[] searchArr, int startOffset) {
    // there are existing children. binary search them to see if there are any with
    // matching prefixes
    int lower = 0;
    int upper = children.length - 1;
    char keyPrefixFirst = searchArr[startOffset];
    while (lower != upper) {
      // pick the midpoint
      int mid = lower + (upper - lower) / 2;
      AbstractNode<V> childNode = children[mid];

      // common case: key and the child prefix will have zero chars in common
      final char[] childPrefix = childNode.getPrefix();
      char childPrefixFirst = childPrefix[0];

      if (keyPrefixFirst < childPrefixFirst) {
        // not the right key. keep binary searching the next child.
        upper = mid - 1;
        continue;
      } else if (keyPrefixFirst > childPrefixFirst) {
        // not the right key. keep binary searching the next child.
        lower = mid + 1;
        continue;
      }

      // if we get here the, the first chars match, which is exciting. if the
      // rest of the strings don't match exactly, we don't have to explore any
      // further.

      // a quick check we can do is to see if this current child prefix is
      // longer than what's left in the search key, as that would guarantee a
      // non-match.
      // TODO: evaluate if this is actually beneficial
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

    // didn't find a sub-match
    return null;
  }

  @Override
  public AbstractNode<V>[] getChildren() {
    return children;
  }

  @Override
  public void getPartialMatches(Set<String> partialMatches, char[] searchArr, int searchArrOffset) {
    // there are existing children. scan them to see if there are any with
    // matching prefixes
    for (int i = 0; i < children.length; i++) {
      AbstractNode<V> childNode = children[i];
      int commonLength = Utils.getCommonLength(searchArr, searchArrOffset, childNode.getPrefix());

      // if there was any match...
      if (commonLength == childNode.getPrefix().length) {
        // if there's a value in this child, then it is a partial match and should be added to the set
        if (childNode.value != null) {
          partialMatches.add(new String(searchArr, 0, searchArrOffset + commonLength));
        }

        // explore further down this subtree to see if there are more partial matches
        childNode.getPartialMatches(partialMatches, searchArr, searchArrOffset + commonLength);

        // no reason to keep searching past here, and let's just save the extra
        // comparison below
        break;
      }

      // if we had a partial match, but not a complete one, then there is no way
      // for the other children of the current node to have a match, so we
      // should exit.
      if (commonLength > 0) {
        break;
      }
    }
  }
}
