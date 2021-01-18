# Advanced Usage

## Includes

When the main.bw file might become to big you can split up your files and include them.
Note that the program will still only rerun when you modify main.bw.

EasyStandard.bw
```yaml
include: intro.bw
include: middle.bw
include: end.bw
```

intro.bw
```yaml
helix:
  beat: 10
```

middle.bw
```yaml
helix
  beat: 20
```

etc.

::: danger NOTE
Imported files currently are merged into one at when running.
Custom Structures, Interfaces etc are not private per file and will be shared.
This may have unintended results if the same name is used for different structures
or parameters between files.
:::
