package map;

/** A point on a map, represented with two coordinates: x and y. */
public class Point
{
    int x, y;
    
    public Point() {this(0, 0);}
    
    public Point(Point p) {this(p.x, p.y);}
    
    public Point(int xx, int yy)
    {
        x = xx;
        y = yy;
    }
    
    /**
     * Creates a point from an ordered pair in String form.
     * @param op the ordered pair as a String
     */
    public Point(String op)
    {
        op = op.replace("(", "");
        op = op.replace(")", "");
        op = op.replace(",", "");
        String[] split = op.split(" ");
        
        x = Integer.parseInt(split[0]);
        y = Integer.parseInt(split[1]);
    }
    
    @Override
    public String toString() {return "(" + x + ", " + y + ")";}
    
    public int getX() {return x;}
    public int getY() {return y;}
    
    public void setX(int xx) {x = xx;}
    public void setY(int yy) {y = yy;}
    
    public void changeX(int xx) {x += xx;}
    public void changeY(int yy) {y += yy;}
    
    public void set(int xx, int yy)
    {
        x = xx;
        y = yy;
    }
    
    /**
     * Returns true if another entered point has the same coordinates as this
     * point.
     * @param point the point to compare for equality
     * @return true if the x coordinates and y coordinates are equal across
     * both points
     */
    public boolean equals(Point point)
        {return point.x == x && point.y == y;}
    
    /**
     * Returns true if another specified point is exactly one space away from
     * this point.
     * @param point the point to compare for adjacency
     * @return true if a coordinate of the point deviates exactly one cardinal
     * space from the corresponding coordinate on this point, assuming the other
     * coordinate does not
     */
    public boolean isAdjacentTo(Point point)
    {
        return ((Math.abs(x - point.x) == 1)  && 
                (Math.abs(y - point.y) == 0)) ||
               ((Math.abs(x - point.x) == 0)  &&
                (Math.abs(y - point.y) == 1));
    }
}