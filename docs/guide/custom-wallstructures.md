# Custom Wallstructures

You can combine and build on top of existing Wallstructures
This is a good way to reduce duplicated Code.

Example:
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
tunnel: _tunnel1,_tunnel2,

# creating a tunel
10 tunnel
```
[//]: # (TODO find a better example)

::: tip naming convention
Starting Names with underscores is a simple way of telling what Wallstructures are used only in other Structures,
and not called uppon
:::

::: danger Overriding Assertions
You can still override Assertions.
```
10 tunnel
    p1 = 1,1,1
```
:::
