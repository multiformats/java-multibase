package io.ipfs.multibase;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class MultibaseBadInputsTest {

    public static Collection<String> data() {
        return Arrays.asList(
                "f012", // Hex string of odd length, not allowed in Base16
                "f0g", // 'g' char is not allowed in Base16
                "zt1Zv2yaI", // 'I' char is not allowed in Base58
                "2", // '2' is not a valid encoding marker
                "" // Empty string is not a valid multibase
        );
    }

    @MethodSource("data")
    @ParameterizedTest(name = "{index}: \"{0}\"")
    public void badInputTest(String input) {
        assertThrows(IllegalArgumentException.class, () -> {
            Multibase.decode(input);
        });
    }

}
