package io.ipfs.multibase;

public class UnsupportedEncodingException extends IllegalStateException {
    private final Multibase.Base base;

    public UnsupportedEncodingException(Multibase.Base base) {
        this.base = base;
    }

    public Multibase.Base getBase() {
        return base;
    }

    @Override
    public String getMessage() {
        return "Unsupported base encoding: " + base.name();
    }
}
