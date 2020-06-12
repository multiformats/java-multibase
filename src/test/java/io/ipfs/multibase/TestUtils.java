package io.ipfs.multibase;


import static java.nio.charset.StandardCharsets.US_ASCII;

public final class TestUtils {
    private TestUtils(){};

    //Copied from https://stackoverflow.com/a/140861
    static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    static byte[] asciiToBytes(String s) {
        return s.getBytes(US_ASCII);
    }

    //Copied from https://stackoverflow.com/a/9855338
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
