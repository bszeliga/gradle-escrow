
# Gradle Escrow Build

----------------------------------

[![Build Status](https://travis-ci.org/bszeliga/gradle-escrow.svg?branch=master)](https://travis-ci.org/bszeliga/gradle-escrow)

## Goals

The goals of the gradle escrow plugin is to make it easily possible to transfer code as well as
all dependencies from a well connected environment to a disconnected environment. It does this
creating a local maven repository within your working directory.

## Use

If using Gradle 2.1 or newer include the following at the top of your build script.

```
plugins {
  id "com.bszeliga.gradle.escrow" version "0.0.1"
}
```

Otherwise:

```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.bszeliga.gradle:escrow:0.0.1"
  }
}

apply plugin: "com.bszeliga.gradle.escrow"
```

## Tasks

Relevant tasks for the plugin are listed below.

| Task        | Description |
|-------------|-------------|
| escrow      | Analyzes all your configurations and creates copy of all resolved artifacts into a .m2 directory within the project. |
| escrowClean | Deletes the above created directory. |