package core.display;

/**
 * A collection of methods and constants for easy retrieval of line characters
 * of varying width.
 */
public abstract class LineChars
{
    /** The character returned when invalid values are specified. */
    public static final char UNKNOWN = '?';
    
    /** One horizontal line. */
    public static final char LINE1_HORIZONTAL = (char) 196;
    /** One vertical line. */
    public static final char LINE1_VERTICAL = (char) 179;
    /** A line at the top left corner of a rectangle. */
    public static final char LINE1_TL = (char) 218;
    /** A line at the top right corner of a rectangle. */
    public static final char LINE1_TR = (char) 191;
    /** A line at the bottom left corner of a rectangle. */
    public static final char LINE1_BL = (char) 192;
    /** A line at the bottom right corner of a rectangle. */
    public static final char LINE1_BR = (char) 217;
    /** A horizontal line with a perpendicular line extending upwards. */
    public static final char LINE1_SPLIT_U = (char) 193;
    /** A horizontal line with a perpendicular line extending downwards. */
    public static final char LINE1_SPLIT_D = (char) 194;
    /** A horizontal line with a perpendicular line extending to the left. */
    public static final char LINE1_SPLIT_L = (char) 180;
    /** A horizontal line with a perpendicular line extending to the right. */
    public static final char LINE1_SPLIT_R = (char) 195;
    /** A horizontal line with two parallel lines extending upwards. */
    public static final char LINE1_SWITCH_U = (char) 208;
    /** A horizontal line with two parallel lines extending downwards. */
    public static final char LINE1_SWITCH_D = (char) 210;
    /** A horizontal line with two parallel lines extending to the left. */
    public static final char LINE1_SWITCH_L = (char) 181;
    /** A horizontal line with two parallel lines extending to the right. */
    public static final char LINE1_SWITCH_R = (char) 198;
    /** A perpendicular intersection of two lines. */
    public static final char LINE1_CENTER = (char) 197;

    /** Two parallel, horizontal lines. */
    public static final char LINE2_HORIZONTAL = (char) 205;
    /** Two parallel, vertical lines. */
    public static final char LINE2_VERTICAL = (char) 186;
    /** Two lines at the top left corner of a rectangle. */
    public static final char LINE2_TL = (char) 201;
    /** Two lines at the top right corner of a rectangle. */
    public static final char LINE2_TR = (char) 187;
    /** Two lines at the bottom left corner of a rectangle. */
    public static final char LINE2_BL = (char) 200;
    /** Two lines at the bottom right corner of a rectangle. */
    public static final char LINE2_BR = (char) 188;
    /** Two parallel, horizontal lines with two parallel lines extending upwards. */
    public static final char LINE2_SPLIT_U = (char) 202;
    /** Two parallel, horizontal lines with two parallel lines extending downwards. */
    public static final char LINE2_SPLIT_D = (char) 203;
    /** Two parallel, vertical lines with two parallel lines extending to the left. */
    public static final char LINE2_SPLIT_L = (char) 185;
    /** Two parallel, vertical lines with two parallel lines extending to the right. */
    public static final char LINE2_SPLIT_R = (char) 204;
    /** Two parallel, horizontal lines with a perpendicular line extending upwards. */
    public static final char LINE2_SWITCH_U = (char) 207;
    /** Two parallel, horizontal lines with a perpendicular line extending downwards. */
    public static final char LINE2_SWITCH_D = (char) 209;
    /** Two parallel, vertical lines with a perpendicular line extending to the left. */
    public static final char LINE2_SWITCH_L = (char) 182;
    /** Two parallel, vertical lines with a perpendicular line extending to the right. */
    public static final char LINE2_SWITCH_R = (char) 199;
    /** A perpendicular intersection of two sets of parallel lines. */
    public static final char LINE2_CENTER = (char) 206;
    
    /**
     * Returns a horizontal or vertical line character with the provided width.
     * @param horizontal if true, will return a horizontal line; if false, will
     * return a vertical line
     * @param width the number of lines in the character to return; must be 1 or
     * 2
     * @return a line character with the specified properties; the UNKNOWN
     * character if invalid
     */
    public static char getLine(boolean horizontal, int width)
        {return horizontal ? horizontal(width) : vertical(width);}
    
