# Assertions

## Math Expressions

Beatwall supports math expressions

```
# Creates a wall thats 50 units high
10 wall
    height = 10 + 10
    height *= 2
    height += 8
```

## Functions

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


```

## User-defined Functions

## Constants

