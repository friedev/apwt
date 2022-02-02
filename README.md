# AsciiPanelWindowingToolkit (APWT)

AsciiPanelWindowingToolkit is a set of classes and utilities that will make working with [AsciiPanel](https://github.com/trystan/AsciiPanel) on a larger scale much easier. It includes support for screens, text windows, multicolored strings, and constants for different CP437 characters. APWT, of course, depends on [AsciiPanel](https://github.com/trystan/AsciiPanel), as well as [SquidLib](https://github.com/SquidPony/SquidLib).

**DISCLAIMER:** To be honest, one of the reasons AsciiPanel is still a decent library is because of its downright simplicity. If you really want more complexity in your Java terminal emulators, I would strongly recommend one that has been designed from the ground up with more advanced features. [Zircon](https://github.com/Hexworks/zircon) is a great choice that I have used myself. Nonetheless, this library exists if you've got something made with AsciiPanel and want to easily extend its functionality.

## Compiling

To compile APWT, clone the repository and run `gradle clean build jar`. You can then include the compiled library jar file in your projects.

## Usage

To set up APWT, download the sources or the library jars and include them as dependencies in your project. To start using APWT, initialize a Display object and go from there. The Display is designed to work with a hierarchy of Screens, each of which read input and display output. This system is modeled after [Trystan's roguelike tutorial](https://trystans.blogspot.com/2016/01/roguelike-tutorial-00-table-of-contents.html).

For an example of APWT in action, check out my (abandoned) space roguelike [EverSector](https://github.com/Maugrift/EverSector).

## License

APWT is licensed under the MIT license. Basically, this lets you copy, modify, distribute, and sell the library or works that use it, privately or publicly, as long as you provide attribution and state your changes. See the LICENSE.txt file for the complete license.

## Contributing

I have ceased development on APWT because, 1) it is largely feature complete as-is and 2) I don't have much further use for it in my own projects.

If you are interested in adding features to APWT, please do NOT make a PR. Instead, fork this repository and maintain your own fork.
