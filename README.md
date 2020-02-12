# beatwalls

Commandline tool to add Walls to beatsaber maps using bookmarks. [showcase](https://streamable.com/felde),
 [docs](https://spookygh0st.github.io/beatwalls/structure/-wall-structure/index.html), [examples](https://github.com/spookyGh0st/beatwalls/tree/master/examples)

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
This File defines the created Wallstructures for the selected difficulty, like a cookbook. Here is an example:

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

The program provides a set of predefined Wallstructures you can build on. The are limited in numbers at the moment, but very flexible. Also I plan to update the program to add more in the future. Lets move on to the next lines

```yaml
amount: 10
time: true
repeat: 10
```

These are properties for our created WallStructure. You can find all available parameters in the documentation linked at the end.

- amount: 10 -> create 10 small structs
- time: true -> times the wall, so they appear with the beat

### Repeat

The repeat parameter allows you to repeat the walls of the Structure. 

`repeat: 10` says, that this structure will be created 10 times.
Each time it will be shifted be the amounts in 

- repeatAddX(default: 0)
- repeatAddY(default: 0) 
- repeatAddZ (default: 1)

One gotcha: This is just copy and paste. So 5 repeatings of a struct with Randomness will create the same struct 5 times.

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




### Point

Some structures use Points as properties. These are simply 3-dimensional Points in space. With

- x= startRow (line index)
- y= startHeight 
- z= startTime, relative to the structure

to create a Point use

```$x,$y,$z```

a normal curve might look like this:

```yaml
30: curve
    amount:16
    p1:0,0,0
    p2:2,0,0.33
    p3:2,0,0.66
    p4:2,4,1
```

### Default

you want to have a whole section of structures hyperWall or make them all 1 height taller.
Everything you define under a default. Will be have the default value of the thing you define.

````yaml
15.0: default 
    changeDuration: -3
    
10: line  
    p1: 0,0,0
    p2: 3,3,1
    
0: default 
    changeDuration: null
````

this sets the changedurationparameter to -3 to default, draws the
note that default still has a beat attached to it. It does not matter at all. only the location in the file matters.

## Documentation

The documentation for this program is automatically updated when i update the program. However it can be dawning to read at first. 

Open this link in a different browser window:
--> [docs](https://spookygh0st.github.io/beatwalls/structure/-wall-structure/index.html) <--

All Wallstructures have a set of default-properties. These are the one defined under Properties, like addDuration, scale, time or mirror.

If you scroll down to the bottom of the page, you will find the Inheritors. These are the actual Wallstructures you can create (at the point of writting this curve, define, helix, randomNoise and wall).

Each of the Structures have their own properties that configure how the walls are created. Lets click on the in my opinion most interesting one, the Curve:
You can see that it has 5 properties: amount, p1, p2, p3 and p4. with descriptions on what they do.

### Execution

After the initial config, all Wallstructures are only defined in the .bw file. To create them, just execute the exe again (without dragging in a folder).

This will delete all walls created buy the previous runs and create the new ones according to your .bw file

this is what will be created by the first example: [video](https://streamable.com/s/md58n)

To select a different Song just drag in the Folder again.
There are also cl options for more advanced user

``--clearAll`` -> deletes all previous from bw created Walls without creating no walls
``--deleteAllPrevious`` ->  deletes all Walls in the difficulty, and not just the one created by beatwalls.

## other stuff

#### Randomness

some values allow the use of random. You can set the seed for this by using 
```yaml
randomSeed: 10
changeHeight: random(0,5) # will be the same every run
```
this will sed the seed for every random Call **below** that line to the given seed. 
this can be done multiple times.

some **Wallstructures** also use random. These use the variable seed for each Structure
```yaml
12: RandomNoise
    seed: 420
```

#### Feedback

I really want to hear about any problem you may run into. Write me.

Please ping me on discord (spookyGhost) about any feedback or any wallstructures you would like to see added. Or submit a pull request yourself

Windows will probably annoy you that this program is dangerous. If you dont trust me, build from source and check the shasum
