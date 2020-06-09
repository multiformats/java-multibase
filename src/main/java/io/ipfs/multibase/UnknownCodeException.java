package io.ipfs.multibase;

public class UnknownCodeException extends IllegalStateException {
    private final char code;

    public UnknownCodeException(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return "Unknown Multibase type: " + code;
    }
}
