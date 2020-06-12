package io.ipfs.multibase;

import java.util.Arrays;

public class BlockEncoding {

    public static final BlockEncoding Base16 = new BlockEncoding("0123456789ABCDEF", 1, 2);
    public static final BlockEncoding Base32 = new BlockEncoding("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", 5, 8);
    public static final BlockEncoding Base64 = new BlockEncoding("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", 3, 4);

    private final String alphabet;
    private final int blockSizeRaw; //bytes
    private final int blockSizeEncoded; //characters
    private final int bitsPerChar;

    public BlockEncoding(String alphabet, int blockSizeRaw, int blockSizeEncoded) {
        this.alphabet = alphabet;
        this.blockSizeRaw = blockSizeRaw;
        this.blockSizeEncoded = blockSizeEncoded;
        this.bitsPerChar = 8 * blockSizeRaw / blockSizeEncoded;
    }

    public String encode(final byte[] input) {
        int inOverhang = input.length % blockSizeRaw;
        int fullBlocksCount = input.length / blockSizeRaw;
        int inOverhangBits = inOverhang * 8;
        int outOverhang = inOverhangBits / bitsPerChar + (inOverhangBits % bitsPerChar == 0? 0 : 1);
        char[] buffer = new char[fullBlocksCount * blockSizeEncoded + outOverhang];
        for (int i = 0; i < fullBlocksCount; ++i) {
            char[] encoded = encodeBlock(Arrays.copyOfRange(input, i * blockSizeRaw, (i + 1) * blockSizeRaw));
            System.arraycopy(encoded, 0, buffer, i * blockSizeEncoded, blockSizeEncoded);
        }
        // If there is a final underfull block, process it separately
        if(inOverhang != 0) {
            char[] encoded = encodeBlock(Arrays.copyOfRange(input, fullBlocksCount * blockSizeRaw, (fullBlocksCount + 1) * blockSizeRaw));
            System.arraycopy(encoded, 0, buffer, fullBlocksCount * blockSizeEncoded, outOverhang);
        }
        return String.valueOf(buffer);
    }

    private char[] encodeBlock(final byte[] block) {
        char[] result = new char[blockSizeEncoded];
        for (int i = 0; i < result.length; ++i) {
            int index = bitsToIndex(block, i * bitsPerChar, bitsPerChar);
            result[i] = alphabet.charAt(index);
        }
        return result;
    }

    private int bitsToIndex(byte[] block, int bitOffset, int bitCount) {
        /*
         * RFC 4648 requires big-endian bit order, at least for base32,
         * but bits in a Java byte are stored in little-endian order
         * See https://tools.ietf.org/html/rfc4648#page-8
         */
        int acc = 0;
        for (int addend = 1 << (bitCount - 1), i = bitOffset; i < bitOffset + bitCount; addend >>= 1, ++i) {
            byte littleEndian = block[i / 8];
            boolean bit = (reverse(littleEndian) & (1 << (i % 8))) != 0;
            if (bit) acc += addend;
        }
        return acc;
    }

    public byte[] decode(final String input) {
        checkLength(input);
        int inOverhang = input.length() % blockSizeEncoded;
        int fullBlocksCount = input.length() / blockSizeEncoded;
        int inOverhangBits = inOverhang * bitsPerChar;
        int outOverhang = inOverhangBits / 8;
        byte[] buffer = new byte[fullBlocksCount * blockSizeRaw + outOverhang];
        for (int i = 0; i < fullBlocksCount; ++i) {
            byte[] decoded = decodeBlock(input.substring(i * blockSizeEncoded, (i + 1) * blockSizeEncoded).toCharArray());
            System.arraycopy(decoded, 0, buffer, i * blockSizeRaw, blockSizeRaw);
        }
        // If there is a final underfull block, process it separately
        if(inOverhang != 0) {
            String remainder = input.substring(fullBlocksCount * blockSizeEncoded);
            byte[] decoded = decodeBlock(remainder.toCharArray());
            System.arraycopy(decoded, 0, buffer, fullBlocksCount * blockSizeRaw, outOverhang);
        }
        return buffer;
    }

