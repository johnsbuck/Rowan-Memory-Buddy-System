package binarybuddysystem.producerconsumer;

import java.util.ArrayList;

public class AllocatorDeallocator
{
	private static int N;
	private static int memorySize;
	private static int minChunk;
	private static Allocator alloc;
	private static Deallocator dealloc;
	private static AllocDeallocMonitor mon;
	private static ArrayList<String> processNames;
	
	public AllocatorDeallocator(int memorySize, int minChunk)
	{
		this.memorySize = memorySize;
		this.minChunk = minChunk;
		N = memorySize/minChunk;
		
		dealloc = new Deallocator();
		alloc = new Allocator();
		mon = new AllocDeallocMonitor();
		
		processNames = new ArrayList<String>(N);
	}
	
	static class Allocator implements Runnable
	{
		private int chunkVar; //Chunk Variations(variation on chunk size)
		private int [] chunkLotto;
		
		public Allocator()
		{
			//How many possible 2^x are in the MMU
			chunkVar = (int) (Math.log(N)/Math.log(2)) + 1;
			chunkLotto = new int[chunkVar];
			
			for(int i = 0; i < chunkVar; i++)
			{
				chunkLotto[chunkVar - 1 - i] = (int) Math.pow(2,i);
			}
		}
		
		@Override
		public void run()
		{
			while(true)
			{
				
			}
		}
		
		private void deallocateProcess(String name)
		{
			
		}
	}
	
	static class Deallocator implements Runnable
	{
		@Override
		public void run()
		{
			while(true)
			{
				
			}
		}
		
		private void allocateProcess(String name, int size)
		{
			
		}
	}
	
	static class AllocDeallocMonitor
	{
		public synchronized void allocate()
		{
			
		}
		
		public synchronized void deallocate()
		{
			
		}
		
		private void sleep()
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
				System.err.println(e);
			}
		}
	}
}
