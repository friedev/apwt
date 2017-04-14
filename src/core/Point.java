package core;

/** A point on a map, represented with two coordinates: x and y. */
public class Point
{
    /** The x coordinate of the point. */
    public int x;
    
    /** The y coordinate of the point. */
    public int y;
    
    /** Creates a new point at (0, 0). */
    public Point()
        {this(0, 0);}
    
    /**
     * Copying constructor that creates a point at the same coordinates as
     * another point.
     * @param p the point whose coordinates will be used in the construction of
     * this point
     */
    public Point(Point p)
        {this(p.x, p.y);}
    
    /**
     * Creates a point with the given x and y coordinates.
     * @param xx the x coordinate of the point
     * @param yy the y coordinate of the point
     */
    public Point(int xx, int yy)
    {
        x = xx;
        y = yy;
    }
    
    /**
     * Creates a point from an ordered pair in String form.
     * @param pair the ordered pair as a String
     */
    public Point(String pair)
    {
        pair = pair.replace("(", "");
        pair = pair.replace(")", "");
        pair = pair.replace(",", "");
        String[] split = pair.split(" ");
        
        x = core.Prompt.parseInt(split[0], 0);
        y = core.Prompt.parseInt(split[1], 0);
    }
    
    @Override
    public String toString() {return "(" + x + ", " + y + ")";}
    
    /**
     * Sets the coordinates to the given ones.
     * @param xx the new x coordinate of the point
     * @param yy the new y coordinate of the point
     */
    public void set(int xx, int yy)
    {
        x = xx;
        y = yy;
    }
    
    /**
     * Returns true if another entered point has the same coordinates as this
     * point.
     * @param point the point to compare for equality, must be non-null
     * @return true if the x coordinates and y coordinates are equal across
     * both points
     */
    public boolean equals(Point point)
    {
        if (point == null)
            return false;
        
        return point.x == x && point.y == y;
    }
    
    /**
     * Returns true if another specified point is exactly one space away from
     * this point.
     * @param point the point to compare for adjacency, must be non-null
     * @return true if a coordinate of the point deviates exactly one cardinal
     * space from the corresponding coordinate on this point, assuming the other
     * coordinate does not
     */
    public boolean isAdjacentTo(Point point)
        {return orthagonalDistanceTo(point) == 1;}
    
    /**
     * Returns the distance to another point, moving only on the x and y plane
     * by one unit at a time.
     * @param point the point to get the distance to, must be non-null
     * @return 
     */
    public int orthagonalDistanceTo(Point point)
    {
        if (point == null)
            return -1;
        
        return Math.abs(x - point.x) + Math.abs(y - point.y);
    }
}