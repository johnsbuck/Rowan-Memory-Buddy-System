package binarybuddysystem;

public class MMU
{
	Process[] memory; 	//Might make wrapper class (Chunk)
						//Each element is the minimum chunk size
						//Should it be a LinkedList instead?
	
	int minChunkSize;	//Minimum chunk size (2^k)
	int memorySize;		//Total Memory Size	(2^n)
	/*
	 * memorySize = memory.length * minChunkSize;
	 */
	
	public MMU()
	{
		
	}
	
	public boolean allocate(Process p)
	{
		return false;
	}
	
	public boolean deallocate(String name)
	{
		int i = 0;
		while(i < memorySize)
		{
			if(memory[i].getName().equals(name))
			{
				int chunkSize = memory[i].size();
				memory[i].emptyProcess();
				merge(i, chunkSize);
				return true;
			}
			//Able to skip more memory this way
			i += memory[i].size()/minChunkSize; 
			
			/* Reason for having Chunk class: Able to show internal
			 * fragmentation and minimum chunkSize equally without
			 * any struggle.
			 */
		}
		
		return false;
	}
	
	private boolean partition(int index)	//index or hole
	{
		return false;
	}
	
	private boolean merge(int index, int chunkSize)		//index or hole
	{
		int a = chunkSize/minChunkSize;
		if((memory[index].buddyReference == memory[index+a].buddyReference)
				&& memory[index].empty() && memory[index+a].empty())
		{
			memory[index+1] = memory[index];
			memory[index] = new Process((chunkSize * 2));	//Chunk doubled in size
			return true;
		}
		else if(index != 0 && (memory[index].buddyReference == memory[index-1].buddyReference)
				&& memory[index].empty() && memory[index-1].empty())
		{
			memory[index-1] = memory[index];
			memory[index] = new Process((chunkSize * 2));	//Chunk doubled in size
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