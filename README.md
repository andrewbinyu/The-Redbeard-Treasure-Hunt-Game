# The-Redbeard-Treasure-Hunt-Game
## Game introduction
The famous pirate Redbeard master ship sunk in 17-th century in the Sea Of 1000 Islands after a fierce battle with Bluebeard’s ship. The sunken ship contains a large bounty. You are navigating the Sea Of 1000 Islands in your ship provided with modern technology. The Sea Of 1000 Islands has been mapped into a grid where a navigable square is denoted by a dot (”.”), and a square that belongs to one of many islands is denoted by a ”+”.

You have a number of sonar devices. If you drop a sonar device from your ship, if the treasure is in the range of sonar, the device will give you the grid coordinates of the treasure. Once you have the coordinates, your AI engine will run a clever algorithm, called A-star search algorithm, that is capable of plotting the shortest way from the current position of your ship to the detected treasure avoiding the obstacles (in this case, the obstacles are the numerous islands).

If the sonar device detects nothing, you can navigate in any direction you like (north, south, east, west, north-east, north-west, south-east, south-west) one navigable square at a time. You can move as many squares as you like. Whenever you feel like it, you drop another sonar. And so on until the treasure has been found, or you run out of the sonars. Please note once a sonar has been dropped, it is not recoverable. The goal of the game is to find the treasure and plot the navigation route to the treasure. If you run out of the sonar devices before finding the treasure, you lose the game.

A copy of the map, including the plotted trip to the treasure, is shown below. The dots indicate navigable squares, the ”+” signs indicate obstacles, the letter ”B” indicates the position of the ship, the letter ”T” indicates the position of the treasure. The sequence of ”*” signs indicates the path plotted by the A-star search algorithm. The jagged red line just follows the ”*” symbols to emphasize the path.

## Algorithms used in this game
### The A-star path search algorithm
The A-star path search adjoins next cell to the path by chosing a cell that is ”open” (see the links below) and satisfies the following condition: the sum of distances of that cell from the origin and the destination must be minimal. The details of the algorithm including the pseudocode can be found on the internet. The next image shows how the actual sum of distances is computed. Please note if we assume one cell has side length one unit, a vertical or horizontal distance from one cell to next is one unit, whereas a diagonal distance (for example from current cell to the cell located north-east is \sqrt{2}) or approx. 1.4 units. In order to avoid decimals from or computation, we will multiply everything by 10, so for instance the distance of the cell painted in red is computed as follows: the red cell is one (diagonal) square far from cell labelled ”A”, that is 14 unit, and two (diagonal) squares from cell labelled ”B” therefore 28 units,
### Heap
Heaps are complete binary trees that satisfy these conditions:
1. The smallest value (or highest priority) element sits at the root. 2. Every subtree of a heap is also a heap.
Since a heap is a complete tree, all its levels but the last are complete. The last level may miss some leaves in the rightmost position. An example of a heap is shown in the next image.
Observe that the root (that is the lowest value element - in that case the letter ”J” of course assuming the highest value letter of the alphabet is ”A” and the lowest value letter of the alphabet is ”Z”) is mapped (in the implementation) to the lement with index 0 of the array to the right. Its immediate children occupy the cells 1 and 2 of the array. The children of the level 1 occupy elements 3,4,5,6. And so son.
When we use this representation of a binary tree, the following relationships hold for an element at position index:
• If the element is not the root, its parent is at position (index 1) / 2.
• If the element has a left child, the child is at position (index * 2) + 1.
• If the element has a right child, the child it is at position (index * 2) + 2.
