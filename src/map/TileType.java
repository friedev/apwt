package map;

import core.display.ColorChar;
import core.display.ExtChars;
import asciiPanel.AsciiPanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TileType
{
    FLOOR(new ColorChar(ExtChars.DOT, AsciiPanel.white),
            new TileProperty[]{TileProperty.OPEN, TileProperty.TRANSPARENT}),
    WALL(new ColorChar(ExtChars.BLOCK, AsciiPanel.brightBlack), -1),
    BOUNDS(new ColorChar('x', AsciiPanel.brightBlack), -1),
    STAIRS_DOWN(new ColorChar('>', AsciiPanel.brightWhite)),
    STAIRS_UP(new ColorChar('<', AsciiPanel.brightWhite)),
    UNKNOWN(new ColorChar(' ', AsciiPanel.white));
    
    private ColorChar glyph;
    private int moveCost;
    private List<TileProperty> properties;

    TileType(ColorChar glyph, int moveCost, List<TileProperty> properties)
    {
        this.glyph = glyph;
        this.moveCost = moveCost;
        this.properties = properties;
    }
    
    TileType(ColorChar glyph, int moveCost, TileProperty[] properties)
        {this(glyph, moveCost, Arrays.asList(properties));}

    TileType(ColorChar glyph, int moveCost)
        {this(glyph, moveCost, new ArrayList<>());}
    
    TileType(ColorChar glyph, List<TileProperty> properties)
        {this(glyph, Map.TILE_COST, properties);}
    
    TileType(ColorChar glyph, TileProperty[] properties)
        {this(glyph, Map.TILE_COST, properties);}

    TileType(ColorChar glyph)
        {this(glyph, Map.TILE_COST);}

    public ColorChar getGlyph()
        {return glyph;}
    
    public int getMoveCost()
        {return moveCost;}
    
    public List<TileProperty> getProperties()
        {return properties;}
    
    public TileType interact()
    {
        // This could include levers, doors, and other interactive tiles
        
        switch (this)
        {
            case WALL:
                return FLOOR;
            default:
                return this;
        }
    }
}