    /**
     * Returns the horizontal line character with the provided width.
     * @param width the number of lines in the character to return; must be 1 or
     * 2
     * @return a horizontal line character with the specified width; the UNKNOWN
     * character if invalid
     */
    public static char horizontal(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_HORIZONTAL;
            case 2:  return LINE2_HORIZONTAL;
            default: return UNKNOWN;
        }
    }
    
    /**
     * Returns the vertical line character with the provided width.
     * @param width the number of lines in the character to return; must be 1 or
     * 2
     * @return a vertical line character with the specified width; the UNKNOWN
     * character if invalid
     */
    public static char vertical(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_VERTICAL;
            case 2:  return LINE2_VERTICAL;
            default: return UNKNOWN;
        }
    }
    
    /**
     * Returns the top-left corner character with the provided width.
     * @param width the number of lines in the character to return; must be 1 or
     * 2
     * @return a top-left corner character with the specified width; the UNKNOWN
     * character if invalid
     */
    public static char topLeft(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_TL;
            case 2:  return LINE2_TL;
            default: return UNKNOWN;
        }
    }
    
    /**
     * Returns the top-right corner character with the provided width.
     * @param width the number of lines in the character to return; must be 1 or
     * 2
     * @return a top-right corner character with the specified width; the
     * UNKNOWN character if invalid
     */
    public static char topRight(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_TR;
            case 2:  return LINE2_TR;
            default: return UNKNOWN;
        }
    }
    
    /**
     * Returns the bottom-left corner character with the provided width.
     * @param width the number of lines in the character to return; must be 1 or
     * 2
     * @return a bottom-left corner character with the specified width; the
     * UNKNOWN character if invalid
     */
    public static char bottomLeft(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_BL;
            case 2:  return LINE2_BL;
            default: return UNKNOWN;
        }
    }
    
    /**
     * Returns the bottom-right corner character with the provided width.
     * @param width the number of lines in the character to return; must be 1 or
     * 2
     * @return a bottom-right corner character with the specified width; the
     * UNKNOWN character if invalid
     */
    public static char bottomRight(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_BR;
            case 2:  return LINE2_BR;
            default: return UNKNOWN;
        }
    }
    
    /**
     * Returns a line character with the provided horizontal width with a
     * perpendicular line of the specified width extending upwards.
     * @param widthHorizontal the width of the horizontal line; must be 1 or 2
     * @param widthVertical the width of the vertical line; must be 1 or 2
     * @return a split line character with the specified widths; the UNKNOWN
     * character if invalid
     */
    public static char splitUp(int widthHorizontal, int widthVertical)
    {
        switch (widthHorizontal)
        {
            case 1:
                switch (widthVertical)
                {
                    case 1:  return LINE1_SPLIT_U;
                    case 2:  return LINE1_SWITCH_U;
                    default: return UNKNOWN;
                }
            case 2:
                switch (widthVertical)
                {
                    case 1:  return LINE2_SWITCH_U;
                    case 2:  return LINE2_SPLIT_U;
                    default: return UNKNOWN;
                }
            default:
                return UNKNOWN;
        }
    }
    
    /**
     * Returns a line character with the provided horizontal width with a
     * perpendicular line of the specified width extending downwards.
     * @param widthHorizontal the width of the horizontal line; must be 1 or 2
     * @param widthVertical the width of the vertical line; must be 1 or 2
     * @return a split line character with the specified widths; the UNKNOWN
     * character if invalid
     */
    public static char splitDown(int widthHorizontal, int widthVertical)
    {
        switch (widthHorizontal)
        {
            case 1:
                switch (widthVertical)
                {
                    case 1:  return LINE1_SPLIT_D;
                    case 2:  return LINE1_SWITCH_D;
                    default: return UNKNOWN;
                }
            case 2:
                switch (widthVertical)
                {
                    case 1:  return LINE2_SWITCH_D;
                    case 2:  return LINE2_SPLIT_D;
                    default: return UNKNOWN;
                }
            default:
                return UNKNOWN;
        }
    }
    
    /**
     * Returns a line character with the provided vertical width with a
     * perpendicular line of the specified width extending to the left.
     * @param widthVertical the width of the vertical line; must be 1 or 2
     * @param widthHorizontal the width of the horizontal line; must be 1 or 2
     * @return a split line character with the specified widths; the UNKNOWN
     * character if invalid
     */
    public static char splitLeft(int widthVertical, int widthHorizontal)
    {
        switch (widthVertical)
        {
            case 1:
                switch (widthHorizontal)
                {
                    case 1:  return LINE1_SPLIT_L;
                    case 2:  return LINE1_SWITCH_L;
                    default: return UNKNOWN;
                }
            case 2:
                switch (widthHorizontal)
                {
                    case 1:  return LINE2_SWITCH_L;
                    case 2:  return LINE2_SPLIT_L;
                    default: return UNKNOWN;
                }
            default:
                return UNKNOWN;
        }
    }
    
    /**
     * Returns a line character with the provided vertical width with a
     * perpendicular line of the specified width extending to the right.
     * @param widthVertical the width of the vertical line; must be 1 or 2
     * @param widthHorizontal the width of the horizontal line; must be 1 or 2
     * @return a split line character with the specified widths; the UNKNOWN
     * character if invalid
     */
    public static char splitRight(int widthVertical, int widthHorizontal)
    {
        switch (widthVertical)
        {
            case 1:
                switch (widthHorizontal)
                {
                    case 1:  return LINE1_SPLIT_R;
                    case 2:  return LINE1_SWITCH_R;
                    default: return UNKNOWN;
                }
            case 2:
                switch (widthHorizontal)
                {
                    case 1:  return LINE2_SWITCH_R;
                    case 2:  return LINE2_SPLIT_R;
                    default: return UNKNOWN;
                }
            default:
                return UNKNOWN;
        }
    }
    
    /**
     * Returns the intersection character with the specified width.
     * @param width the number of lines in the character to return; must be 1 or
     * 2
     * @return a vertical line character with the specified width; the UNKNOWN
     * character if invalid
     */
    public static char center(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_CENTER;
            case 2:  return LINE2_CENTER;
            default: return UNKNOWN;
        }
    }
}