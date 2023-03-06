package io.ipfs.multibase;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MultibaseBadInputsTest {

    @Parameter
    public String input;

    @Parameters(name = "{index}: \"{0}\"")
    public static Collection<String> data() {
        return Arrays.asList(
            "f012", // Hex string of odd length, not allowed in Base16
            "f0g", // 'g' char is not allowed in Base16
            "zt1Zv2yaI", // 'I' char is not allowed in Base58
            "2", // '2' is not a valid encoding marker
            "" // Empty string is not a valid multibase
        );
    }

    @Test (expected = IllegalArgumentException.class)
    public void badInputTest() {
        Multibase.decode(input);
    }

}
