package map;

import core.display.ColorChar;
import core.display.ExtChars;
import asciiPanel.AsciiPanel;
import java.util.List;

public enum TileType
{
    FLOOR(new ColorChar(ExtChars.DOT, AsciiPanel.white), "A dirt and rock cave floor.", new TileProperty[]{TileProperty.OPEN, TileProperty.TRANSPARENT}),
    WALL(new ColorChar(ExtChars.BLOCK, AsciiPanel.brightBlack), "A dirt and rock cave wall."),
    BOUNDS(new ColorChar('x', AsciiPanel.brightBlack), "Beyond the edge of the world."),
    STAIRS_DOWN(new ColorChar('>', AsciiPanel.brightWhite), "A stone staircase that goes down."),
    STAIRS_UP(new ColorChar('<', AsciiPanel.brightWhite), "A stone staircase that goes up."),
    UNKNOWN(new ColorChar(' ', AsciiPanel.white), "(unknown)");

    private ColorChar glyph;
    private String description;
    private List<TileProperty> properties;

    TileType(ColorChar glyph, String description, List<TileProperty>
            properties)
    {
        this.glyph = glyph;
        this.description = description;
        this.properties = properties;
    }
    
    TileType(ColorChar glyph, String description, TileProperty[] properties)
        {this(glyph, description, java.util.Arrays.asList(properties));}

    TileType(ColorChar glyph, String description)
        {this(glyph, description, new java.util.ArrayList<>());}

    public ColorChar getGlyph()
        {return glyph;}

    public String describe()
        {return description;}

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
