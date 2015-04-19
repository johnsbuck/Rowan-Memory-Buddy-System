package binarybuddysystem.producerconsumer;

import java.util.ArrayList;

public class AllocatorDeallocator
{
	private static int N;
	private static int memorySize;
	private static int minChunk;
	private static Allocator alloc = new Allocator();
	private static Deallocator dealloc = new Deallocator();
	private static AllocDeallocMonitor mon;
	private static ArrayList<String> processNames;
	
	public AllocatorDeallocator(int memorySize, int minChunk)
	{
		this.memorySize = memorySize;
		this.minChunk = minChunk;
		N = memorySize/minChunk;
		
		mon = new AllocDeallocMonitor();
		
		processNames = new ArrayList<String>(N);
	}
	
	static class Allocator implements Runnable
	{
		private boolean running;
		
		@Override
		public void run()
		{
			running = true;
			while(running)
			{
				mon.allocate();
			}
		}
		
		public void stop()
		{
			running = false;
		}
	}
	
	static class Deallocator implements Runnable
	{
		private boolean running;
		
		@Override
		public void run()
		{
			running = true;
			while(running)
			{
				mon.deallocate();
			}
		}
		
		public void stop()
		{
			running = false;
		}
	}
	
	static class AllocDeallocMonitor
	{
		private int chunkVar; //Chunk Variations(variation on chunk size)
		private int [] chunkLotto;
		
		public AllocDeallocMonitor()
		{
			//How many possible 2^x are in the MMU
			chunkVar = (int) (Math.log(N)/Math.log(2)) + 1;
			chunkLotto = new int[chunkVar];
			
			for(int i = 0; i < chunkVar; i++)
			{
				chunkLotto[chunkVar - 1 - i] = (int) Math.pow(2,i);
			}
		}
		
		public synchronized void allocate()
		{
			//If there are no more places for the smallest chunk, wait
			if(chunkLotto[chunkVar - 1] == 0)
				sleep();
			
			
			
		}
		
		public synchronized void deallocate()
		{
			//If there is enough room to fit the largets chunk, wait
			if(chunkLotto[0] == 1)
				sleep();
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
