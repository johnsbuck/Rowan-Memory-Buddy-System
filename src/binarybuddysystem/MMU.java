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
		chunkSize = chunkSize;
		numChunks = memorySize/chunkSize;
		//initialize memory # of Chunks = memorySize/ChunkSize
		//If prior 2 are chosen correctly, should divide evenly
		memory = new Slot[numChunks];
		
		//Initialize that the entire memory is one unit that is a hole
		Process initial = new Process("hole", numChunks);
		memory[0]=new Slot(initial, numChunks, 0, 0);
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
		//how many chunks in memory this process will take up
		int chunksNeeded = 1;
		
		//Start at minChunkSize and keep doubling until processSize fits.
		//This will give us the best fit for the process
		int i = chunkSize;
		while(i<processSize){
			i = i << 1 ;
		}
		
		//This will give us the amount of chunks needed for this process in memory.
		chunksNeeded = i / chunkSize;
		
		//Now that we have the number of chunks, lets find a hole that has enough space in it to fit
		//Easiest case
		i=0;
		Process slot;//will be used in loop
		while(i<numChunks){
			slot = memory[i].getProcess();
			//we found a hole!
			if(slot.getName().equals("hole")){
				//check if Size is large enough
				if(slot.size()>=chunksNeeded){
					//Ok, this slot is large enough, Lets see if we can break it up
					//and get a better fit
					while(slot.size()/2 >= chunksNeeded){
						//We can split this hole into two buddy holes
						
					}
				}
				else{
					//check if this hole is bordering another hole and allocate accordingly
				}
			}
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
		while(i < memorySize)
		{
			if(memory[i].getProcess().getName().equals(name))
			{
				//Removes process
				memory[i].removeProcess();
				//Tries to merge the empty chunk with a neighboring chunk
				merge(i);
				return true;
			}
			//Able to skip more memory this way
			i += memory[i].getChunkSize()/chunkSize; 
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
	 * @return true if merges, false if there is no merge
	 */
	private boolean merge(int index)		//index or hole
	{
		//If the chunk is the max size of memory, return false
		if(memory[index].getChunkSize() ==  memorySize)
			return false;
		
		//Indexes past to the next different chunk
		int a = memory[index].getChunkSize()/chunkSize;
		
		//If it is within memory bounds, both chunks are empty, and are buddies
		//then merge
		if(index + a < memory.length && memory[index].isHole() && memory[index+a].isHole() 
				&& (memory[index].getRef() == memory[index+a].getIndexPoint())
				&& (memory[index].getChunkSize() == memory[index+a].getChunkSize()))
		{
			//Index doubles the chunk size
			memory[index].doubleChunkSize();
			//Index cuts the index point
			memory[index].setIndexPoint(memory[index].getIndexPoint()/2);
			
			/*
			 * Is the new index even or odd?
			 * If even, point to the next memory chunk
			 * If odd, point to the previous memory chunk
			 */
			if(memory[index].getIndexPoint() % 2 == 1)
				memory[index].setBuddyReference(memory[index].getIndexPoint()-1);
			else
				memory[index].setBuddyReference(memory[index].getIndexPoint()+1);
			
			//Number of indexes in buddy chunk
			int size = memory[index+a].getChunkSize()/chunkSize;
			
			//Set each of the buddy chunk's min chunks to the new chunk
			for(int i = index+a; i < size; i++)
				memory[i] = memory[index];
			
			//Has merged
			return true;
		}
		//If the next chunk isn't a buddy, check the previous chunk 
		else if(index != 0 && memory[index].isHole() && memory[index-1].isHole()
				&& (memory[index].getRef() == memory[index-1].getIndexPoint())
				&& (memory[index].getChunkSize() == memory[index-1].getChunkSize()))
		{
			//Previous chunk doubles chunk size
			memory[index-1].doubleChunkSize();
			//Previous index cuts index point
			memory[index-1].setIndexPoint(memory[index-1].getIndexPoint()/2);
			
			/*
			 * Is the new index even or odd?
			 * If even, point to the next memory chunk
			 * If odd, point to the previous memory chunk
			 */
			if(memory[index-1].getIndexPoint() % 2 == 1)
				memory[index-1].setBuddyReference(memory[index-1].getIndexPoint()-1);
			else
				memory[index-1].setBuddyReference(memory[index-1].getIndexPoint()+1);
			
			//Number of indexes in buddy chunk
			int size = memory[index].getChunkSize()/chunkSize;
			
			//Set each of the buddy chunk's min chunks to the new chunk
			for(int i = index; i < size; i++)
				memory[i] = memory[index-1];
			
			//Has merged
			return true;
		}
		
		//Did not merge chunks
		return false;
	}
	
	public String toString(){
		int size = memory.length;
		String content ="This piece of memory of size "+memorySize+" bytes contains "+numChunks+ " chunks"+"\n";
		int i =0;
		while(i<size){
			content += memory[i] + "\n";
			i += memory[i].getProcess().size();
		}

		return content;
	}
}