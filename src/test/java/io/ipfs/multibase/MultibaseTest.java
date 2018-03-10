package io.ipfs.multibase;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MultibaseTest {

    @Test
    public void base58Test() {
        List<String> examples = Arrays.asList("zQmPZ9gcCEpqKTo6aq61g2nXGUhM4iCL3ewB6LDXZCtioEB",
                "zQmatmE9msSfkKxoffpHwNLNKgwZG8eT9Bud6YoPab52vpy");
        for (String example: examples) {
            byte[] output = Multibase.decode(example);
            String encoded = Multibase.encode(Multibase.Base.Base58BTC, output);
            assertEquals(example, encoded);
        }
    }

    @Test
    public void base16Test() {
        List<String> examples = Arrays.asList("f234abed8debede",
                "f87ad873defc2b288",
                "f",
                "f01",
                "f0123456789abcdef");
        for (String example: examples) {
            byte[] output = Multibase.decode(example);
            String encoded = Multibase.encode(Multibase.Base.Base16, output);
            assertEquals(example, encoded);
        }
    }

    @Test
    public void base32Test() {
        List<String> examples = Arrays.asList("bnbswy3dpeb3w64tmmq");
        for (String example: examples) {
            byte[] output = Multibase.decode(example);
            String encoded = Multibase.encode(Multibase.Base.Base32, output);
            assertEquals(example, encoded);
        }
    }

    @Test
    public void invalidBase16Test() {
        String example = "f012"; // hex string of odd length
        byte[] output = Multibase.decode(example);
        String encoded = Multibase.encode(Multibase.Base.Base16, output);
        assertNotEquals(example, encoded);

    }

    @Test (expected = NumberFormatException.class)
    public void invalidWithExceptionBase16Test() {
        String example = "f0g"; // g char is not allowed in hex
        Multibase.decode(example);
    }

}
