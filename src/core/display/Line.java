package core.display;

/**
 * A collection of methods and constants for easy retrieval of line characters
 * of varying width.
 */
public abstract class Line
{
    public static final char UNKNOWN = '?';
    
    public static final char LINE1_HORIZONTAL = (char) 196;
    public static final char LINE1_VERTICAL   = (char) 179;
    public static final char LINE1_TL         = (char) 218;
    public static final char LINE1_TR         = (char) 191;
    public static final char LINE1_BL         = (char) 192;
    public static final char LINE1_BR         = (char) 217;
    public static final char LINE1_SPLIT_U    = (char) 193;
    public static final char LINE1_SPLIT_D    = (char) 194;
    public static final char LINE1_SPLIT_L    = (char) 180;
    public static final char LINE1_SPLIT_R    = (char) 195;
    public static final char LINE1_SWITCH_U   = (char) 208;
    public static final char LINE1_SWITCH_D   = (char) 210;
    public static final char LINE1_SWITCH_L   = (char) 181;
    public static final char LINE1_SWITCH_R   = (char) 198;
    public static final char LINE1_CENTER     = (char) 197;

    public static final char LINE2_HORIZONTAL = (char) 205;
    public static final char LINE2_VERTICAL   = (char) 186;
    public static final char LINE2_TL         = (char) 201;
    public static final char LINE2_TR         = (char) 187;
    public static final char LINE2_BL         = (char) 200;
    public static final char LINE2_BR         = (char) 188;
    public static final char LINE2_SPLIT_U    = (char) 202;
    public static final char LINE2_SPLIT_D    = (char) 203;
    public static final char LINE2_SPLIT_L    = (char) 185;
    public static final char LINE2_SPLIT_R    = (char) 204;
    public static final char LINE2_SWITCH_U   = (char) 207;
    public static final char LINE2_SWITCH_D   = (char) 209;
    public static final char LINE2_SWITCH_L   = (char) 182;
    public static final char LINE2_SWITCH_R   = (char) 199;
    public static final char LINE2_CENTER     = (char) 206;
    
    public static char horizontal(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_HORIZONTAL;
            case 2:  return LINE2_HORIZONTAL;
            default: return UNKNOWN;
        }
    }
    
    public static char vertical(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_VERTICAL;
            case 2:  return LINE2_VERTICAL;
            default: return UNKNOWN;
        }
    }
    
    public static char topLeft(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_TL;
            case 2:  return LINE2_TL;
            default: return UNKNOWN;
        }
    }
    
    public static char topRight(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_TR;
            case 2:  return LINE2_TR;
            default: return UNKNOWN;
        }
    }
    
    public static char bottomLeft(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_BL;
            case 2:  return LINE2_BL;
            default: return UNKNOWN;
        }
    }
    
    public static char bottomRight(int width)
    {
        switch (width)
        {
            case 1:  return LINE1_BR;
            case 2:  return LINE2_BR;
            default: return UNKNOWN;
        }
    }
    
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