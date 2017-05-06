package corelib.map;

import corelib.display.glyphs.ColorChar;
import squidpony.squidmath.Coord;

/** A more fleshed out Entity capable of acting on its own. */
public class Being extends Entity
{
    private AI ai;
    
    public Being(TileMap map, Coord location, ColorChar glyph)
        {super(map, location, glyph);}
    
    public void setAI(AI ai)
        {this.ai = ai;}
    
    @Override
    public boolean update()
        {return ai.update();}
}