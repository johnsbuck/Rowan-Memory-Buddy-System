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
		int i = 0;
		while(i < memorySize)
		{
			if(memory[i].getProcess().getName().equals(name))
			{
				memory[i].removeProcess();
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
	
	private boolean merge(int index)		//index or hole
	{
		if(memory[index].getChunkSize() ==  memorySize)
			return false;
		
		int a = memory[index].getChunkSize()/minChunkSize;
		
		if(index + a < memory.length && memory[index].isHole() && memory[index+a].isHole() 
				&& (memory[index].getBuddyReference() == memory[index+a].getIndexPoint())
				&& (memory[index].getChunkSize() == memory[index+a].getChunkSize()))
		{
			memory[index].doubleChunkSize();
			
			int size = memory[index+a].getChunkSize()/minChunkSize;
			
			for(int i = index+a; i < size; i++)
				memory[i] = memory[index];
			
			return true;
		}
		else if(index != 0 && memory[index].isHole() && memory[index-1].isHole()
				&& (memory[index].getBuddyReference() == memory[index-1].getIndexPoint())
				&& (memory[index].getChunkSize() == memory[index-1].getChunkSize()))
		{
			memory[index-1].doubleChunkSize();
			
			int size = memory[index].getChunkSize()/minChunkSize;
			
			for(int i = index; i < size; i++)
				memory[i] = memory[index-1];
			
			return true;
		}
		
		return false;
	}
	
	public String toString()
	{
		String str = "";
		
		return str;
	}
}