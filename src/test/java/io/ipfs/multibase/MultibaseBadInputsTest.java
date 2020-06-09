package io.ipfs.multibase;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class MultibaseBadInputsTest {

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
