package io.ipfs.multibase;

import java.math.BigInteger;

public class Base36 {

  public static byte[] decode(String in) {
    if (in.isEmpty()) {
      return new byte[0];
    }
    BigInteger value = new BigInteger(in, 36);
    byte[] withoutLeadingZeroes = value.signum() == 0 ? new byte[0] : value.toByteArray();
    // BigInteger.toByteArray() prepends a 0x00 sign byte when the top magnitude byte has its
    // high bit set; strip it so the only leading zeroes are the ones recorded in the string.
    int start = withoutLeadingZeroes.length > 0 && withoutLeadingZeroes[0] == 0 ? 1 : 0;
    int magnitudeLength = withoutLeadingZeroes.length - start;
    int zeroPrefixLength = zeroPrefixLength(in);
    byte[] res = new byte[zeroPrefixLength + magnitudeLength];
    System.arraycopy(withoutLeadingZeroes, start, res, zeroPrefixLength, magnitudeLength);
    return res;
  }

  public static String encode(byte[] in) {
    BigInteger value = new BigInteger(1, in);
    String withoutLeadingZeroes = value.signum() == 0 ? "" : value.toString(36);
    int zeroPrefixLength = zeroPrefixLength(in);
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < zeroPrefixLength; i++) b.append("0");
    b.append(withoutLeadingZeroes);
    return b.toString();
  }

  private static int zeroPrefixLength(byte[] bytes) {
    for (int i = 0; i < bytes.length; i++) {
      if (bytes[i] != 0) {
        return i;
      }
    }
    return bytes.length;
  }

  private static int zeroPrefixLength(String in) {
    for (int i = 0; i < in.length(); i++) {
      if (in.charAt(i) != '0') {
        return i;
      }
    }
    return in.length();
  }
}
