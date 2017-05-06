package corelib.map;

import corelib.display.glyphs.ColorChar;
import squidpony.squidmath.Coord;

/** An {@link Entity} capable of acting on its own through an {@link AI}. */
public class Being extends Entity
{
    /** The {@link AI} controlling this {@link Being}. */
    private AI ai;
    
    /**
     * Creates a {@link Being} with all {@link Entity} fields defined.
     * @param map the {@link Entity}'s map
     * @param location the {@link Entity}'s location
     * @param glyph the {@link Entity}'s glyph
     */
    public Being(TileMap map, Coord location, ColorChar glyph)
        {super(map, location, glyph);}
    
    /**
     * Sets the {@link Being}'s {@link AI} to the given {@link AI}.
     * @param ai the {@link Being}'s new {@link AI}
     */
    public void setAI(AI ai)
        {this.ai = ai;}
    
    /**
     * Sets the {@link Being}'s {@link AI} to null, removing it. This should
     * only be done through {@link AI#removeHost()}.
     */
    public void removeAI()
        {ai = null;}
    
    @Override
    public boolean update()
        {return ai == null ? false : ai.update();}
}