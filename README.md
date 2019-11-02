# beatwalls

Commandline tool to add Walls to beatsaber maps using bookmarks. [showcase](https://streamable.com/felde)

## Installation

- Have java installed

- Download the binary from the [latest release](https://github.com/spookyGh0st/beatwalls/releases) tab

## Introduction

The goal of the Beatwalls Program is to make working with mapping extension walls easier and enable everyone to use cool Walls in their Maps. It is designed to be used easily with good defaults, but still enables lots of options for interested Users.

### A WallStructure 

A WallStructure is a collection of Walls. Bundling them together allows one to scale, repeat and edit them all at once to fit the Song, without having to manually edit each Wall. Combine multiple Wallstructures, to create amazing scenery for your Map.

### A Wall

To make working with Walls easier, I introduce to you a new [standard](https://xkcd.com/927) of Values, which define a wall. It defines 1 = one line Index and the center = 0. Everything else scales accordingly.

![something like this](https://i.imgur.com/Uz7aIDg.png=100x100 )

## Usage

Drag a Song Folder into the .exe to launch the configuration setup. 
It will create a .bw File in your song folder. Open this in any text editor. Recommended is Visual Studio Code.
This File defines the walls for the selected difficulty, like a cookbook. Here is an example:

```yaml
# start of the intro
10.0: RandomNoise
    amount: 10
    time: true
    repeat: 10
    
# start of the first chorus
20: Helix
    count: 3
    repeat: 10
    time: true
```

Lets walk through the start of this example line by line

```# start of The Intro```

Lines starting with a `#` are comments and get ignored

```10: RandomNoise```

This is our first Wallstructure. It consists of 2 parts:
- 10: the beat it will be created on
- RandomNoise: The name of the Wallstructure

So just with that line we will create a default RandomNoise structure on beat 10.

```yaml
amount: 10
time: true
repeat: 10
```

These are parameters for our created WallStructure. You can find all available parameters in the documentation linked at the end.

- amount: 10 -> create 10 small structs
- time: true -> times the wall, so they appear with the beat

### Repeat

The repeat parameter allows you to repeat the walls of the Structure. 

`repeat: 10` says, that this structure will be created 10 times.
There are also some more advanced features you can read about in the documentation linked at the end.

### Defining you own structures

The Wallstructure Define is a special one for advanced uses. You can use this to to reuse complex structures without having to write them all down. They must be written in the order they are used.

Example:
```yaml
define: _tunel1
    structures: wall
    startRow:-4
    width: 8

define: _tunel2
    structures: wall
    startRow:-4
    startHeight:4
    width: 8

define: _tunel3
    structures: wall
    startRow: -4
    Height: 4

define: _tunel4
    structures: wall
    startRow: 4
    Height: 4
    
define: tunel
    structures: _tunel1,_tunel2,_tunel3,tunel4
```
now you can create a tunel at time 10 with
```yaml
10:tunel
```



### Execution

Now to create the walls, save and execute beatwalls.exe. 
It will automatically delete old Wallstructures created by the program, so you dont need to worry about overriding those.

this is what will be created: [video](https://streamable.com/s/md58n)

To select a different Song just drag in the Folder again.

To find out all currently possible options and structures, head to this link:
The properties are available to all structures. The Inheritors are all available Structures. To learn more about it, just click on it.

--> [docs](https://spookygh0st.github.io/beatwalls/structure/-wall-structure/index.html) <--



## other stuff

Please message me on discord (spookyGhost) about any feedback or any wallstructures you would like to see added.

Windows will probably annoy you that this program is dangerous. If you dont want this, build from source or buy me a certificate



