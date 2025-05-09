[![Build Status](https://travis-ci.org/looorent/anpr-wiegand.svg?branch=master)](https://travis-ci.org/looorent/anpr-wiegand)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/be.looorent/anpr-wiegand/badge.svg)](http://search.maven.org/#artifactdetails%7Cbe.looorent%7Canpr-wiegand)

# ANPR - Wiegand

This library aims at formatting license plates to the Wiegand format according to the [Nedap Wiegand Interface Module](https://www.nedapidentification.com/products/anpr/wiegand-interface-module/).

## Development constraints

* Support of Java 8
* No additional dependency

## Supported formats

Two formats are supported:

* Wiegand 26 bits (based on SHA-1)
* Wiegand 64 bits

## Getting started

### Add the JAR to your classpath

Adding the JAR to the classpath allows you to use `be.looorent.anpr.Wiegand26` and `be.looorent.anpr.Wiegand64`. It is available on _Maven Central_.

To do so, for instance:
* with Gradle:
```groovy
compile "be.looorent:anpr-wiegand:1.0.1"
```
* or with Maven:
```xml
<dependency>
    <groupId>be.looorent</groupId>
    <artifactId>anpr-wiegand</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Requirements

* JDK 1.7+

### JPMS

When using _Java Platform Module System_, you must import the module named `anpr.wiegand`.

## Development

Build with AdoptOpenJDK 21.

### Release

```shell
./gradlew build 
./gradlew jreleaserConfig
./gradlew clean
./gradlew publish
./gradlew jreleaserFullRelease
```

