package corelib.map;

import corelib.display.glyphs.ColorChar;
import squidpony.squidmath.Coord;

/**
 * 
 */
public class Cursor extends Entity
{
    public Cursor(TileMap map, Coord location, ColorChar glyph,
            double moveSpeed)
        {super(map, location, glyph, moveSpeed);}
    
    public Cursor(TileMap map, ColorChar glyph, double moveSpeed)
        {super(map, glyph, moveSpeed);}
    
    public Cursor(TileMap map, Coord location, ColorChar glyph)
        {super(map, location, glyph);}
    
    public Cursor(TileMap map, ColorChar glyph)
        {super(map, glyph);}
    
    @Override
    public boolean setLocation(Coord destination)
    {
        if (map.contains(destination))
        {
            location = destination;
            return true;
        }
        
        return false;
    }
}