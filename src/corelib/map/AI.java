package corelib.map;

/**
 * A {@link Being} controller that can be extended and adapted for many
 * different situations.
 */
public abstract class AI
{
    /** The {@link Being} that this {@link AI} controls. */
    private Being host;
    
    /**
     * Creates a new {@link AI} for the given host.
     * @param host the {@link Being} that this {@link AI} will control
     */
    public AI(Being host)
    {
        this.host = host;
        this.host.setAI(this);
    }
    
    /**
     * Processes available input and makes this {@link AI}'s host act
     * accordingly.
     * @return true if the host moved, signaling to the map that its search must
     * be updated before further updates
     */
    public abstract boolean update();
    
    /**
     * Returns this {@link AI}'s host {@link Being}.
     * @return this {@link AI}'s host {@link Being}
     */
    public Being getHost()
        {return host;}
    
    /**
     * Sets the host to the given {@link Being}, also updating the references to
     * this {@link AI} in the former host and new host.
     * @param host the {@link AI}'s new host
     */
    public void setHost(Being host)
    {
        this.host.removeAI();
        this.host = host;
        this.host.setAI(this);
    }
    
    /**
     * Removes the current host, also removing the old host's reference to this
     * {@link AI}.
     */
    public void removeHost()
    {
        this.host.removeAI();
        host = null;
    }
}
