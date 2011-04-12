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

final class MultiChildNode<V> extends AbstractNode<V> {
  private final char[] prefix;
  private final AbstractNode<V>[] children;

  public MultiChildNode(char[] prefix,
      V value,
      AbstractNode<V>[] optimizedChildren) {
    super(value);
    this.prefix = prefix;
    this.children = optimizedChildren;
  }

  @Override
  public AbstractNode<V>[] getChildren() {
    return children;
  }

  @Override
  public char[] getPrefix() {
    return prefix;
  }

  @Override
  public V get(char[] searchArr, int startOffset) {
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
}
