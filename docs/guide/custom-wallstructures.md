# Interfaces and custom Structures

Copy-Pasting Structures with similar Properties is tedious and error-prone.
So there are some mechanism to help reduce duplicate code.
Note However Beatwalls is __NOT__ a scripting Language, it's okay if it gets a bit messy as long as the map looks good. 

## Repeating Structures

By using the `repeat` Property aswell as the variable `r` you repeat a structure and adjust certain value.
We have already seen it put to use to increase the beat, here is another example to add more splines to a helix

```yaml
helix:
    beat: 10
    repeat: 4
    startRotation: 90*r
```

## Interfaces

Interfaces hold properties for Structures with the **same name**.
All Structures **under them** inhered these properties.
A special case is the interface named `default` which get's applied to **all** Structures

```yaml
interface helix:
    beat: 49 + c*d
    mirror: 6
    type: Rectangle
    repeat: 4
    duration: 4
helix:
    radius: easeInOutQuad(3,9)

helix:
    radius: easeInOutQuad(9,3)
    type: Cuboid # You can still overwrite Properties

# Resetting the interface by filling it with no properties
interface helix:
```

## Custom Structures

You can combine and build on top of existing Wallstructures
It was written so you dont have to kill yourself making custom Walls and it is perfectly fine if the file gets messy and you copy paste Stuff.

```yaml
# left and right Wall of the tunel
# _tunnel1 is the name of our Wallstructure
# Wall is the base Wallstructure
define _tunnel1: 
    structures: Wall
    p0: 2,0,0
    p1: 2,4,1
    mirror: 2

# ceiling and ground 
define: _tunnel2
    structures: Wall
    p0: -2,4,0
    p1: 2,4,1
    mirror: 4
    
# you can add multiple Wallstructures and apply properties on all of them.
define tunnel: 
    structures: _tunnel1,_tunnel2
    color: green

# creating a 10 beats long tunnel
tunnel:
  beat: 10 * r
  repeat: 10
```

::: tip naming convention
Starting Names with underscores is a simple way of telling what Structures are used only in other Structures.
:::
