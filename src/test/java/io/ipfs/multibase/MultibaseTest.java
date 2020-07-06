package io.ipfs.multibase;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static io.ipfs.multibase.TestUtils.*;
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
                {Multibase.Base.Base32, hexToBytes("01A195B1B1BC81DDBDC9B190"), "bagqzlmnrxsa53pojwgia"},
                {Multibase.Base.Base32, hexToBytes("017112207F83B1657FF1FC53B92DC18148A1D65DFC2D4B1FA3D677284ADDD200126D9069"), "bafyreid7qoywk77r7rj3slobqfekdvs57qwuwh5d2z3sqsw52iabe3mqne"},
                {Multibase.Base.Base32, hexToBytes("446563656e7472616c697a652065766572797468696e67212121"), "birswgzloorzgc3djpjssazlwmvzhs5dinfxgoijbee"},
                {Multibase.Base.Base32, asciiToBytes("Hello World!"), "bjbswy3dpeblw64tmmqqq"},
                {Multibase.Base.Base64, hexToBytes("01A195B1B1BC81DDBDC9B190"), "mAaGVsbG8gd29ybGQ"},
                {Multibase.Base.Base64, asciiToBytes("Hello World!"), "mSGVsbG8gV29ybGQh"},
                {Multibase.Base.Base64, asciiToBytes("The quick brown fox jumps over the lazy dog."), "mVGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4"},
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



}
