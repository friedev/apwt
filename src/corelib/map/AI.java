package corelib.map;

/**
 * 
 */
public abstract class AI
{
    protected Being host;
    
    public AI(Being host)
    {
        this.host = host;
        this.host.setAI(this);
    }
    
    public abstract boolean update();
}
