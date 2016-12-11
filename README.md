# java-multihash
A Java implementation of Multibase

## Usage
byte[] data = ...
String encoded = Multibase.encode(Multibase.Base.Base58BTC, data);
byte[] decoded = Multibase.decode(encoded);

## Compilation
To compile just run ant.
