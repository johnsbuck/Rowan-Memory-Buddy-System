package binarybuddysystem.producerconsumer;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import binarybuddysystem.MMU;
import binarybuddysystem.view.MainWindow;

/**
 * This class automatically allocates and deallocates
 * to the MMU and GUI.
 * @author John Bucknam
 */

public class AllocatorDeallocator
{
	//Number of possible chunks
	private static int N;
	//Animation time (msec)
	private static int msec;
	
	private static int memorySize;
	private static int minChunk;
	
	//Custom allocator (producer)
	private static Allocator alloc;
	//Custom deallocator (consumer)
	private static Deallocator dealloc;
	//Custom monitor for Allocator and Deallocator
	private static AllocDeallocMonitor mon;
	//Viewer for MMU
	private static MainWindow mw;
	
	//Used for randomization in sleep and lottery
	private static Random rn = new Random();
	
	/**
	 * This constructor takes the memory size and minimum chunk size
	 * for the MainWindow with the MMU as well as to act as constant
	 * throughout the instance's lifespan.
	 * @param memorySize
	 * @param minChunk
	 */
	public AllocatorDeallocator(int memorySize, int minChunk)
	{
		//Set basic constants
		this.memorySize = memorySize;
		this.minChunk = minChunk;
		N = memorySize/minChunk;
		msec = 500; //500 msec
		
		//Set custom classes
		mw = new MainWindow(true, memorySize, minChunk);
		mon = new AllocDeallocMonitor();
		alloc = new Allocator();
		dealloc = new Deallocator();
		
		//Start threads
		alloc.start();
		dealloc.start();
	}
	
	/**
	 * This constructor takes the memory size and minimum chunk size
	 * for the MainWindow with the MMU as well as to act as constant
	 * throughout the instance's lifespan. It will also set the speed
	 * of the animation for the MainWindow.
	 * @param memorySize
	 * @param minChunk
	 */
	public AllocatorDeallocator(int memorySize, int minChunk, int speed)
	{
		//Set constants
		AllocatorDeallocator.memorySize = memorySize;
		AllocatorDeallocator.minChunk = minChunk;
		N = memorySize/minChunk;
		msec = speed;
		
		//Set custom classes
		mw = new MainWindow(true, memorySize, minChunk);
		mon = new AllocDeallocMonitor();
		alloc = new Allocator();
		dealloc = new Deallocator();
		
		//Start threads
		alloc.start();
		dealloc.start();
	}
	
	/**
	 * This constructor takes the memory size and minimum chunk size
	 * for the MainWindow with the MMU as well as to act as constant
	 * throughout the instance's lifespan. It will also set the speed
	 * of the animation for the MainWindow as well as the size of the 
	 * Main Window's chunks.
	 * @param memorySize
	 * @param minChunk
	 */
	public AllocatorDeallocator(int memorySize, int minChunk, int speed,
			int colorSize)
	{
		//Set constants
		AllocatorDeallocator.memorySize = memorySize;
		AllocatorDeallocator.minChunk = minChunk;
		N = memorySize/minChunk;
		msec = speed;
		
		//Set custom classes
		mw = new MainWindow(true, memorySize, minChunk, colorSize);
		mon = new AllocDeallocMonitor();
		alloc = new Allocator();
		dealloc = new Deallocator();
		
		//Start Threads
		alloc.start();
		dealloc.start();
	}
	
	/**
	 * This class is used to automatically
	 * allocate processes to the MainWindow and MMU.
	 * @author John Bucknam
	 *
	 */
	private static class Allocator extends Thread
	{
		private boolean running;
		
