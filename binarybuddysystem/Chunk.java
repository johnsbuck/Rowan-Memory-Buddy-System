package binarybuddysystem;

public class Chunk
{
    private Process process;
    private int chunkSize;          //2^n
    private int point;				//The chunk's indexing point
    private int buddyReference;     //Points to buddy chunk, which must be the same size
                                    //and point back to this chunk (neighboring)
        
    public Chunk()
    {
        process = null; //or new Process()
        chunkSize = 0;
        buddyReference = 0;
        point = 0;
    }
    
    public Chunk(Process p, int chunkSize, int point, int reference)
    {
        process = p; //or equivalent to safe assignment
        this.chunkSize = chunkSize;
        buddyReference = reference;
        this.point = point;
    }
    
    public Process getProcess()
    {
        return process;
    }
    
    public void setProcess(Process p)
    {
        process = p;
    }
    
    public boolean removeProcess()
    {
        process = null;
        
        return isHole();
    }
    
    public boolean isHole()
    {
        if(process == null)
            return true;
        return false;
    }
    
    public int getChunkSize()
    {
        return chunkSize;
    }
    
    public void setChunkSize(int size)
    {
    	chunkSize = size;
    }
    
    public void doubleChunkSize()
    {
    	chunkSize *= 2;
    }
    
    public void cutChunkSize()
    {
    	chunkSize /= 2;
    }
    
    public int getBuddyReference()
    {
        return buddyReference;
    }
    
    public void setBuddyReference(int reference)
    {
    	buddyReference = reference;
    }
    
    public int getIndexPoint()
    {
    	return point;
    }
    
    public void setIndexPoint(int index)
    {
    	point = index;
    }
}