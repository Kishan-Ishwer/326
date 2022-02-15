# Ants

A variation on langstons ant where squares can be more than just black or white, and rules on how the ant behaves while moving on the grid world are determined by the input of rules

# Input

Input Structure:


|# Ant Name

|# Rules

|# Steps


Examples:


|# ?????

|. EEEE xxxx

|y SEWN x.!y

|# Ignore me!

|x WEEE !!!!

|! NWES .yx!

|12

The first value in each rule is the colour/symbol of the tile the ant is currently on
The next 4 values and the last set of 4 value are used to determine what happens to the tile the ant is leaving, based on the direction the ant is leaving. the first set of 4 values in the direction the ant leaves a tile, the second set of 4 is the color/symbol the tile becomes based on its corrosponding value in the first set fo 4.
