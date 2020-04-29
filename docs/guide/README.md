# Quickstart

## Installation

### Tools

- install [java](https://java.com/en/download/)
- install [beatwalls](https://github.com/spookyGh0st/beatwalls)
- install [mma2](https://bsmg.wiki/mapping/mediocre-map-assistant.html#editor-setup)

### mods
::: tip Info
can be installed through modAssistant, 
or [manually](https://bsmg.wiki/pc-modding.html#install-mods)
:::

- required: [Noodle Extension](https://github.com/Aeroluna/NoodleExtensions) - allows for wallmaps
- recommended: [Chroma](https://github.com/Aeroluna/chroma) - lets you color your walls
- highly recommended: [FPFCToggle](https://github.com/DeadlyKitten/FPFCToggle) - lets you fly around the map without vr
- highly recommended: [MusicEscape](https://github.com/DeadlyKitten/MusicEscape) - lets you quit to menu and pause-menu with your keyboard
- highly recommended: [ReLoader](https://github.com/Kylemc1413/ReLoader) - hot reloads the map from the pause menu
- highly recommended: [PracticePlugin](https://github.com/Kylemc1413/PracticePlugin) - change Song time, playback speed and more from the pause manu

## Usage

Drag a Song Folder into the .exe to launch the configuration setup. 
It will create a .bw File in your song folder. Open this in any text editor. Recommended is Visual Studio Code.
This File defines the created Wallstructures for the selected difficulty, like a cookbook.

The next time you run beatwalls simply double-click the exe.
::: tip Info
When you want to change some settings or pick a different Song, you need to redo the step above
:::

## Setup

[//]: # (TODO explain how to create a map with ne)

## Overview

### WallStructure 

A WallStructure is a collection of Walls. 
Bundling them together allows one to  edit and repeat them all at once to fit the Song, 
without having to manually edit each Wall. 
In Beatwalls you only work with Wallstructures

### Wall

To make working with Walls easier, I introduce to you a new [standard](https://xkcd.com/927) of Values, which define a wall. It defines 1 = one block.

![something like this](https://i.imgur.com/Uz7aIDg.png=100x100 )

A wall is defined by `x,y,z,width,height,duration`
::: tip Info
z and duration is measured in beats
:::

[//]: # (TODO add picture)

## Hello Wall

Now let`s create our first Structure
Open Beatwalls and leave it running.
Now write this into you .bw file.

```
# creates a noodle Helix at beat 10
10 NoodleHelix
    count = 2
```

Lets see what happens in this example
 * Lines starting with a `#` are comments and get ignored.
 * `10 NoodleHelix` creates a NoodleHelix at beat 10. 
 NoodleHelix is one of the base Wallstructures.
 * `count = 2` is an assertion of the Property `count`
 With this line our NoodleHelix will create 2 helixe instead of one.
 
[//]: # (TODO explain this better)
 
:::tip
Wallstructures starting with `Noodle` often make use of features like localRotation or rotation
:::

Now open Beatsaber and reload with ctrl r.
Start your map and you should see the Wallstructure appearing timed to beat 10
 
::: warning Timing
Beatwalls automatically makes sure your Wallstructures __apear__ and not zoom past you at the specific beat.
::: 

[//]: # (TODO add video)


    