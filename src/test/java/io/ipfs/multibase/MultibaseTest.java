package io.ipfs.multibase;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MultibaseTest {

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
                {Multibase.Base.Base16Upper, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "F446563656E7472616C697A652065766572797468696E67212121"},
                {Multibase.Base.Base32, hexToBytes("4D756C74696261736520697320617765736F6D6521205C6F2F"), "bjv2wy5djmjqxgzjanfzsaylxmvzw63lfeeqfy3zp"},
                {Multibase.Base.Base32, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "birswgzloorzgc3djpjssazlwmvzhs5dinfxgoijbee"},
                {Multibase.Base.Base32, hexToBytes("01711220bb6ef01d25459cc803d0864cde4227cd2b779965eb1df34abeaec22c20fa42ea"), "bafyreif3n3yb2jkftteahuegjtpeej6nfn3zszpldxzuvpvoyiwcb6sc5i"},
                {Multibase.Base.Base32, hexToBytes("0000000000"), "baaaaaaaa"},
                {Multibase.Base.Base32, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "birswgzloorzgc3djpjssazlwmvzhs5dinfxgoijbee"},
                {Multibase.Base.Base32Pad, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "cirswgzloorzgc3djpjssazlwmvzhs5dinfxgoijbee======"},
                {Multibase.Base.Base32PadUpper, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "CIRSWGZLOORZGC3DJPJSSAZLWMVZHS5DINFXGOIJBEE======"},
                {Multibase.Base.Base32Upper, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "BIRSWGZLOORZGC3DJPJSSAZLWMVZHS5DINFXGOIJBEE"},
                {Multibase.Base.Base32Hex, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "v8him6pbeehp62r39f9ii0pbmclp7it38d5n6e89144"},
                {Multibase.Base.Base32HexPad, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "t8him6pbeehp62r39f9ii0pbmclp7it38d5n6e89144======"},
                {Multibase.Base.Base32HexPadUpper, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "T8HIM6PBEEHP62R39F9II0PBMCLP7IT38D5N6E89144======"},
                {Multibase.Base.Base32HexUpper, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "V8HIM6PBEEHP62R39F9II0PBMCLP7IT38D5N6E89144"},
                {Multibase.Base.Base36, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "km552ng4dabi4neu1oo8l4i5mndwmpc3mkukwtxy9"},
                {Multibase.Base.Base36, hexToBytes("00446563656e7472616c697a652065766572797468696e67212121"), "k0m552ng4dabi4neu1oo8l4i5mndwmpc3mkukwtxy9"},
                {Multibase.Base.Base36Upper, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "KM552NG4DABI4NEU1OO8L4I5MNDWMPC3MKUKWTXY9"},
                {Multibase.Base.Base58BTC, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "z36UQrhJq9fNDS7DiAHM9YXqDHMPfr4EMArvt"},
                {Multibase.Base.Base64, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "mRGVjZW50cmFsaXplIGV2ZXJ5dGhpbmchISE"},
                {Multibase.Base.Base64Url, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "uRGVjZW50cmFsaXplIGV2ZXJ5dGhpbmchISE"},
                {Multibase.Base.Base64Pad, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "MRGVjZW50cmFsaXplIGV2ZXJ5dGhpbmchISE="},
                {Multibase.Base.Base64UrlPad, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "URGVjZW50cmFsaXplIGV2ZXJ5dGhpbmchISE="},
        });
    }

    @MethodSource("data")
    @ParameterizedTest(name = "{index}: {0}, {2}")
    public void testEncode(Multibase.Base base, byte[] raw, String encoded) {
        String output = Multibase.encode(base, raw);
        assertEquals(encoded, output);
    }

    @MethodSource("data")
    @ParameterizedTest(name = "{index}: {0}, {2}")
    public void testDecode(Multibase.Base base, byte[] raw, String encoded) {
        byte[] output = Multibase.decode(encoded);
        assertArrayEquals(raw, output, String.format("Expected %s, but got %s", bytesToHex(raw), bytesToHex(output)));
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
