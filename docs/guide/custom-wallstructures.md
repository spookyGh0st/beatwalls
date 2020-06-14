# Nested Wallstructures and Interfaces

It is best to avoid duplicates, when you can.
This leads to Code, that you can easily change and adjust to maps.
To help you with this Beatwalls has some features you might recognized when you worked with object-oriented programming before.

## Custom Wallstructures

You can combine and build on top of existing Wallstructures
This is a good way to reduce duplicated Code. 
However Beatwalls is __NOT__ a scripting Language.
It was written so you dont have to kill yourself making custom Walls and it is perfectly fine if the file gets messy and you copy paste Stuff.

```
# Bottom and top Wall of the tunel
# _tunnel1 is the name of our Wallstructure
# Wall is the base Wallstructure
_tunnel1: Wall
    x = -2
    width = 4
    mirror = 6

# left and right Wall
 _tunnel2: Wall
    x = 4
    height: 4
    mirror: 2
    
# you can add multiple Wallstructures
tunnel: _tunnel1,_tunnel2

# creating a tunel
10 tunnel
```

::: tip naming convention
Starting Names with underscores is a simple way of telling what Wallstructures are used only in other Structures.
:::

::: tip Overriding Assertions
You can still override Assertions.
```
myline: Line
    p1 = 10,0,0
10 myLine
    p1 = 10,5,0
```
:::

## Interfaces

Interfaces hold assertions for Wallstructures.

```
# create
interface fast
    duration = -2
interface point
    width = 0
    height = 0

# has duration set to -2
# has width and height 0
10 tunnel: fast, point
```

You can also create nested Interfaces

```
interface fastpoints: fast, point
10 tunnel: fastpoints
```

You can use Interfaces on custom Wallstructures

```
struct pointLine: Line, fastpoints
    p1: -10,0,0
    p2: 2,10,4
10 pointLine
```

## Default Interface

The default Interface is one __all__ Wallstructures have.
You can overwrite it to set certain Values for all Walls.

```
# override the default Interface
interface default
    duration = -3
# will have its duration set to -3
10 wall
```


## Repeating Wallstructures

Since writing A wallstructure multiple way gets annoying there is a way to repeat it.
Lets first make our WallStructure, which will be half an helix going from the right to the left side.

```text
struct myH: NoodleHelix
    radius = 10
    rotationAmount = 180
    count = 1
```

The way repeat works is that an Variable `repeatCounter` is added which counts up as we repeat.
Lets make our Wallstructure repeat every second Beat for example.

```text
10 myH
    repeat = 8
    z += repeatCounter * 2
```

Because can get annoying there is also a build in way for better readability:

```text
# this is the same as above
10 myH
    repeat = 8
    repeat z += 2

11 myH
    repeat = 8
    repeat z += 2
```
