# AsciiPanelWindowingToolkit (APWT)

AsciiPanelWindowingToolkit is a set of classes and utilities that will make working with [AsciiPanel](https://github.com/trystan/AsciiPanel) on a larger scale much easier.
It includes support for screens, text windows, multicolored strings, and constants for different CP437 characters.
APWT, of course, depends on [AsciiPanel](https://github.com/trystan/AsciiPanel), as well as [SquidLib](https://github.com/SquidPony/SquidLib).

**DISCLAIMER:** To be honest, one of the reasons AsciiPanel is still a decent library is because of its downright simplicity.
If you really want more complexity in your Java terminal emulators, I would strongly recommend one that has been designed from the ground up with more advanced features.
[Zircon](https://github.com/Hexworks/zircon) is a great choice that I have used myself.
Nonetheless, this library exists if you've got something made with AsciiPanel and want to easily extend its functionality.

## Compiling

To build APWT as a library JAR, clone the repository and run `./gradlew clean build jar`.
You can then include the compiled library jar file in your projects.

To test building for Maven distribution (e.g. JitPack), run `./gradlew clean build jar publishToMavenLocal`.

## Usage

To use APWT in your own Java project, you have a few options:

1. Build a library JAR as described above and include it in your project.
2. Use a build tool like Maven or Gradle and get APWT from JitPack.

Below is an example configuration for Gradle based off of the `v1.0.0` tag:

```gradle
repositories {
	allprojects {
		repositories {
			maven {
				url 'https://jitpack.io'
			}
		}
	}
}

dependencies {
	implementation 'com.github.maugrift:apwt:v1.0.0'
}
```

## Documentation

APWT has full JavaDoc documentation.
To read it, you again have a few options:

1. Run `./gradlew javadoc` and browse to `build/docs/index.html`.
2. Get a JavaDoc JAR from JitPack.

To get started, I would recommend simply initializing a `Display` object and going from there.
The `Display` is designed to work with a hierarchy of `Screen`s, each of which read input and display output.
This system is modeled after [Trystan's roguelike tutorial](https://trystans.blogspot.com/2016/01/roguelike-tutorial-00-table-of-contents.html).

For an example of APWT in action, check out my (abandoned) space roguelike [EverSector](https://github.com/Maugrift/EverSector).

## Contributing

I have ceased development on APWT because, 1) it is largely feature complete as-is and 2) I don't have much further use for it in my own projects.
However, I may still try to maintain it, so if something doesn't work, feel free to create an issue or pull request for it.

If you want to develop more features for APWT, I would suggest making your own fork.
Just make sure to abide by the terms of the license (found in `LICENSE.txt`).
