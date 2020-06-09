package io.ipfs.multibase;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MultibaseTest {

    private Multibase.Base base;
    private byte[] raw;
    private String encoded;

    public MultibaseTest(Multibase.Base base, byte[] raw, String encoded) {
        this.base = base;
        this.raw = raw;
        this.encoded = encoded;
    }

    @Parameters(name = "{index}: {0}, {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Multibase.Base.Base58BTC, hexToBytes("1220120F6AF601D46E10B2D2E11ED71C55D25F3042C22501E41D1246E7A1E9D3D8EC"), "zQmPZ9gcCEpqKTo6aq61g2nXGUhM4iCL3ewB6LDXZCtioEB"},
                {Multibase.Base.Base58BTC, hexToBytes("1220BA8632EF1A07986B171B3C8FAF0F79B3EE01B6C30BBE15A13261AD6CB0D02E3A"), "zQmatmE9msSfkKxoffpHwNLNKgwZG8eT9Bud6YoPab52vpy"},
                {Multibase.Base.Base58BTC, new byte[1], "z1"},
                {Multibase.Base.Base58BTC, new byte[2], "z11"},
                {Multibase.Base.Base58BTC, new byte[4], "z1111"},
                {Multibase.Base.Base58BTC, new byte[8], "z11111111"},
                {Multibase.Base.Base58BTC, new byte[16], "z1111111111111111"},
                {Multibase.Base.Base58BTC, new byte[32], "z11111111111111111111111111111111"},
                {Multibase.Base.Base16, hexToBytes("234ABED8DEBEDE"), "f234abed8debede"},
                {Multibase.Base.Base16, hexToBytes("87AD873DEFC2B288"), "f87ad873defc2b288"},
                {Multibase.Base.Base16, hexToBytes(""), "f"},
                {Multibase.Base.Base16, hexToBytes("01"), "f01"},
                {Multibase.Base.Base16, hexToBytes("0123456789ABCDEF"), "f0123456789abcdef"},
                {Multibase.Base.Base32, hexToBytes("01A195B1B1BC81DDBDC9B190"), "bnbswy3dpeb3w64tmmq"},
                {Multibase.Base.Base32, hexToBytes("0005C44881FE0EC595FFC7F14EE4B7060522875977F0B52C7E8F59DCA12B77480049B641A4"), "bafyreid7qoywk77r7rj3slobqfekdvs57qwuwh5d2z3sqsw52iabe3mqne"},
        });
    }

    @Test
    public void testEncode() {
        String output = Multibase.encode(base, raw);
        assertEquals(encoded, output);
    }

    @Test
    public void testDecode() {
        byte[] output = Multibase.decode(encoded);
        assertArrayEquals(String.format("Expected %s, but got %s", bytesToHex(raw), bytesToHex(output)), raw, output);
    }

    //Copied from https://stackoverflow.com/a/140861
    private static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    //Copied from https://stackoverflow.com/a/9855338
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
