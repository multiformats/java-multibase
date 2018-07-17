# java-multibase

[![](https://img.shields.io/badge/made%20by-Protocol%20Labs-blue.svg?style=flat-square)](http://ipn.io)
[![](https://img.shields.io/badge/project-multiformats-blue.svg?style=flat-square)](https://github.com/multiformats/multiformats)
[![](https://img.shields.io/badge/freenode-%23ipfs-blue.svg?style=flat-square)](https://webchat.freenode.net/?channels=%23ipfs)
[![](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

> A Java implementation of [Multibase](https://github.com/multiformats/multibase)

## Install

Simply clone this repo.

## Usage
```java
byte[] data = ...
String encoded = Multibase.encode(Multibase.Base.Base58BTC, data);
byte[] decoded = Multibase.decode(encoded);
```

## Dependency
You can use this project by building the JAR file as specified below, or by using [JitPack](https://jitpack.io/#multiformats/java-multibase/) (also supporting Gradle, SBT, etc).

for Maven, you can add the follwing sections to your POM.XML:
```
  <repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
  </repositories>

  <dependencies>
    <dependency>
      <groupId>io.ipfs</groupId>
      <artifactId>multibase</artifactId>
      <version>v1.0.1</version>
    </dependency>
  </dependencies>
```

## Testing

### Ant
`ant test`

### Maven
`mvn test`

## Building

### Ant
`ant dist` will build a JAR file in the `./dist` suitable for manual inclusion in a project. Dependent libraries are included in `./dist/lib`.

### Maven
`mvn package` will build a JAR file with Maven dependency information.

## Releasing
The version number is specified in `build.xml` and `pom.xml` and must be changed in both places in order to be accurately reflected in the JAR file manifest. A git tag must be added in the format "vx.x.x" for JitPack to work.

## Maintainers

Captain: [@ianopolous](https://github.com/ianopolous).

## Contribute

Contributions welcome. Please check out [the issues](https://github.com/multiformats/java-multibase/issues).

Check out our [contributing document](https://github.com/multiformats/multiformats/blob/master/contributing.md) for more information on how we work, and about contributing in general. Please be aware that all interactions related to multiformats are subject to the IPFS [Code of Conduct](https://github.com/ipfs/community/blob/master/code-of-conduct.md).

Small note: If editing the README, please conform to the [standard-readme](https://github.com/RichardLitt/standard-readme) specification.

## License

[MIT](LICENSE) © 2015 Ian Preston
