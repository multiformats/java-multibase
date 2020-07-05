package io.ipfs.multibase;

import org.junit.Test;

public class MultibaseBadInputsTest {

    @Test
    public void invalidBase16Test() {
        String example = "f012"; // hex string of odd length
        try {
            byte[] output = Multibase.decode(example);
            throw new RuntimeException();
        } catch (IllegalStateException e) {
            // expect error
        }
    }

    @Test (expected = NumberFormatException.class)
    public void invalidWithExceptionBase16Test() {
        String example = "f0g"; // g char is not allowed in hex
        Multibase.decode(example);
    }

}
