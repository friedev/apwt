package maugrift.apwt.glyphs;

/**
 * An object with a {@link #toColorString()} method.
 *
 * @author Maugrift
 */
public interface ColorStringObject
{
    /**
     * Returns a {@link ColorString} representing this object.
     *
     * @return a {@link ColorString} representing this object
     */
    ColorString toColorString();
}
