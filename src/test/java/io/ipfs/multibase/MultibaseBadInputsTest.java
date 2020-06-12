package io.ipfs.multibase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class MultibaseBadInputsTest {

    private String encoded;

    public MultibaseBadInputsTest(String encoded) {
        this.encoded = encoded;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Object[] data() {
        return new Object[]{
                // Incorrect length
                "f12345",
                "bagqzlmnrxsa53pojwgi",
                "mybG",
                // Illegal characters
                "f0g",
                "bagqzlmnrxsa50pojwgia",
                "m0-GVsbG8gd29ybGQ"
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecode() {
        Multibase.decode(encoded);
    }

}
