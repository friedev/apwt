package boldorf.apwt.glyphs;

/** An object with a {@link #toColorString()} method. */
public interface ColorStringObject
{
    /**
     * Returns a {@link ColorString} representing this object.
     * @return a {@link ColorString} representing this object
     */
    public ColorString toColorString();
}