		@Override
		public void run()
		{
			running = true;
			while(running)
			{
				//System.out.println("Allocating");
				mon.allocate();
				
				//Randomized sleep cycle based on animation time
				try
				{
					if(rn.nextInt(10) == 0)
						this.sleep(msec*2);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	/**
	 * This class is used to automatically
	 * deallocate processes to the MainWindow and MMU.
	 * @author John Bucknam
	 *
	 */
	private static class Deallocator extends Thread
	{
		private boolean running;
		
		@Override
		public void run()
		{
			running = true;
			while(running)
			{
				//System.out.println("Deallocating");
				mon.deallocate();
				
				//Randomized sleep cycle based on animation time
				try
				{
					if(rn.nextInt(5) == 0)
						this.sleep(msec*2);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	/**
	 * This class acts as a monitor between Allocators and Deallocators.
	 * It may allocate new, automated processes to the MainWindow and MMU
	 * so long as it isn't filled, and it may deallocate currently existing
	 * processes, so long as one exists.
	 * @author John Bucknam
	 *
	 */
	private static class AllocDeallocMonitor
	{
		private int chunkVar; //Chunk Variations(variation on chunk size)
		private int [] chunkLotto;
		private static ArrayList<String> processes;
		private static ArrayList<Integer> processSizes;
		private int count;
		
		/**
		 * This default constructor uses information from the AllocatorDeallocator class
		 * to set all of its variables. The AllocatorDeallocator must be defined before
		 * doing this operation.
		 */
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
		
		/**
		 * This method attempts to allocate a random process into the MMU and MainWindow,
		 * and stores its name into a processes list for later deallocation.
		 */
		public synchronized void allocate()
		{
			//If there are no more places for the smallest chunk, wait.
			if(chunkLotto[0] == 0)
				sleep();
			
			//magnitude = log2(2^n) for a random possible set of n
			int magnitude = lottery();
			
			//Max = 2^magnitude
			//Min = 2^(magnitude - 1) + 1
			int max = (int) Math.pow(2, magnitude)*minChunk;
			int min = (magnitude > 0) ? ((int) Math.pow(2, magnitude - 1)*minChunk + 1) : 1;
			
			//Set process to be possible min to max
			int processSize = (rn.nextInt((max - min) + 1) + min);
			//Set process name based on details of magnitude and processSize
			String processName = "P" + chunkLotto[magnitude] + " : " + magnitude + " : " + processSize;
			
			//If process does not exist
			if(!processes.contains(processName))
			{
				//Attempt to allocate process
				if(mw.allocate(processName, processSize))
				{
					//Add processName to list
					processes.add(processName);
					//Add magnitude for later use
					processSizes.add(magnitude);
					//Remove lottos based on magnitude
					removeLottos(magnitude);
					
					//Animation time
					try
					{
						Thread.sleep(msec);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					
					//Notify deallocator if there is a new process added to the MMU
					randomNotify();
				}
			}
		}
		
		/**
		 * This method returns a possible magnitude that can be used
		 * to set a process size.
		 * @return
		 */
		private int lottery()
		{
			//Randomly chooses possible magnitude
			//Max = count
			//Min = 1
			int lotto = rn.nextInt(count) + 1;
			int magnitude = -1;
			boolean found = false;
			
			//Until a chunk that can fit the magnitude is found
			for(int i = 0; i < chunkVar && !found; i++)
			{
				//If chunkLotto is greater than or equal to lotto
				if(lotto <= chunkLotto[chunkVar - 1 - i])
				{
					found = true;
					//Set magnitude
					magnitude = chunkVar - 1 - i;
				}
			}
			
			return magnitude;
		}
		
		/**
		 * Removes lottos based on magnitude of chunk given to match
		 * possible chunks that can be added to MMU
		 * @param magnitude
		 */
		private void removeLottos(int magnitude)
		{
			//Remove 2^magnitude from count as they can no longer be used as chunks
			count -= Math.pow(2, magnitude);
			
			//For lesser magnitudes, remove lotteries of equal size
			for(int i = magnitude; i >= 0 ; i--)
			{
				//Set number of chunks to be removed
				int removal = (int) Math.pow(2, magnitude - i);
				
				//If there are no more possible chunks
				if(chunkLotto[i] <= removal)
					chunkLotto[i] = 0;
				else
					chunkLotto[i] -= removal;
			}
			
			//For magnitudes higher, check if there is no more room for it
			for(int i = magnitude + 1; i < chunkVar; i++)
			{
				//If there is no more space for x number of chunks
				while(count / ((int) Math.pow(2, i)) < chunkLotto[i])
				{
					//Remove from chunk lotto
					chunkLotto[i]--;
				}
			}
		}
		
		/**
		 * This method is used to attempt to deallocate 
		 * processes that already exist within the MMU and MainWindow and then
		 * removes them from listing if succcessful.
		 */
		public synchronized void deallocate()
		{
			//If there is enough room to fit the largest chunk, wait.
			if(chunkLotto[chunkVar - 1] == 1)
				sleep();
			
			//Choose random process
			int index = rn.nextInt(processes.size());
			
			//Take randomly chosen process
			String process = processes.remove(index);
			int processSize = processSizes.remove(index);
			
			//Attempt to deallocate
			if(mw.deallocate(process))
			{
				//If successful, renew lottos
				renewLottos(processSize);
				
				//Animation time
				try
				{
					Thread.sleep(msec);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				
				//Notify the Allocator that more space has been made
				randomNotify();
			}
			else
			{
				//If failed, return the information back to the process list
				processes.add(process);
				processSizes.add(processSize);
			}
		}
		
		/**
		 * This method renews lottos using the process size as the magnitude.
		 * @param index
		 * @param processSize
		 */
		private void renewLottos(int processSize)
		{
			//Set magnitude
			int magnitude = processSize;
			
			//Add chunk space back to count
			count += Math.pow(2, magnitude);
			
			//For lesser magnitudes, remove lotteries of equal size
			for(int i = magnitude; i >= 0 ; i--)
			{
				//Add lottos back to smaller magnitudes
				int additive = (int) Math.pow(2, magnitude - i);
				
				chunkLotto[i] += additive;
			}
			
			//For magnitudes higher, check if there is no more room for it
			for(int i = magnitude + 1; i < chunkVar; i++)
			{
				//If there is more space that could possibly be used,
				//add a lotto
				while(count / ((int) Math.pow(2, i)) > chunkLotto[i])
				{
					chunkLotto[i]++;
				}
			}
		}
		
		/**
		 * This method randomly notifies threads
		 */
		private void randomNotify()
		{
			if(rn.nextInt(2) == 0)
			{
				notify();
			}
		}
		
		/**
		 * This method notifies threads and
		 * sets the current thread to sleep.
		 */
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
