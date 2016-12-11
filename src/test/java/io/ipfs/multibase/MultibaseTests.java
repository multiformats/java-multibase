package io.ipfs.multibase;

import org.junit.*;

import java.util.*;

public class MultibaseTests {

    @Test
    public void base58Test() {
        List<String> examples = Arrays.asList("zQmPZ9gcCEpqKTo6aq61g2nXGUhM4iCL3ewB6LDXZCtioEB",
                "zQmatmE9msSfkKxoffpHwNLNKgwZG8eT9Bud6YoPab52vpy");
        for (String example: examples) {
            byte[] output = Multibase.decode(example);
            String encoded = Multibase.encode(Multibase.Base.Base58BTC, output);
            if (!encoded.equals(encoded))
                throw new IllegalStateException("Incorrect base58! " + example + " => " + encoded);
        }
    }

    @Test
    public void base16Test() {
        List<String> examples = Arrays.asList("f234abed8debede",
                "f87ad873defc2b288a");
        for (String example: examples) {
            byte[] output = Multibase.decode(example);
            String encoded = Multibase.encode(Multibase.Base.Base16, output);
            if (!encoded.equals(encoded))
                throw new IllegalStateException("Incorrect base16! " + example + " => " + encoded);
        }
    }
}
