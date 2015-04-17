package binarybuddysystem;

public class Slot
{
    private Process process;
    private int location;//Points to buddy chunk, which must be the same size
                                    //and point back to this chunk (neighboring)
    
    /**
	 * Instantiates a new, blank Chunk
	 */
    public Slot()
    {
        process = null; //or new Process()
        location = 0;
    }
    
    /**
	 * Instantiates a new Chunk
	 * @param p the Process assigned to the Chunk
	 * @param chunkSize size of the Chunk, in blocks
	 * @param point is the reference used by the Buddy of this Chunk
	 * @param reference references to the Buddy of this Chunk
	 */
    public Slot(Process p, int loc)
    {
        process = p; //or equivalent to safe assignment
        location = loc;
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
	 * Gets the Chunk location
	 * @return location of the Chunk
	 */
    public int getLocation()
    {
        return location;
    }
    
    /**
	 * Sets the Chunk location
	 * @param location to be set
	 * @return true if successful, false if otherwise
	 */
    public boolean setSize(int loc)
    {
    	this.location = loc;
    	if(location == loc)
    		return true;
    	return false;
    }
    
    /**
     * @return returns a String denoting this chunks current state
     */
    public String toString()
    {
    	if(isHole()){
    		return "Hole. Location(chunk): "+ location;  		
    	}
    	else
    	{
    		return "Location: "+process.getName()+", Location(chunk): "+ location;
    	}
    }
}