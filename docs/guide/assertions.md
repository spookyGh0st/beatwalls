# Assertions

## Math Expressions

Beatwalls supports math expressions. 
It internally uses [Math parser](http://mathparser.org) so you can use everything described in Exrpressions there.
Here are some examples.

```
# Creates a wall thats 50 units high
10 wall
    height = 2 + 3 * 4 ^ 5
    height += 2
    height *= 3
    height ^= 4
```

## Functions

Functions in Beatwalls take some numbers and return one. 
They can and should not assign properties. 

Beatwalls supports a bunch of functions to make your live easier in functions.
a list of can be [here](http://mathparser.org/api/org/mariuszgromada/math/mxparser/mathcollection/MathFunctions.html)

::: tip
i always returns a double of 0..1 based on the progress of the wallstructure
:::

```
10 wall
    height = round(i)
```

It also has some all Easing functions, that can be found [here](https://easings.net/)

```
10 wall
    height = easeInSin(0,20)
```

::: danger
Only Properties, that are used to __change__ existing Wallstructures and not Properties that are used to __create__ them have access to easing functions
:::

## User-defined Functions

It is possible to create Functions yourself.
You cannot change a function after it has been created.

```
fun myFunction(x,y) = sin(x) + 2 * y
10 wall
    height = myFunction(i,2)
```

## Constants

It is possible to create Constants yourself.
You cannot change a Constant after it has been created.

```
const offset = 20
10 wall
    height = offset + 10
```

Properties are also visible to each other, so you can reference them.

```
10 wall
    height = width * 2
```

There are also __default__ variables you can use, 
for example the previous talked about `i`.

- all default Constants found in [this list](https://mathparser.org/mxparser-tutorial/build-in-constants) e.g. `pi`
- references the current wall Properties`wall{x/y/z/width/height/duration}` 
- `i`, 0 - 1 on the proggress of how many walls have been created.

::: danger
Be cautions with `i` and `wall{x/y/z/width/height/duration}`.
Only Properties, that are used to __change__ existing Wallstructures and not Properties that are used to __create__ them have access to them.
Therefore, some functions like easing do not work on all properties.
:::

