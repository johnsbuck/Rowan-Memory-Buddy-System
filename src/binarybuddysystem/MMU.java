package binarybuddysystem;

public class MMU
{
	Slot[] memory; 	//Might make wrapper class (Chunk)
						//Each element is the minimum chunk size
						//Should it be a LinkedList instead?
	
	private int chunkSize;	//Minimum chunk size (2^k)
	private int memorySize;		//Total Memory Size	(2^n)
	private int numChunks;
	/*
	 * memorySize = memory.length * minChunkSize;
	 */
	

	/**
	 * constructor for MMU, needs memorySize and chunkSize.
	 * @param blocks number of blocks this MMU is responsible for
	 */
	public MMU(int memSize, int chunkSize)
	{		
		memorySize = memSize;
		this.chunkSize = chunkSize;
		numChunks = memorySize/chunkSize;
		//initialize memory # of Chunks = memorySize/ChunkSize
		//If prior 2 are chosen correctly, should divide evenly
		memory = new Slot[numChunks];
		
		//Initialize that the entire memory is one unit that is a hole
		memory[0]=new Slot(null, numChunks, 0, 0);
	}
	
	/**
	 * Allocates memory for a Process
	 * @param p the process to allocate and add to the Memory
	 * @return true on success, false otherwise
	 */
	public boolean allocate(Process p)
	{
		//Need to find best chunkSize to fit process in
		int processSize = p.size();
		
		
		//Start at minChunkSize and keep doubling until processSize fits.
		//This will give us the best fit for the process
		int i = chunkSize;
		while(i<processSize){
			i = i << 1 ;
		}
		
		//This will give us the amount of chunks needed for this process in memory.
		int chunksNeeded= i / chunkSize;
		
		//Now that we have the number of chunks, lets find a hole that has enough space in it to fit
		i=0;
		boolean allocated = false;
		while(i<numChunks && !allocated){
			Slot s = memory[i];
			int initialSlotSize = s.getSize(); 
			if(s.isHole()){
				//We found a hole, lets see if the process can fit in here
				if(s.getSize()>=chunksNeeded){
					//process will fit in slot, lets see if we can make smaller slots
					//so the process fits better
					while(s.getSize()/2 >=chunksNeeded){
						s.cutSize();
						s.setPoint(s.getPoint()*2);
						//Need to make a new slot to represent the hole that was created
						Slot hole = new Slot(null, s.getSize(), s.getPoint()+1, s.getPoint());
						memory[i+s.getSize()]=hole;
						s.setRef(s.getPoint()+1);
					}
					//finally set the process to the slot after all the holes
					s.setProcess(p);
					allocated = true;
				}
			}
			i+=initialSlotSize;
		}
		
		
		return false;
	}
	
	/**
	 * Deallocates memory and removes a Process
	 * @param name Process to be removed
	 * @return true if successful, false if otherwise
	 */
	public boolean deallocate(String name)
	{
		//Will loop through the memory till it finds the correct process
		int i = 0;
		while(i < numChunks)
		{
			if(!memory[i].isHole() && memory[i].getProcess().getName().equals(name))
			{
				//Removes process
				memory[i].removeProcess();
				//Tries to merge the empty chunk with a neighboring chunk
				merge(i);
				return true;
			}
			//Able to skip more memory this way
			i += memory[i].getSize(); 
		}
		
		return false;
	}
	
	private boolean partition(int index)	//index or hole
	{
		return false;
	}
	
	/**
	 * Tries to merge two empty chunks of the same size that are buddies
	 * @param index
	 * @return true if merges, false if there is no merge or fails to merge
	 */
	private boolean merge(int index)		//index or hole
	{
		//If the chunk is the max size of memory, return false
		if(index >= numChunks || index < 0 || memory[index].getSize() ==  numChunks)
			return false;
		
		//Indexes past to the next different chunk
		int a = memory[index].getSize()/chunkSize;
		
		//If it is within memory bounds, both chunks are empty, and are buddies
		//then merge
		if(index + a < numChunks && memory[index].isHole() && memory[index+a].isHole() 
				&& (memory[index].getRef() == memory[index+a].getPoint())
				&& (memory[index].getSize() == memory[index+a].getSize()))
		{
			//Index doubles the chunk size
			memory[index].doubleSize();
			//Index cuts the index point
			memory[index].setPoint(memory[index].getPoint()/2);
			
			/*
			 * Is the new index even or odd?
			 * If even, point to the next memory chunk
			 * If odd, point to the previous memory chunk
			 */
			setReference(index);
			
			memory[index+a] = null;
			
			//Can we merge again?
			merge(index);
			
			//Has merged
			return true;
		}
		//If the next chunk isn't a buddy, check the previous chunk 
		else if(index-a >= 0 && memory[index].isHole() && memory[index-a].isHole()
				&& (memory[index].getRef() == memory[index-a].getPoint())
				&& (memory[index].getSize() == memory[index-a].getSize()))
		{
			//Previous chunk doubles chunk size
			memory[index-a].doubleSize();
			//Previous index cuts index point
			memory[index-a].setPoint(memory[index-a].getPoint()/2);
			
			/*
			 * Is the new index even or odd?
			 * If even, point to the next memory chunk
			 * If odd, point to the previous memory chunk
			 */
			setReference(index-a);
			
			memory[index] = null;
			
			//Can we merge again?
			merge(index-a);
			
			//Has merged
			return true;
		}
		
		//Did not merge chunks
		return false;
	}
	
	/**
	 * This method sets the reference to an indexed Slot
	 * If it is even and within range,
	 * @param index
	 * @return
	 */
	private boolean setReference(int index)
	{
		if(index < 0 || index >= numChunks || memory[index] == null)
			return false;
		
		int size = memory[index].getSize();
		
		if(memory[index].getPoint() % 2 == 1)
		{
			if(numChunks > index-size && index-size >= 0)
			{
				memory[index].setRef(memory[index].getPoint()-1);
				return true;
			}
		}
		else if(memory[index].getPoint() % 2 == 0)
		{
			if(numChunks > index-size && index-size >= 0)
			{
				memory[index].setRef(memory[index].getPoint()+1);
				return true;
			}
		}
		
		memory[index].setRef(memory[index].getPoint());
		return false;
	}
	
	public String toString(){
		int size = memory.length;
		String content ="This piece of memory of size "+memorySize+" bytes contains "+numChunks+ " chunks"+"\n";
		int i =0;
		while(i<size){
			content += memory[i] + "\n";
			i += memory[i].getSize();
		}

		return content;
	}
}