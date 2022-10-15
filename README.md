# AsciiPanel Windowing Toolkit

AsciiPanel Windowing Toolkit is a library that provides several high-level UI components for [AsciiPanel](https://github.com/trystan/AsciiPanel).

**DISCLAIMER:** If you want to develop a roguelike or other complex TUI application in Java, I strongly recommend using [Zircon](https://hexworks.org/projects/zircon) instead of AsciiPanel or APWT.

## Compiling

Dependencies:

- Java 8 or newer
- Gradle (last tested with Gradle 7.5.1)

To build a library JAR:

```sh
gradle clean build jar
```

## Usage

To use APWT in your own Java project, you have a few options:

1. Build a library JAR as described in the Compiling section above and include it in your project.
2. Add this repository as a Git submodule: `git submodule add https://frie.dev/apwt.git`
	- You may need to create a symbolic link so that APWT's source tree is in the path expected by your build system.
	- See [EverSector](https://frie.dev/eversector) for an example of this configuration.
3. Use a [Gradle source dependency](https://blog.gradle.org/introducing-source-dependencies).
	- I have not been able to get this to work for APWT, but you may have better luck.

## Documentation

APWT has full JavaDoc documentation.
To read it, run `gradle javadoc` and browse to `build/docs/index.html`.

To get started, I would recommend simply initializing a `Display` object and going from there.
The `Display` is designed to work with a hierarchy of `Screen`s, each of which read input and display output.
This system is modeled after [Trystan's roguelike tutorial](https://trystans.blogspot.com/2016/01/roguelike-tutorial-00-table-of-contents.html).

For an example project using APWT, see [EverSector](https://frie.dev/eversector).

## Contributing

I have ceased development on APWT because it is largely feature-complete and I have no further use for it in my own projects.
If you want to expand on APWT, fork it.
