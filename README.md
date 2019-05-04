# AsciiPanelWindowingToolkit (APWT)

AsciiPanelWindowingToolkit is a set of classes and utilities that will make working with AsciiPanel on a larger scale much easier. It includes support for screens, text windows, multicolored strings, and defines constants for different CP437 characters. APWT, of course, depends on [AsciiPanel](github.com/trystan/AsciiPanel), as well as [SquidLib](https://github.com/SquidPony/SquidLib).

**DISCLAIMER:** To be brutally honest, one of the reasons AsciiPanel is still a decent library is because of its downright simplicity. If you really want more complexity in your Java terminal emulators, I would strongly recommend [Zircon](https://github.com/Hexworks/zircon), or even [SquidLib](https://github.com/SquidPony/SquidLib)'s built-in graphical options. Still, this library exists if you've got something made with AsciiPanel and want to easily extend its functionality.

## Implementation

### Compiling

To compile APWT, clone the repository and run ``gradle clean build jar``. You can then include the compiled library jar file in your projects.

### Usage

To set up APWT, download the sources or the library jars and include them as dependencies in your project. To start using APWT, initialize a Display object and go from there. The Display is designed to work with a hierarchy of Screens, each of which read input and display output. This system is modeled after [Trystan's roguelike tutorial](https://trystans.blogspot.com/2016/01/roguelike-tutorial-00-table-of-contents.html).

For an example of APWT in action, check out my (abandoned) space roguelike [EverSector](https://github.com/Maugrift/EverSector) ([original Bitbucket mirror](https://bitbucket.org/Maugrift/eversector)).

### License

APWT is licensed under the MIT license. Basically, this lets you copy, modify, distribute, and sell the library or works that use it, privately or publicly. See the LICENSE.txt file for the complete license, and know that this description is not a substitute for it.

## Contributing

If you want to contribute to APWT, I would happy to accept reasonable pull requests. Please create JavaDoc comments on any added methods, classes, and constants, and try to adhere to the existing code format.

Note that I have ceased development on APWT because, 1) it is largely feature complete as-is and 2) I don't have much further use for it in my own projects.

## Contact

If you're having any problems with APWT, please post an issue here on GitHub! For other inquiries, you can DM me on Twitter ([@Maugrift](https://twitter.com/Maugrift)) or Discord (Maugrift#5077).
