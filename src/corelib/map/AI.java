package corelib.map;

/**
 * 
 */
public abstract class AI
{
    private Being host;
    
    public AI(Being host)
    {
        this.host = host;
        this.host.setAI(this);
    }
    
    public abstract boolean update();
    
    public Being getHost()
        {return host;}
    
    public void setHost(Being host)
        {this.host = host;}
}
