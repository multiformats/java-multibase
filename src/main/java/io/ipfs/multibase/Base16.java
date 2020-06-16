package io.ipfs.multibase;

public class Base16 {

    @Deprecated
    public static byte[] decode(String hex) {
        return BlockEncoding.Base16.decode(hex);
    }

    @Deprecated
    public static String encode(byte[] data) {
        return BlockEncoding.Base16.encode(data);
    }
}
