# RandomNoise

[video](https://youtu.be/pwFA2ul9RBk) of all Wallstructures in this file

creates small random points in a given area.


## Example 1

![I should be a picture](https://github.com/spookyGh0st/beatwalls/blob/master/examples/pictures/RandomNoise/10.png)

```yaml
# Random Noise over 4 beats
10: RandomNoise
    p1: -4,0,0
    p2: 4,4,4
```

--- 

## Example 2

![I should be a picture](https://github.com/spookyGh0st/beatwalls/blob/master/examples/pictures/RandomNoise/20.png)

note: pictures use less walls to make understanding them easier

```yaml
# create laser-like effects on the sides.
# This uses negative duration for hyper walls.
20: RandomNoise
    p1: 3,0,0
    p2: 4,4,4
    amount: 32          # create 32 walls
    changeDuration: -3  # negative duration
    mirror: 6           # mirror on 0/2, this leads to more random looking walls.
```

--- 

## Example 3

![I should be a picture](https://github.com/spookyGh0st/beatwalls/blob/master/examples/pictures/RandomNoise/30.png)

note: pictures use less walls to make understanding them easier

```yaml
# creates some weird floor and ceiling effects
# this structures makes heavy use of the random feature. 
# Not all parametes support this tho. check the documentation
30: RandomNoise
    p1: -8,0,0
    p2: 8,0,4
    amount: 16   # create 16 walls
    repeat: 2   # but create the structure 2 times
    mirror: 4
    changeDuration: random(-2,-3)
    changeWidth: random(0,0.5)
```
