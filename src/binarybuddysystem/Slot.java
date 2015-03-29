package binarybuddysystem;

public class Slot
{
    private Process process;
    private int chunkSize;          //2^n
    private int point;				//The chunk's indexing point
    private int ref;     //Points to buddy chunk, which must be the same size
                                    //and point back to this chunk (neighboring)
    
    /**
	 * Instantiates a new, blank Chunk
	 */
    public Slot()
    {
        process = null; //or new Process()
        chunkSize = 0;
        ref = 0;
        point = 0;
    }
    
    /**
	 * Instantiates a new Chunk
	 * @param p the Process assigned to the Chunk
	 * @param chunkSize size of the Chunk, in blocks
	 * @param point is the reference used by the Buddy of this Chunk
	 * @param reference references to the Buddy of this Chunk
	 */
    public Slot(Process p, int chunkSize, int point, int reference)
    {
        process = p; //or equivalent to safe assignment
        this.chunkSize = chunkSize;
        ref = reference;
        this.point = point;
    }
    
    /**
	 * Gets the Process assigned to this Chunk
	 * @return the assigned Process
	 */
    public Process getProcess()
    {
        return process;
    }
    
    /**
	 * Sets the Process assigned to this Chunk
	 * @param p the Process to assign
	 */
    public void setProcess(Process p)
    {
        process = p;
    }
    
    /**
	 * Empties the Chunk (Deletes the Process from memory)
	 * @return true if success, false if not
	 */
    public boolean removeProcess()
    {
        process = null;
        
        return isHole();
    }
    
    /**
	 * Checks to see if this Chunk is a "hole"
	 * @return true if this Chunk does not contain a process, false if otherwise
	 */
    public boolean isHole()
    {
        if(process == null)
            return true;
        return false;
    }
    
    /**
	 * Gets the Chunk size
	 * @return size of the Chunk
	 */
    public int getChunkSize()
    {
        return chunkSize;
    }
    
    /**
	 * Sets the Chunk Size
	 * @param size the size to be set
	 * @return true if successful, false if otherwise
	 */
    public void setChunkSize(int size)
    {
    	chunkSize = size;
    }
    
    /**
	 * Doubles the chunk size if merging
	 */
    public void doubleChunkSize()
    {
    	chunkSize *= 2;
    }
    
    /**
	 * Chunks the chunk size to obtain suitable size for process
	 */
    public void cutChunkSize()
    {
    	chunkSize /= 2;
    }
    
    /**
	 * Gets the "buddy" reference to this Chunk
	 * @return the adjacent "buddy" Chunk
	 */
    public int getRef()
    {
        return ref;
    }
    
    /**
	 * Sets the buddy reference to this Chunk
	 * @param reference the Chunk to be set
	 */
    public void setBuddyReference(int reference)
    {
    	ref = reference;
    }
    
    /**
	 * Gets the index point of the Chunk
	 * @return the index point of this Chunk
	 */
    public int getIndexPoint()
    {
    	return point;
    }
    
    /**
	 * Sets the index point of the Chunk
	 * @param index the point to be set
	 */
    public void setIndexPoint(int index)
    {
    	point = index;
    }
    
    
    /*
     * @return returns a String denoting this chunks current state
     */
    public String toString()
    {
    	return "name: "+process.getName()+", size: "+chunkSize+", points: "+point+" buddy: "+ ref;
    }
}