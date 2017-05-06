package corelib.map;

import corelib.display.glyphs.ColorChar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A tile on a {@link TileMap} with a movement cost and list of properties,
 * displayed with a ColorChar.
 * @param <Property> the object that this Tile will use as a property
 */
public class Tile<Property>
{
    /**
     * The {@link corelib.display.glyphs.ColorChar} that the {@link Tile} will
     * be visually represented with.
     */
    private ColorChar glyph;
    
    /**
     * The cost to move onto this {@link Tile}. Negative values mean that this
     * {@link Tile} cannot be entered.
     */
    private double moveCost;
    
    /** The properties possessed by this {@link Tile}. */
    private List<Property> properties;
    
    /**
     * Creates a {@link Tile} with all fields defined.
     * @param glyph the {@link Tile}'s glyph
     * @param moveCost the {@link Tile}'s movement cost
     * @param properties the {@link Tile}'s properties
     */
    public Tile(ColorChar glyph, double moveCost, List<Property> properties)
    {
        this.glyph = glyph;
        this.properties = properties;
        this.moveCost = moveCost;
    }
    
    /**
     * Creates a {@link Tile} from another {@link Tile}.
     * @param copying the {@link Tile} to copy
     */
    public Tile(Tile copying)
        {this(copying.glyph, copying.moveCost, copying.properties);}
    
    /**
     * Creates a {@link Tile} with an array of properties rather than a List.
     * @param glyph the {@link Tile}'s glyph
     * @param moveCost the {@link Tile}'s movement cost
     * @param properties the {@link Tile}'s properties as an array
     */
    public Tile(ColorChar glyph, double moveCost, Property[] properties)
        {this(glyph, moveCost, Arrays.asList(properties));}

    /**
     * Creates a {@link Tile} with no properties.
     * @param glyph the {@link Tile}'s glyph
     * @param moveCost the {@link Tile}'s movement cost
     */
    public Tile(ColorChar glyph, double moveCost)
        {this(glyph, moveCost, new ArrayList<>());}
    
    /**
     * Creates a {@link Tile} with the
     * {@link TileMap#TILE_COST default move cost} specified in {@link TileMap}.
     * @param glyph the {@link Tile}'s glyph
     * @param properties the {@link Tile}'s properties
     */
    public Tile(ColorChar glyph, List<Property> properties)
        {this(glyph, TileMap.TILE_COST, properties);}
    
    /**
     * Creates a {@link Tile} with the
     * {@link TileMap#TILE_COST default move cost} specified in {@link TileMap},
     * along with an array of properties rather than a List.
     * @param glyph the {@link Tile}'s glyph
     * @param properties the {@link Tile}'s properties as an array
     */
    public Tile(ColorChar glyph, Property[] properties)
        {this(glyph, TileMap.TILE_COST, properties);}

    /**
     * Creates a {@link Tile} with the
     * {@link TileMap#TILE_COST default move cost} specified in {@link TileMap},
     * without any properties.
     * @param glyph the {@link Tile}'s glyph
     */
    public Tile(ColorChar glyph)
        {this(glyph, TileMap.TILE_COST);}
    
    /**
     * Returns the glyph representing this {@link Tile}.
     * @return the glyph representing this {@link Tile}
     */
    public ColorChar getGlyph()
        {return glyph;}
    
    /**
     * Returns the cost of moving onto this {@link Tile}.
     * @return the cost of moving onto this {@link Tile}
     */
    public double getCost()
        {return moveCost;}
    
    /**
     * Returns true if this {@link Tile} can be entered.
     * @return true if this {@link Tile}'s move cost is non-negative
     */
    public boolean isOpen()
        {return moveCost >= 0;}
    
    /**
     * Returns this {@link Tile}'s list of properties.
     * @return this {@link Tile}'s list of properties
     */
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
