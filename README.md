##Java Code Example
Michael Ventoso
24 June 2020

BoundingBox:
We were given the following API to implement as we saw fit, with the rule that top(), bottom(), left(), and right() should run in constant time.

The `BoundingBox` Interface
    public class BoundingBox
              BoundingBox()        create an empty bounding box
         void add(Point2D item)    add a point to the bounding box
      boolean isEmpty()            is the bounding box empty?
          int size()               number of items in the bounding box
      Point2D top()                the top-most point (max y)
      Point2D bottom()             the bottom-most point (min y) 
      Point2D left()               the left-most point (min x)
      Point2D right()              the right-most point (max x)
      Point2D centroid()           the centroid (mean) of the points
      Iterator iterator()          iterator that provides all the points in the BB
      
      
We were also given the ConvexHull class in a completed state, and were told to implement Stack2 and MinPQMultiway:

This program [ConvexHull] assumes two classes exist:

1. `Stack2` -- A stack that implements `peek` as well as `sneakPeek` . These methods return the first item and second item on the stack without popping (not the north most point).
2. `MinPQMultiway` -- A heap of objects which are removed in sorted order. Beyond the binary heap covered in the textbook (pg. 318), this version allows for multi-way branching. 



The main for BoundingBox takes two integers as parameters.  The first is how many random points will be plotted, and the second is the size of the plot.
Points will only be made within the boundaries of the plot's given size.

The main for ConvexHull requires three integer parameters from standard input.  The first two are for the width and height of the plot and the third is how many random points will
be plotted.
