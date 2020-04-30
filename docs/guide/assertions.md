# Properties and Functions

Before we dive in, have some glossary
The line `height = 2` consists of 2 elements.
- Property: `height`
- Expressions: `2`

This line tells the program that the height of all walls should be changed to 2.
Often however more flexibility is needed.

It internally uses [Math parser](http://mathparser.org) so you can use everything there that is handled with Expressions.
Here are some things that stood out useful or have something special to them.

## Math Expressions

Beatwalls supports math expressions. 

```
# Creates a wall thats 50 units high
10 wall
    height = 2 + 3 * 4 ^ 5
    height += 2
    height *= 3
    height ^= 4
```

## Functions

Functions in Beatwalls take some numbers as input and return one. 
They can and should not assign properties. 

Beatwalls features [build in](http://mathparser.org/api/org/mariuszgromada/math/mxparser/mathcollection/MathFunctions.html)
functions for more advanced use cases.

::: tip
i always returns a double of 0..1 based on the progress of the wallstructure
:::

```
10 wall
    height = round(i)
```

They have also been enhanced by some functions I found lacking.
- all easing methods [here](https://easings.net/)
- random Functions

```
10 wall
    height = easeInSin(0,20)
    height += random(-2,2)
```

::: danger
Only Properties, that are used to __change__ existing Wallstructures and not Properties that are used to __create__ them have access to easing functions
:::

## User-defined Functions

User-defined functions can be created with the keyword `fun`.

```
fun myFunction(x,y) = sin(x) + 2 * y
10 wall
    height = myFunction(1,2) # we will talk about i later
```

## Constants

all build in Constants can be found in [this list](https://mathparser.org/mxparser-tutorial/build-in-constants) e.g. `pi`

## User-defined Constants

User-defined constants can be created with the keyword `const`.

```
const offset = 20
10 wall
    height = offset + 10
```

## Beatwalls specific Elements

Properties are visible to each other, so you can reference them.

```
10 wall
    height = width * 2
```

There are also some predefined variables to use. 
- references the current wall Properties`wall{x/y/z/width/height/duration}` 
- `i` -> 0 - 1 on the proggress of how many walls have been created.

```
10 wall
    height = sin(i*pi)
```

::: danger
Be cautions with `i` and `wall{x/y/z/width/height/duration}`.
Only Properties, that are used to __change__ existing Wallstructures and not all Properties that are used to __create__ them have access to them.
Therefore, some functions like easing do not work on all properties.
:::

