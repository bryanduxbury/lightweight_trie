package com.rapleaf.lightweight_trie;

final class Utils {
  private Utils() {}

  static int getCommonLength(char[] origChars, int off, char[] prefixChars) {
    int extent = Math.min(origChars.length-off, prefixChars.length);
    int i = 0;
    for (; i < extent; i++) {
      if (origChars[off+i] != prefixChars[i]) {
        break;
      }
    }
    return i;
  }
}
