package io.ipfs.multibase;

public class Base16 {

    public static byte[] decode(String hex) {
        byte[] res = new byte[hex.length()/2];
        for (int i=0; i < res.length; i++) {
            res[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return res;
    }

    public static String encode(byte[] data) {
        StringBuilder s = new StringBuilder();
        for (byte b : data) {
            s.append(String.format("%02x", b & 0xFF));
        }
        return s.toString();
    }
}
