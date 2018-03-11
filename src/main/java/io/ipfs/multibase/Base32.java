package io.ipfs.multibase;

import java.math.BigInteger;

/**
 * Based on RFC 4648
 * No padding
 */
public class Base32 {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz234567";
    private static final BigInteger BASE = BigInteger.valueOf(32);

    public static String encode(final byte[] input) {
        return BaseN.encode(ALPHABET, BASE, input);
    }

    public static byte[] decode(final String input) {
        return BaseN.decode(ALPHABET, BASE, input);
    }
}
