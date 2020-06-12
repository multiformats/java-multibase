package io.ipfs.multibase;

public class Base16 {

    @Deprecated
    public static String encode(byte[] data) {
        return BlockEncoding.Base16.encode(data);
    }
}
