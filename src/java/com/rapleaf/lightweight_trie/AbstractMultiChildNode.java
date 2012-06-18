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
    // there are existing children. scan them to see if there are any with
    // matching prefixes
    for (int i = 0; i < children.length; i++) {
      AbstractNode<V> childNode = children[i];
      int commonLength = Utils.getCommonLength(searchArr, startOffset, childNode.getPrefix());

      // if there was any match...
      if (commonLength == childNode.getPrefix().length) {
        // it could have been an exact match, which would indicate we found it
        if (searchArr.length == commonLength + startOffset) {
          // it's an exact match!
          return childNode.value;
        } else {
          // the search string is longer than the child's prefix, so we need to
          // recurse
          return childNode.get(searchArr, startOffset + commonLength);
        }
      } else if (commonLength > 0) {
        // any partial match means we're at a dead end. return immediately.
        return null;
      }
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
