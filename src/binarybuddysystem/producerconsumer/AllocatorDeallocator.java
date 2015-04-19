package binarybuddysystem.producerconsumer;

import java.util.ArrayList;
import java.util.Random;

import binarybuddysystem.MMU;
import binarybuddysystem.view.MainWindow;

public class AllocatorDeallocator
{
	private static int N;
	private static int memorySize;
	private static int minChunk;
	private static Allocator alloc;
	private static Deallocator dealloc;
	private static AllocDeallocMonitor mon;
	//private static MainWindow mw;
	
	public AllocatorDeallocator(int memorySize, int minChunk)
	{
		AllocatorDeallocator.memorySize = memorySize;
		AllocatorDeallocator.minChunk = minChunk;
		N = memorySize/minChunk;
		//mw = new MainWindow(new MMU(memorySize, minChunk), memorySize, minChunk);
		mon = new AllocDeallocMonitor();
		alloc = new Allocator();
		dealloc = new Deallocator();
		alloc.start();
		dealloc.start();
	}
	
	static class Allocator extends Thread
	{
		private boolean running;
		
		@Override
		public void run()
		{
			running = true;
			while(running)
			{
				System.out.println("Allocating");
				mon.allocate();
			}
		}
	}
	
	static class Deallocator extends Thread
	{
		private boolean running;
		
		@Override
		public void run()
		{
			running = true;
			while(running)
			{
				System.out.println("Deallocating");
				mon.deallocate();
			}
		}
	}
	
	static class AllocDeallocMonitor
	{
		private int chunkVar; //Chunk Variations(variation on chunk size)
		private int [] chunkLotto;
		private static ArrayList<String> processes;
		private static ArrayList<Integer> processSizes;
		private Random rn = new Random();
		int count;
		
		public AllocDeallocMonitor()
		{
			//The # of possible chunks available
			count = N;
			//How many possible 2^x are in the MMU
			chunkVar = (int) (Math.log(N)/Math.log(2)) + 1;
			chunkLotto = new int[chunkVar];
			processes = new ArrayList<String>(N);
			processSizes = new ArrayList<Integer>(N);
			
			for(int i = 0; i < chunkVar; i++)
			{
				chunkLotto[chunkVar - 1 - i] = (int) Math.pow(2,i);
			}
		}
		
		public synchronized void allocate()
		{
			//If there are no more places for the smallest chunk, wait.
			if(chunkLotto[0] == 0)
				sleep();
			
			int magnitude = lottery();
			
			//Max = 2^magnitude
			//Min = 2^(magnitude - 1) + 1
			int max = (int) Math.pow(2, magnitude)*minChunk;
			int min = (magnitude > 0) ? ((int) Math.pow(2, magnitude - 1) + 1)*minChunk : 0;
			
			int processSize = (rn.nextInt((max - min) + 1) + min) * minChunk;
			String processName = "P" + chunkLotto[magnitude] + " : " + magnitude + " - " + processSize;
			
			processes.add(processName);
			processSizes.add(magnitude);
			removeLottos(magnitude);

			try
			{
				Thread.sleep(750);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		private int lottery()
		{
			//Max = count
			//Min = 1
			int lotto = rn.nextInt(count) + 1;
			int magnitude = -1;
			
			boolean found = false;
			for(int i = 0; i < chunkVar && !found; i++)
			{
				if(lotto <= chunkLotto[chunkVar - 1 - i])
				{
					found = true;
					magnitude = chunkVar - 1 - i;
				}
			}
			
			return magnitude;
		}
		
		private void removeLottos(int magnitude)
		{
			count -= Math.pow(2, magnitude);
			
			//For lesser magnitudes, remove lotteries of equal size
			for(int i = magnitude; i >= 0 ; i--)
			{
				int removal = (int) Math.pow(2, magnitude - i);
				
				if(chunkLotto[i] <= removal)
					chunkLotto[i] = 0;
				else
					chunkLotto[i] -= removal;
			}
			
			//For magnitudes higher, check if there is no more room for it
			for(int i = magnitude + 1; i < chunkVar; i++)
			{
				if(count / ((int) Math.pow(2, i)) < chunkLotto[i])
				{
					chunkLotto[i]--;
				}
			}
		}
		
		public synchronized void deallocate()
		{
			//If there is enough room to fit the largest chunk, wait.
			if(chunkLotto[chunkVar - 1] == 1)
				sleep();
			
			int index = rn.nextInt(processes.size());
			
			String process = processes.remove(index);
			int processSize = processSizes.remove(index);
			/*
			if(mw.deallocate(process))
			{
				
			}
			*/
			
			renewLottos(index, processSize);
			
			try
			{
				Thread.sleep(750);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
		}
		
		private void renewLottos(int index, int processSize)
		{
			int magnitude = processSize;
			count += Math.pow(2, magnitude);
			
			//For lesser magnitudes, remove lotteries of equal size
			for(int i = magnitude; i >= 0 ; i--)
			{
				int additive = (int) Math.pow(2, magnitude - i);
				
				chunkLotto[i] += additive;
			}
			
			//For magnitudes higher, check if there is no more room for it
			for(int i = magnitude + 1; i < chunkVar; i++)
			{
				if(count / ((int) Math.pow(2, i)) > chunkLotto[i])
				{
					chunkLotto[i]++;
				}
			}
		}
		
		private void sleep()
		{
			notify();
			
			try
			{
				wait();
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
