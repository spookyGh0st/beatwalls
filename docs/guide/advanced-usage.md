# Advanced Usage

## Imports

As time goes on more and more lines gets added, 
and the file might become impractical to handle.

For this reason you can import other files.
For example, it might be practical to differentiate between different parts of the song.

EasyStandard.bw
```
import: start.bw
import: middle.bw
import: end.bw
```

start.bw
```
0.0 line
    p1: -10,0,0
    p2: 10,10,2
...
```

middle.bw
```
50.0 line
    p1: -10,0,0
    p2: 10,10,2
...
```

etc.

::: danger
The files simply get merged at runtime, custom Structures, interfaces, etc are not private per file.
:::

## Command Line Options

There are also cl options for more advanced user

- ``--clearAll`` -> deletes all previous from bw created Walls without creating no walls
- ``--deleteAllPrevious`` ->  deletes all Walls and notes in the difficulty, and not just the one created by beatwalls before creating walls/bombs.
- ``--noUpdate`` -> don't auto update

