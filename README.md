# beatwalls

commandline tool to add Walls to beatsaber maps using bookmarks. [showcase](https://streamable.com/felde)

## Installation

- Download the binary from the [latest release](https://github.com/spookyGh0st/beatwalls/releases) tab

- This program works with bookmarks, which are currently only supported by Mediocre Mapper or json editing. So you need that.

## Introduction

The goal of the Beatwalls Program is to make working with mapping extension walls easier and enable everyone to use cool Walls in their Maps. It is designed to be used easily with good defaults, but still enables lots of options for interested Users.

### A WallStructure 

A WallStructure is a collection of Walls. Bundling them together allows one to scale, repeat and edit them all at once to fit the Song, without having to manually edit each Wall. Combine multiple Wallstructures, to create amazing scenery for your Map.

### A Wall

To make working with Walls easier, I introduce to you a new [standard](https://xkcd.com/927) of Values, which define a wall. It defines 1 = one line Index and the center = 0. Everything else scales accordingly.

![something like this](https://i.imgur.com/Uz7aIDg.png=100x100 )

## Usage

Drag a Song Folder into the .exe to launch the configuration setup. 

After it run successful You will find a created Default File in your Folder of the selected Song.
This File defines the walls for the selected difficulty, like a cookbook. Here is an example:

```
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

lets walk through the start of this example line by line

```# start of The Intro```

Lines starting with a `#` are comments and get ignored

```10: RandomNoise```

This is our first Wallstructure. It consists of 2 parts:
- 10: the beat it will be created on
- RandomNoise: The name of the Wallstructure

So just with that line we will create a default RandomNoise structure on beat 10.

```
amount: 10
time: true
repeat: 10
```

These are option for our created WallStructure. 

- amount: 10 -> create 10 small structs
- time: true -> times the wall, so they appear with the beat
- repeat: 10 -> repeat the created Walls (not the WallStructure) 10 times.

Now to create the walls, save and execute beatwalls.exe. 
It will automatically delete old Wallstructures created by the program, so you dont need to worry about overriding those.

this is what will be created: [video](https://streamable.com/s/md58n)

To select a different File just drag in the Folder again.

To find out all currently possible options and structures, head to this link:
You care about the properties and Inheritors.

--> [docs](https://spookygh0st.github.io/beatwalls/structure/-wall-structure/index.html) <--

## other stuff

If you use this program, please let me of it and tell me your feedback. I plan on updating it frequently

Windows will probably annoy you that this program is dangerous. If you dont want this, build from source or buy me a certificate



