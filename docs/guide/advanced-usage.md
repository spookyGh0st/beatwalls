# Advanced Usage

## Includes

When the main.bw file might become to big you can split up your files and include them.

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
Imported files are **locally scoped**.
Interfaces, Custom Structures, etc are **private per file** and will not be shared.
:::
