A tool to create [wallmaps](https://www.youtube.com/watch?v=t4bk8ym3fIQ) for Beat Saber.
Check out the docs under https://spooky.moe/beatwalls.

Created with <3 by your friendly ghost.

Change Log:
- **BREAKING:** Switch from 0-1 values for colors to 0-255 since it's more used on color pickers.
- Add Property `colorAlpha` to overwrite the alpha values of colors in a structure.
- Reintroduce Property `splineAmount` to ease the creation of helixes. (in some versions called `count`)
- Add alias `b` for `beat` since i hate typing.
- fixed a bug that didn't allow rainbows without repetitions number (defaults to 1 now)
