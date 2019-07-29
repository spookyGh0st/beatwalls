# beatwalls

commandline tool to add Walls to beatsaber maps using bookmarks

## Installation
- have java installed

- Download the binary from the [latest release](https://github.com/spookyGh0st/beatwalls/releases) tab

# BACK UP EVERYTHING

## Usage

This program works with bookmarks, which are currently only supported by Mediocre Mapper. 
Then you simply have to add a bookmark with one or more commands on the timing you want your walls to be. 
one command consists of 

___/bw $name $scale $repeat PARAMETERS___

check out the examples //LINK Document for more examples

Then just drag whole Song folder or a difficulty on the beatwalls.exe File.
_(or open it in a cli)_

__WARNING__ by default this will delete all previous obstacles. Look at the help page, if you want to keep them

### Parameters

If you want to finetune the walls you can do so by adjusting the Parameters of the Pattern. Each Value will get added to every wall in the pattern. This is done before Scale and Repeat cound are added. The Parameters are as follows:

- duration: The duration of each Wall in beats.

- wallHeight: height of each wall.

- startHeight: StartHeight of each wall

- lineOffset: How many blocks a wall will be shifted.  __IMPORTANT:__ 0 is the Center (so original 0 would be -2)

- width: The width of each wall

## Plans

1.0 Create a working program with some decent example files.

later: create a text generator



