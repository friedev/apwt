package corelib.map;

import corelib.display.glyphs.ColorChar;
import corelib.items.Container;
import squidpony.squidmath.Coord;

/** A more fleshed out Entity capable of acting on its own. */
public class Being extends Entity
{
    private double speed;
    private AI ai;
    // Replace with a BodyPart[] eventually
    private Container[] inventories;
    
    public Being(TileMap map, Coord location, ColorChar glyph, double moveSpeed,
            Container[] inventories)
    {
        super(map, location, glyph);
        this.inventories = inventories;
        this.speed = moveSpeed;
    }
    
    public double getMoveSpeed()
        {return speed;}
    
    public void setAI(AI ai)
        {this.ai = ai;}
    
    @Override
    public boolean update()
        {return ai.update();}
}