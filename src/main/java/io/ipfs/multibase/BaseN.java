package io.ipfs.multibase;

import java.math.BigInteger;

public class BaseN {

     static String encode(final String alphabet, final BigInteger base, final byte[] input) {
        // TODO: This could be a lot more efficient.
        BigInteger bi = new BigInteger(1, input);
        boolean isZero = bi.equals(BigInteger.ZERO);
        StringBuffer s = new StringBuffer();
        while (bi.compareTo(base) >= 0) {
            BigInteger mod = bi.mod(base);
            s.insert(0, alphabet.charAt(mod.intValue()));
            bi = bi.subtract(mod).divide(base);
        }
         if (! isZero)
             s.insert(0, alphabet.charAt(bi.intValue()));
        // Convert leading zeros too.
        for (byte anInput : input) {
            if (anInput == 0)
                s.insert(0, alphabet.charAt(0));
            else
                break;
        }
        return s.toString();
    }

    static byte[] decode(final String alphabet, final BigInteger base, final String input) {
        byte[] bytes = decodeToBigInteger(alphabet, base, input).toByteArray();
        // We may have got one more byte than we wanted, if the high bit of the next-to-last byte was not zero. This
        // is because BigIntegers are represented with twos-compliment notation, thus if the high bit of the last
        // byte happens to be 1 another 8 zero bits will be added to ensure the number parses as positive. Detect
        // that case here and chop it off.
        boolean stripSignByte = bytes.length > 1 && bytes[0] == 0 && bytes[1] < 0;
        // Count the leading zeros, if any.
        int leadingZeros = 0;
        for (int i = 0; i < input.length() && input.charAt(i) == alphabet.charAt(0); i++) {
            leadingZeros++;
        }
        if (leadingZeros == input.length())
            return new byte[leadingZeros];
        // Now cut/pad correctly. Java 6 has a convenience for this, but Android can't use it.
        byte[] tmp = new byte[bytes.length - (stripSignByte ? 1 : 0) + leadingZeros];
        System.arraycopy(bytes, stripSignByte ? 1 : 0, tmp, leadingZeros, tmp.length - leadingZeros);
        return tmp;
    }

    private static BigInteger decodeToBigInteger(final String alphabet, final BigInteger base, final String input) {
        BigInteger bi = BigInteger.valueOf(0);
        // Work backwards through the string.
        for (int i = input.length() - 1; i >= 0; i--) {
            int alphaIndex = alphabet.indexOf(input.charAt(i));
            if (alphaIndex == -1) {
                throw new IllegalStateException("Illegal character " + input.charAt(i) + " at " + i);
            }
            bi = bi.add(BigInteger.valueOf(alphaIndex).multiply(base.pow(input.length() - 1 - i)));
        }
        return bi;
    }
}
