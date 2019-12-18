[![Build Status](https://travis-ci.org/looorent/anpr-weigand.svg?branch=master)](https://travis-ci.org/looorent/anpr-weigand)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/be.looorent/anpr-weigand/badge.svg)](http://search.maven.org/#artifactdetails%7Cbe.looorent%7Canpr-weigand)

# ANPR - Wiegand

This library aims at formatting license plates to the Wiegand format according to the [Nedap Wiegand Interface Module](https://www.nedapidentification.com/products/anpr/wiegand-interface-module/).

## Development constraints

* Support of Java 7
* No additional dependency

## Supported formats

A single format is supported:

* Wiegand 26 bits (based on SHA-1)

## Getting started

### Add the JAR to your classpath

Adding the JAR to the classpath allows you to use `be.looorent.anpr.Wiegand`. It is available on _Maven Central_.

To do so, for instance:
* with Gradle:
```groovy
compile "be.looorent:anpr-wiegand:0.1.0"
```
* or with Maven:
```xml
<dependency>
    <groupId>be.looorent</groupId>
    <artifactId>anpr-wiegand</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Requirements

* JDK 1.7+

### JPMS

When using _Java Platform Module System_, you must import the module named `anpr.wiegand`.

## Development

### Release

Base on this [great article](https://nemerosa.ghost.io/2015/07/01/publishing-to-the-maven-central-using-gradle/)
```shell
./gradlew -Prelease uploadArchives closeAndReleaseRepository
```

### Future work

* Support for 64-bits format

