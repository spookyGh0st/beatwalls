# Curve/Steadycurve

[video](https://youtu.be/eowLTGFseuk) of all Wallstructures in this file

All curves are controlled using Points. 
These are fairly intuitive when using, but might be difficult when first seeing. 
Check out this [**this link**](https://youtu.be/eowLTGFseuk) to play around.


## Example 1

![I should be a picture](https://github.com/spookyGh0st/beatwalls/blob/master/examples/pictures/curve/10.png)

```yaml
# Steadycurve stretches the points over 1 beat
# most of the time it is easier to use then normal curve
10: Steadycurve
    p1: 8,0   
    p2: 8,6
    p3: -8,6
    p4: -8.0
    fitStartHeight: 8   # all fit to the startHeight 8.
    mirror: 2           # mirrors on the x axis
    amount: 8           # use 8 walls for this structure.
    scale: 2.0          # stretch the structure over 2 beats.
```

--- 

## Example 2

![I should be a picture](https://github.com/spookyGh0st/beatwalls/blob/master/examples/pictures/curve/20.png)

```yaml
# the normal curve can use z-values for finer control
# the default amount should be fine, it automatically gets it from the duration, 
# so we dont set it here
20: curve
    p1: 0,0,0
    p2: 2,0,0.5
    p3: 3,0,1
    p4: 3,2,2       
    mirror: 8         # mirors both on the x and y axis
```

--- 

## Example 3

![I should be a picture](https://github.com/spookyGh0st/beatwalls/blob/master/examples/pictures/curve/30.png)

```yaml
# This structure uses hyperwalls.
# those are the really fast walls. 
# They are created using negative duration
30: Steadycurve
    p1: 20,0
    p2: 8,0
    p3: 4,2
    p4: 4,8
    fitStartHeight: 0     # every wallstructure is grounded
    changeWidth: 0        # Changes the width of every wall to 0
    scale: 8              # Stretch over 8 beats
    amount: 64            # use 64 walls
    changeDuration: -3    # make hyper walls
    mirror: 2             # mirror on the other side
```