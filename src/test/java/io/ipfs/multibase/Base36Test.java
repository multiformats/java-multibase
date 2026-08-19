package io.ipfs.multibase;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class Base36Test {

  // Round trip must be size-preserving for every input: empty and all-zero arrays, and values
  // whose top byte has the high bit set (which BigInteger.toByteArray() would otherwise pad with
  // a 0x00 sign byte). See issue #67.
  @Test
  public void roundTripEdgeCases() {
    byte[][] inputs = {
      new byte[] {}, new byte[] {0}, new byte[] {0, 0}, new byte[] {(byte) 0x80}, new byte[] {0x01},
    };
    for (byte[] in : inputs) {
      byte[] out = Base36.decode(Base36.encode(in));
      assertArrayEquals(in, out, "round trip changed " + describe(in) + " -> " + describe(out));
    }
  }

  // Empty input encodes to the empty string and decodes back to an empty array.
  @Test
  public void emptyRoundTrip() {
    assertEquals("", Base36.encode(new byte[0]));
    assertArrayEquals(new byte[0], Base36.decode(""));
  }

  // The pre-existing multibase base36 vectors (the body after the "k"/"K" prefix) must stay
  // byte-identical, and the leading-zero vector must decode to 0x00 ++ decode(body).
  @Test
  public void existingVectorsUnchanged() {
    byte[] raw = hexToBytes("446563656e7472616c697a652065766572797468696e67212121");
    String body = "m552ng4dabi4neu1oo8l4i5mndwmpc3mkukwtxy9";
    assertEquals(body, Base36.encode(raw));
    assertArrayEquals(raw, Base36.decode(body));

    byte[] rawLeadingZero = hexToBytes("00446563656e7472616c697a652065766572797468696e67212121");
    String bodyLeadingZero = "0" + body;
    assertEquals(bodyLeadingZero, Base36.encode(rawLeadingZero));
    assertArrayEquals(rawLeadingZero, Base36.decode(bodyLeadingZero));

    byte[] zeroPlusBody = new byte[raw.length + 1];
    System.arraycopy(raw, 0, zeroPlusBody, 1, raw.length);
    assertArrayEquals(zeroPlusBody, Base36.decode(bodyLeadingZero));
  }

  // Exhaustive proof of a size-preserving round trip for every array of length 0..2. With the
  // BigInteger bug this fails for 33027 of the 65793 inputs; with the fix, for none.
  @Test
  public void exhaustiveRoundTripUpToLengthTwo() {
    int failures = roundTrips(new byte[0]) ? 0 : 1;
    for (int a = 0; a < 256; a++) {
      if (!roundTrips(new byte[] {(byte) a})) {
        failures++;
      }
      for (int b = 0; b < 256; b++) {
        if (!roundTrips(new byte[] {(byte) a, (byte) b})) {
          failures++;
        }
      }
    }
    assertEquals(0, failures, failures + " of 65793 inputs of length 0..2 failed to round trip");
  }

  private static boolean roundTrips(byte[] in) {
    return Arrays.equals(in, Base36.decode(Base36.encode(in)));
  }

  private static String describe(byte[] bytes) {
    StringBuilder sb = new StringBuilder("[len=").append(bytes.length).append(":");
    for (byte x : bytes) {
      sb.append(String.format(" %02x", x & 0xFF));
    }
    return sb.append("]").toString();
  }

  private static byte[] hexToBytes(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] =
          (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }
}
