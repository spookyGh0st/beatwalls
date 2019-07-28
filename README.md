# beatwalls

commandline tool to add Walls to beatsaber maps using bookmarks

## Installation

Download the binary from the [latest release](https://github.com/spookyGh0st/beatwalls/releases) tab

## Usage

IMPORTANT: every Parametars are in Scale to the blocks. So a wall with a height of 1 will be one block high. You can use odd values for precise walls.

write one or more command in a bookmark at the time you want to take effect. The given parameters (exept scale) are added to the default parameters.
The Syntax is:

/bw NAME duration wallHeight startHeight lineOffset width startTime Scale

name: The name of the pattern you want to use

duration: The duration of each Wall in beats.

wallHeight: height of each wall.

startHeight: StartHeight of each wall

lineOffset: How many blocks a wall will be shifted.  __IMPORTANT:__ 0 is the Center (so original 0 would be -2)

width: The width of each wall

Scale: How much you want to Scale The WHOLE pattern. DOES ONLY AFFECT DURATION AND TIMING.


Then just drag whole Song folder or a difficulty On the programm.
## Roadmap

1.0 Create a working program with some example walls

later outsource the walls to a special file, so everyone can use their own walls. Maybe create a tool that automates this. Then you can load that file and use the walls created in there.

## License

have not added one but do whatever you want with this project.