    private void checkLength(String input) {
        int bitsInUnderfullBlock = (input.length() % blockSizeEncoded) * bitsPerChar;
        /*
         * Underfull block length for base16:
         *   4 bit --- bad
         *
         * Underfull block length for base32:
         *   5 bit --- bad
         *  10 bit --- OK, discard last 2 bits
         *  15 bit --- bad
         *  20 bit --- OK, discard last 4 bits
         *  25 bit --- OK, discard last 7 bits
         *  30 bit --- bad
         *  35 bit --- OK, discard last 3 bits
         *
         * Underfull block length for base64:
         *   6 bit --- bad
         *  12 bit --- OK, discard last 4 bits
         *  18 bit --- OK, discard last 2 bits
         */
        int padBitsCount = bitsInUnderfullBlock % 8;
        if(padBitsCount >= bitsPerChar) throw new InputLengthException(input.length());
        if(padBitsCount > 0) {
            char lastCharacter = input.charAt(input.length() - 1);
            int lastValue = alphabet.indexOf(lastCharacter);
            if(lastValue != 0 && Integer.lowestOneBit(lastValue) < 1 << (padBitsCount)) throw new NonZeroPadException(lastCharacter);
        }
    }

    private byte[] decodeBlock(final char[] block) {
        byte[] result = new byte[blockSizeRaw];
        for (int i = 0; i < block.length; ++i) {
            int charValue = alphabet.indexOf(block[i]);
            if(charValue == -1) throw new NumberFormatException(String.format("Character '%c' (code 0x%x) cannot be decoded", block[i], (int) block[i]));
            byte mask = (byte) charValue;
            setBits(result, i * bitsPerChar, mask);
        }
        return result;
    }

    private void setBits(byte[] target, int bitOffset, byte mask) {
        /*
         * The mask is assumed to be little-endian,
         * and bits in a Java byte are also stored in little-endian order
         */
        int byteIndex = bitOffset / 8;
        int maskShift = bitOffset % 8;
        int slip = 8 - bitsPerChar;
        byte shiftedMask = shiftRight(mask, maskShift - slip);
        target[byteIndex] |= shiftedMask;
        // If the offset is not aligned with the byte boundary, the tail of the mask must be applied to the next byte
        if(maskShift != 0) {
            byte shiftedTail = shiftRight(mask, -8 + maskShift - slip);
            if(shiftedTail != 0) {
                target[byteIndex + 1] |= shiftedTail;
            }
        }
    }

    private static byte reverse(byte b) {
        return (byte) (Integer.reverse(b) >>> 24);
    }

    private static byte shiftRight(byte b, int shift) {
        return (byte) (shift > 0 ? b >> shift : b << -shift);
    }

    public class InputLengthException extends IllegalArgumentException {
        private final int inputLength;

        private static final String message = "Sequence of %s bits (%s characters %s bit each) is not a valid representation of encoded data";

        public InputLengthException(int inputLength) {
            this.inputLength = inputLength;
        }

        public int getInputLength() {
            return inputLength;
        }

        public BlockEncoding getEncoding() {
            return BlockEncoding.this;
        }

        @Override
        public String getMessage() {
            return String.format(message, inputLength * bitsPerChar, inputLength, bitsPerChar);
        }
    }

    public class NonZeroPadException extends IllegalArgumentException {
        private final char lastCharacter;

        private static final String message = "The last character ('%c', code 0x%x) contains non-zero pad bits";

        public NonZeroPadException(char lastCharacter) {
            this.lastCharacter = lastCharacter;
        }

        public char getLastCharacter() {
            return lastCharacter;
        }

        public BlockEncoding getEncoding() {
            return BlockEncoding.this;
        }

        @Override
        public String getMessage() {
            return String.format(message, lastCharacter, lastCharacter);
        }
    }

}
