# beatwalls

commandline tool to add Walls to beatsaber maps using bookmarks very first [showcase](https://streamable.com/8lx94)

## Installation
- have java installed

- Download the binary from the [latest release](https://github.com/spookyGh0st/beatwalls/releases) tab

- This program works with bookmarks, which are currently only supported by Mediocre Mapper. So you need that.

# BACK UP EVERYTHING

## Usage

- you have to add a bookmark with your chosen commands at the Beat you want your walls to be. 
one command consists of 

`/bw $name $scale $repeatCount $repeatGap $PARAMETERS`

check out the [Wiki Page](https://github.com/spookyGh0st/beatwalls/wiki) for example Usage

- Then just drag whole song folder on the beatwalls.exe. It will automatically create the BeatwallAsset File the first time it is run.
_(or open it in a cli)_

***WARNING*** by default this will delete all previous obstacles. Look at the help page, if you want to keep them

### Parameters

If you want to finetune the walls you can do so by adjusting the Parameters of the Pattern. Each Value will get added to every wall in the pattern. This is done before Scale and Repeat cound are added. The Parameters are as follows:

- duration: The duration of each Wall in beats.

- wallHeight: height of each wall.

- startHeight: StartHeight of each wall

- startRow: How many blocks a wall will be shifted.  __IMPORTANT:__ 0 is the Center (so original 0 would be -2)

- width: The width of each wall

### The BeatwallAsset File

The Asset File is the File, where most of the CustomWallStructures are saved! You are invited to look into it, change it and add new structures.

If you create Some cool new structures, please send me, so I can add them to the default asset file!

### special WallStructures

some Special WallStructures have can have options that are not covered by the default Parameters. to use them put them in

`/bw $name -- specialOptions -- $scale $repeatCount ...` 

note that You need to have them at the beginning. They can enable more advanced WallStructures like RandomLines and soon text.

## Plans

1.0 Create a working program with some decent example files.

later: create a text walls, create a converter from original obstacle to my layout, add more Structures.



