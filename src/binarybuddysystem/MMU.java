package binarybuddysystem;

public class MMU
{
	Chunk[] memory; 	//Might make wrapper class (Chunk)
						//Each element is the minimum chunk size
						//Should it be a LinkedList instead?
	
	int minChunkSize;	//Minimum chunk size (2^k)
	int memorySize;		//Total Memory Size	(2^n)
	/*
	 * memorySize = memory.length * minChunkSize;
	 */
	

	/**
	 * Default constructor for the MMU
	 * @param blocks number of blocks this MMU is responsible for
	 */
	public MMU()
	{
		
	}
	
	/**
	 * Allocates memory for a Process
	 * @param p the process to allocate and add to the Memory
	 * @return true on success, false otherwise
	 */
	public boolean allocate(Process p)
	{
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
			i += memory[i].getChunkSize()/minChunkSize; 
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
		int a = memory[index].getChunkSize()/minChunkSize;
		
		//If it is within memory bounds, both chunks are empty, and are buddies
		//then merge
		if(index + a < memory.length && memory[index].isHole() && memory[index+a].isHole() 
				&& (memory[index].getBuddyReference() == memory[index+a].getIndexPoint())
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
			int size = memory[index+a].getChunkSize()/minChunkSize;
			
			//Set each of the buddy chunk's min chunks to the new chunk
			for(int i = index+a; i < size; i++)
				memory[i] = memory[index];
			
			//Has merged
			return true;
		}
		//If the next chunk isn't a buddy, check the previous chunk 
		else if(index != 0 && memory[index].isHole() && memory[index-1].isHole()
				&& (memory[index].getBuddyReference() == memory[index-1].getIndexPoint())
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
			int size = memory[index].getChunkSize()/minChunkSize;
			
			//Set each of the buddy chunk's min chunks to the new chunk
			for(int i = index; i < size; i++)
				memory[i] = memory[index-1];
			
			//Has merged
			return true;
		}
		
		//Did not merge chunks
		return false;
	}
	
	public String toString()
	{
		String str = "";
		
		return str;
	}
}