package map;

/** A map tile, represented by a symbol at its core. */
public class Tile
{
    public static char EMPTY = '.';
    
    private char  symbol;
    private Point location;
    private Map   map;
    
    public Tile(Point l, Map m) {this(EMPTY, l, m);}
    
    public Tile(char s, Point l, Map m)
    {
        symbol   = s;
        location = l;
        map      = m;
    }
    
    @Override
    public String toString() {return "" + symbol;}
    
    public char  getSymbol()   {return symbol;  }
    public Point getLocation() {return location;}
    public Map   getMap()      {return map;     }
    
    public void setSymbol(char newSymbol) {symbol = newSymbol;}
}