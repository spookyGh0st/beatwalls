# Properties and Functions

Before we dive in, have some glossary
The line `changeX: 2` consists of 2 elements.
- Property: `changeX`
- Value: `2`

This line tells the program that the x-position of all Walls gets set to 2.

## Math Expressions

Beatwalls supports math expressions. 

```
helix:
    changeWidth: 2+3*4^5%6
    changeWidth: pi+3
```

## Functions

Functions in Beatwalls take some numbers as input and return one number as output.
The Function types in included are:
- common Math functions like `sin(x), ceil(x), log(x), sqrt(x), etc...`  found [here](https://www.objecthunter.net/exp4j/#Built-in_functions)
- all easing methods [here](https://easings.net/), e.g. `easeInOUtQuad(min, max)`
- random(min,max)

```yaml
line:
    beat: 10
    addWidth: easeInOutQuad(4,0) # start with 10, ease down to 0
    changeHeight: random(0, 2)   # randomly change the height to a value between 0 and 2
```

## Variables

There are certain variables set at runtime:
- math variables: `pi, e`
- `p` (progress): Value of 0..1 on how many Walls of the structure have been affected.
When you have a structure with 4 walls, `p` will be 0 on the first wall, `0.25` on the second, etc...
- `r` (repetitions): 0 on the first repetition, 1 on the second, etc. More on repetitions later.
- `d` (duration): Value of the `duration` Property.

```yaml
helix:
    beat: 10 + r*d # total this Structure will be 16 beat long
    repeat: 4
    duration: 4
    addWidth: sin(pi*p)
```

::: warning
Not all properties that are used to __create__ wallstructures have access to easing functions and `p`.
:::

## User defined Variables

You can create your own variables

```yaml
variable offset:
    value: 20
helix:
    beat: 10 + offset
```
