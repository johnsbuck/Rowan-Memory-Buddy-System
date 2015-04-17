package binarybuddysystem;

import java.util.ArrayList;
import java.util.LinkedList;

public class MMU
{
	private ArrayList<LinkedList<Slot>> memory; 	//Might make wrapper class (Chunk)
						//Each element is the minimum chunk size
						//Should it be a LinkedList instead?
	
	private int minChunk;	//Minimum chunk size (2^k)
	private int memorySize;		//Total Memory Size	(2^n)
	private int numChunks;
	int length;
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
		minChunk = chunkSize;
		numChunks = memorySize/chunkSize;
		
		int memTest = memorySize;
		
		//Set length of ArrayList
		for(length = 0; memTest >= chunkSize; length++)
			memTest = memTest >> 1;
			
		//initialize memory # of Chunks = memorySize/ChunkSize
		//If prior 2 are chosen correctly, should divide evenly
		memory = new ArrayList<LinkedList<Slot>>(length);
		
		for(int i = 0; i < length; i++)
		{
			memory.add(new LinkedList<Slot>());
		}
		//Initialize that the entire memory is one unit that is a hole
		memory.get(length-1).add(new Slot(null, 0));
	}
	
	/**
	 * Allocates memory for a Process
	 * @param name name of process to add
	 * @param size size of process to add (in bytes)
	 * @return two integers, first being the location and the second being the chunk size. If they are equal to -1, then it didn't allocate
	 */
	public int[] allocate(String name, int size)
	{
		Process p = new Process(name, size);
		
		if(getProcess(p.getName()) != null)
			return null;
		
		//chunksNeeded is how many minChunks does the slot need
		int index = minChunk;
		int i = 0;
		while(index < p.size())
		{
			index = index << 1;
			i++;
		}
		
		int chunksNeeded = index/minChunk;
		
		//Base case: There is a slot that is our size, and we are done.
		for(Slot s : memory.get(i))
		{
			if(s.isHole())
			{
				s.setProcess(p);
				int [] ret = {s.getLocation(), chunksNeeded};
				return ret;
			}
		}
		
		//There is no slot that is open and our size, so we must try to make one.
		int oldIndex = index;
		for(int j = i + 1; j < length; j++)
		{	
			oldIndex = oldIndex << 1;
			for(Slot s : memory.get(j))
			{
				if(s.isHole())
				{
					int location = s.getLocation();
					memory.get(j).remove(s);
					while(j >= i)
					{
						j--;
						oldIndex = oldIndex >> 1;
						memory.get(j).add(new Slot(null, location));
						if(j == i)
						{
							memory.get(j).add(new Slot(p, location + chunksNeeded));
							int ret[] = {location, chunksNeeded};
							return ret;
						}
						location = location + oldIndex/minChunk;
					}
				}
			}
		}
		
		return null;
		/*
		Process p = new Process(name, size);
		//Need to find best chunkSize to fit process in
		int processSize = p.size();
		
		//If process of same name exists
		if(getProcess(p.getName()) != null)
			return null;
		
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
					int [] ret = {i, s.getSize()};
					return ret;
				}
			}
			i+=initialSlotSize;
		}
		
		return null;
		*/
	}
	
	/**
	 * Deallocates memory and removes a Process
	 * @param name Process to be removed
	 * @return true if successful, false if otherwise
	 */
	public boolean deallocate(String name)
	{
		for(int i = 0; i < length; i++)
		{
			for(Slot s : memory.get(i))
			{
				if(!s.isHole() && s.getProcess().getName().equals(name))
				{
					s.setProcess(null);
					merge(i, memory.get(i).indexOf(s));
					return true;
				}
			}
		}
		
		return false;
		
		/*
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
		*/
	}
	
	/**
	 * Tries to merge two empty chunks of the same size that are buddies
	 * @param index
	 * @return true if merges, false if there is no merge or fails to merge
	 */
	private boolean merge(int index, int slotIndex)		//index or hole
	{
		int ref = memory.get(index).get(slotIndex).getLocation();
		int neighbor = -1;
		
		int chunksNeeded = 1;
		
		for(int i = 0; i < index; i++)
		{
			chunksNeeded = chunksNeeded << 1;
		}
		
		//Buddies are neighboring and are only separated by the # of chunks they need.
		//If the # of minChunks located is even, the buddy is on the right
		//otherwise it is one the left and the buddy is even.
		if((ref/chunksNeeded) % 2 == 0)
			neighbor = ref + chunksNeeded;
		else
			neighbor = ref - chunksNeeded;
		
		memory.get(index).remove(slotIndex);
		
		for(Slot s : memory.get(index))
		{
			if(s.getLocation() == neighbor)
			{
				if(!s.isHole())
				{
					memory.get(index).add(new Slot(null, ref));
					return false;
				}
				
				memory.get(index).remove(s);
				
				if((ref/chunksNeeded) % 2 == 0)
				{
					memory.get(index+1).add(new Slot(null, ref));
					merge(index+1, memory.get(index+1).size() - 1);
				}
				else
				{
					memory.get(index+1).add(new Slot(null, neighbor));
					merge(index+1, memory.get(index+1).size() - 1);
				}
				
				return true;
			}
		}
		
		memory.get(index).add(new Slot(null, ref));
		return false;
		
		/*
		//If the chunk is the max size of memory, return false
		if(index >= numChunks || index < 0 || memory[index].getSize() ==  numChunks)
			return false;
		
		//Indexes past to the next different chunk
		int a = memory[index].getSize();
		
		//If it is within memory bounds, both chunks are empty, and are buddies
		//then merge
		if(index + a < numChunks && memory[index+a] != null && memory[index].isHole() && memory[index+a].isHole() 
				&& (memory[index].getRef() == memory[index+a].getPoint())
				&& (memory[index].getSize() == memory[index+a].getSize()))
		{
			//Index doubles the chunk size
			memory[index].doubleSize();
			//Index cuts the index point
			memory[index].setPoint(memory[index].getPoint()/2);
			
			
			//Is the new index even or odd?
			// If even, point to the next memory chunk
			// If odd, point to the previous memory chunk
			 
			setReference(index);
			
			memory[index+a] = null;
			
			//Can we merge again?
			merge(index);
			
			//Has merged
			return true;
		}
		//If the next chunk isn't a buddy, check the previous chunk 
		else if(index-a >= 0 && memory[index-a] != null && memory[index].isHole() && memory[index-a].isHole()
				&& (memory[index].getRef() == memory[index-a].getPoint())
				&& (memory[index].getSize() == memory[index-a].getSize()))
		{
			//Previous chunk doubles chunk size
			memory[index-a].doubleSize();
			//Previous index cuts index point
			memory[index-a].setPoint(memory[index-a].getPoint()/2);
			
			
			// Is the new index even or odd?
			// If even, point to the next memory chunk
			// If odd, point to the previous memory chunk
			 
			setReference(index-a);
			
			memory[index] = null;
			
			//Can we merge again?
			merge(index-a);
			
			//Has merged
			return true;
		}
		
		//Did not merge chunks
		return false;
		*/
	}
	
	public String getProcess(String name)
	{
		for(int i = 0; i < length; i++)
		{
			for(Slot s : memory.get(i))
			{
				if(!s.isHole() && s.getProcess().getName().equals(name))
				{
					return s.getProcess().toString();
				}
			}
		}
		
		return null;
	}
	
	public String toString(){
		String content = "This piece of memory of size " + memorySize
						+ " bytes contains " + numChunks 
						+ " chunks (" + minChunk + " bytes per chunk)\n";
		
		int chunksNeeded = 1;
		for(int i = 0; i < length; i++)
		{
			for(Slot s : memory.get(i))
				content += s  + ", Size: " + chunksNeeded + " chunk(s)\n";
			chunksNeeded = chunksNeeded << 1;
		}

		return content;
	}
}