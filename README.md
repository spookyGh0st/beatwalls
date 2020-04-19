# Beatwalls

A separate tool to create awesome Wallshows in Beatsaber! Define entire Sections in a few lines and watch your maps get requested everywhere!

[showcase](https://streamable.com/felde),
[docs](https://spookygh0st.github.io/beatwalls/structure/-wall-structure/index.html), 
[examples](https://github.com/spookyGh0st/beatwalls/tree/master/examples).

## Prerequisites

- Have java installed

- Download the binary from the [latest release](https://github.com/spookyGh0st/beatwalls/releases) tab

- Have a song setup with mapping extensions already. You can find instructions somewhere in the bsmg discord.

## Usage

Drag a Song Folder into the .exe to launch the configuration setup. 
It will create a .bw File in your song folder. Open this in any text editor. Recommended is Visual Studio Code.
This File defines the created Wallstructures for the selected difficulty, like a cookbook.

# Syntax

### WallStructure 

A WallStructure is a collection of Walls. 
Bundling them together allows one to  edit and repeat them all at once to fit the Song, 
without having to manually edit each Wall. 
Now add a few of them, and you can have some really awesome ~~lag~~ awesome Walls, fitting the music.


### Wall

To make working with Walls easier, I introduce to you a new [standard](https://xkcd.com/927) of Values, which define a wall. It defines 1 = one line Index and the center = 0. Everything else scales accordingly.

![something like this](https://i.imgur.com/Uz7aIDg.png=100x100 )


```yaml
# start of the first chorus
10: Line
    p1: 3,0,0
    p2: 0,5,2.5
    amount: 16
    mirror: 2
    time: true
```

Lets walk through the start of this example line by line

```# start of The Intro```

Lines starting with a `#` are comments and get ignored

```10: Line```

This is our first Wallstructure. It consists of 2 parts:

`10`: the beat it will be created on
`Line`: The name of the Wallstructure

The program provides a set of predefined Wallstructures you can work with. 
Each Wallstructure has it own properties, and some properties all Wallstructures share.

Our line for example needs 2 Points, so the line will go from the startPoint to the EndPoint

`p1: 3,0,0` Point 1 -> The start point of the line. Imagine a 3-dimensional Point. 
- 3 -> means about 3 meters on the right side of the player
- 0 -> means a height of 0 meters, so basically at the Ground
- 0 -> will start 0 beats away from the player. But because the whole structure starts at 10, this means that the first wall will be created at the beat 10+0.

`p1: 0,5,2.5` Point 2 -> The end point of the line. so we are drawing a line from p1 to p2 
- 0 -> means 0 meters to the right from the player, so the center
- 5 -> means a height of 5 meters, about the highest one can go
- 2.5 -> will start 2 beats away from the player. The entire Line will now end at 10+2.5=12.5 beats

Now all Wallstructures share some common Properties. 
This one for example uses these 3:

`amount: 16 ` how many walls will be created -> creates 16 walls for this lane

`mirror: 2` mirrors the entire line to the other side, so we 2 lines will be created, one left and one right from the player.

`time: true` all Walls will have the halfjumpDurationOffset added, so they appear at the beat (and not pass by them). 

The `time` property is a special one, since it is enabled by default, meaning that all Wallstructures will appear on the beat you place them on. 
I see no reason why you would want anything else

These are just a few of all available parameters. Make sure you check the 
[documentation](https://spookygh0st.github.io/beatwalls/structure/-wall-structure/index.html), 
and the
[examples](https://github.com/spookyGh0st/beatwalls/tree/master/examples).
for everything you can work with.


### Defining you own structures

Writing a few lines of code some walls is pretty great, but how about storing and reusing them?

Define is a special structure one for advanced uses. You can use this to to reuse complex structures without having to write them all down. They must be written in the order they are used.

Example:
```yaml
define: _tunnel1
    structures: wall
    startRow: -4
    width: 8

define: _tunnel2
    structures: wall
    startRow: -4
    startHeight: 4
    width: 8

define: _tunnel3
    structures: wall
    startRow: -4
    Height: 4

define: _tunnel4
    structures: wall
    startRow: 4
    Height: 4
    
define: tunnel
    structures: _tunnel1,_tunnel2,_tunnel3,tunnel4
```

now you can create a bunch of tunnels at times 10, 12, 16.6 and 20 with just

```yaml
10: tunnel
12: tunnel
16.6: tunnel
# a tunnel, which is twice as long
20: tunel
    scale: 2
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

## more syntax

- Beatwalls is ***NOT*** case sensitive
- Beatwalls does ***NOT*** care about spaces or tabs, or any 
- Beatwalls does ***NOT*** work with non-ascii characters
- Beatwalls works with BEATS (the thing in mm) and not TIMES, so no need to worry about bpm changes
- Beatwalls has ***NO*** Syntax checker currently and might crash when something weird happens. Please write me when it does.

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
``--deleteAllPrevious`` ->  deletes all Walls and notes in the difficulty, and not just the one created by beatwalls before creating walls/bombs.
``--noUpdate`` -> dont auto update

## other stuff

#### Randomness

some values allow the use of random. You can set the seed for this by using 
```yaml
randomSeed: 10
changeHeight: random(0,5) # will be the same every run
```
this will sed the seed for every random Call **below** that line to the given seed. 
this can be done multiple times.

some **Wallstructures** (not properties) also use random. These use the variable seed for each Structure
```yaml
12: RandomNoise
    seed: 420
```

#### Feedback

I really want to hear about any problem you may run into. Write me.

Please ping me on discord (spookyGhost) about any feedback or any wallstructures you would like to see added. Or submit a pull request yourself

Windows will probably annoy you that this program is dangerous. If you dont trust me, build from source and check the shasum
