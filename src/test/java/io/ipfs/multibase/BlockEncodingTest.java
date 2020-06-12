package io.ipfs.multibase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static io.ipfs.multibase.BlockEncoding.*;
import static io.ipfs.multibase.TestUtils.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BlockEncodingTest {

    private BlockEncoding encoding;
    private byte[] raw;
    private String encoded;

    public BlockEncodingTest(BlockEncoding encoding, byte[] raw, String encoded) {
        this.encoding = encoding;
        this.raw = raw;
        this.encoded = encoded;
    }

    @Parameterized.Parameters(name = "{index}: {0}, {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Base16, new byte[0], "" },
                {Base16, new byte[5], "0000000000" },
                {Base16, asciiToBytes("f"), "66" },
                {Base16, asciiToBytes("fo"), "666F" },
                {Base16, asciiToBytes("foo"), "666F6F" },
                {Base16, asciiToBytes("foob"), "666F6F62" },
                {Base16, asciiToBytes("fooba"), "666F6F6261" },
                {Base16, asciiToBytes("foobar"), "666F6F626172" },
                {Base16, hexToBytes("CAFEBABE"), "CAFEBABE" },

                {Base32, new byte[0], "" },
                {Base32, new byte[5], "AAAAAAAA" },
                {Base32, asciiToBytes("foo"), "MZXW6" },
                {Base32, asciiToBytes("foob"), "MZXW6YQ" },
                {Base32, asciiToBytes("fooba"), "MZXW6YTB" },
                {Base32, asciiToBytes("foobar"), "MZXW6YTBOI" },
                {Base32, hexToBytes("0102030405"), "AEBAGBAF" },
                {Base32, hexToBytes("010203040506"), "AEBAGBAFAY" },

                {Base64, new byte[0], "" },
                {Base64, new byte[5], "AAAAAAA" },
                {Base64, hexToBytes("CAFEBABE"), "yv66vg" },
                {Base64, hexToBytes("010203040506"), "AQIDBAUG" },
                {Base64, hexToBytes("01020304050607"), "AQIDBAUGBw" }
        });
    }

    @Test
    public void testEncode() {
        String output = encoding.encode(raw);
        assertEquals(encoded, output);
    }

    @Test
    public void testDecode() {
        byte[] output = encoding.decode(encoded);
        assertArrayEquals(String.format("Expected %s, but got %s", bytesToHex(raw), bytesToHex(output)), raw, output);
    }

}
