package binarybuddysystem;

public class Chunk
{
    private Process process;
    private int chunkSize;          //2^n
    private int minChunkSize;
    private int maxChunkSize;
    private int point;				//The chunk's indexing point
    private int buddyReference;     //Points to buddy chunk, which must be the same size
                                    //and point back to this chunk (neighboring)
        
    public Chunk()
    {
        process = null; //or new Process()
        chunkSize = 0;
        buddyReference = 0;
        minChunkSize = 0;
        maxChunkSize = 0;
    }
    
    public Chunk(Process p, int chunkSize, int minChunkSize, int maxChunkSize, int reference)
    {
        process = p; //or equivalent to safe assignment
        this.chunkSize = chunkSize;
        buddyReference = reference;
        this.minChunkSize = minChunkSize;
        this.maxChunkSize = maxChunkSize;
    }
    
    public Process getProcess()
    {
        return process;
    }
    
    public void setProcess(Process p)
    {
        process = p;
    }
    
    public boolean emptyChunk()
    {
        process = null;
        
        return hole();
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
    
    public boolean setChunkSize(int size)
    {
    	if(chunkSize >= minChunkSize && chunkSize <- maxChunkSize)
    	{
    		chunkSize = size;
    		return true;
    	}
    	
    	return false;
    }
    
    public boolean doubleChunkSize()
    {
        if(chunkSize < maxChunkSize)
        {
            chunkSize *= 2;
            return true;
        }
        return false;
    }
    
    public boolean cutChunkSize()
    {
        if(chunkSize > minChunkSize)
        {
            chunkSize /= 2;
            return true;
        }
        return false;
    }
    
    public int getBuddyReference()
    {
        return buddyReference;
    }
    
    public boolean setBuddyReference(int reference)
    {
    	if(maxChunkSize/minChunkSize > reference && reference >= 0)
    	{
    		buddyReference = reference;
    		return false;
    	}
    	return false;
    }
    
    public int getIndexPoint()
    {
    	return point;
    }
    
    public in setIndexPoint(int index)
    {
    	if(maxChunkSize/minChunkSize > index && index >= 0)
    	{
    		point = index;
    		return true;
    	}
    	return false;
    }
}