package io.ipfs.multibase;

import java.util.Map;
import java.util.TreeMap;

public class Multibase {

    public enum Base {
        // encoding(code)
        Base1('1'), // unary tends to be 11111
        Base2('0'), // binary has 1 and 0
        Base8('7'), // highest char in octal
        Base10('9'), // highest char in decimal
        Base16('f'), // highest char in hex
        Base32('b'), // rfc4648 no padding
        Base58Flickr('Z'), // highest char
        Base58BTC('z'); // highest char

        private final char prefix;

        Base(char prefix) {
            this.prefix = prefix;
        }

        private static Map<Character, Base> lookup = new TreeMap<>();
        static {
            for (Base b: Base.values())
                lookup.put(b.prefix, b);
        }

        public static Base lookup(char p) {
            if (!lookup.containsKey(p))
                throw new IllegalStateException("Unknown Multibase type: " + p);
            return lookup.get(p);
        }
    }

    public static String encode(Base b, byte[] data) {
        switch (b) {
            case Base58BTC:
                return b.prefix + Base58.encode(data);
            case Base16:
                return b.prefix + Base16.encode(data);
            case Base32:
                return b.prefix + Base32.encode(data);
            default:
                throw new IllegalStateException("Unsupported base encoding: " + b.name());
        }
    }

    public static Base encoding(String data) {
        return Base.lookup(data.charAt(0));
    }

    public static byte[] decode(String data) {
        Base b = encoding(data);
        String rest = data.substring(1);
        switch (b) {
            case Base58BTC:
                return Base58.decode(rest);
            case Base16:
                return Base16.decode(rest);
            case Base32:
                return Base32.decode(rest);
            default:
                throw new IllegalStateException("Unsupported base encoding: " + b.name());
        }
    }
}
