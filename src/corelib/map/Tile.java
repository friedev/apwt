package corelib.map;

import corelib.display.glyphs.ColorChar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @param <Property> 
 */
public class Tile<Property>
{
    private ColorChar glyph;
    private double moveCost;
    private List<Property> properties;
    
    public Tile(ColorChar glyph, double moveCost, List<Property> properties)
    {
        this.glyph = glyph;
        this.properties = properties;
        this.moveCost = moveCost;
    }
    
    public Tile(Tile copying)
        {this(copying.glyph, copying.moveCost, copying.properties);}
    
    public Tile(ColorChar glyph, double moveCost, Property[] properties)
        {this(glyph, moveCost, Arrays.asList(properties));}

    public Tile(ColorChar glyph, double moveCost)
        {this(glyph, moveCost, new ArrayList<>());}
    
    public Tile(ColorChar glyph, List<Property> properties)
        {this(glyph, TileMap.TILE_COST, properties);}
    
    public Tile(ColorChar glyph, Property[] properties)
        {this(glyph, TileMap.TILE_COST, properties);}

    public Tile(ColorChar glyph)
        {this(glyph, TileMap.TILE_COST);}
    
    public ColorChar getGlyph()
        {return glyph;}
    
    public double getCost()
        {return moveCost;}
    
    public boolean isOpen()
        {return moveCost >= 0;}
    
    public List<Property> getProperties()
        {return properties;}
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof Tile))
            return false;
        
        Tile other = (Tile) o;
        return glyph.equals(other.glyph) && moveCost == other.moveCost &&
                properties.equals(other.properties);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.glyph);
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.moveCost) ^
                (Double.doubleToLongBits(this.moveCost) >>> 32));
        hash = 61 * hash + Objects.hashCode(this.properties);
        return hash;
    }
}
