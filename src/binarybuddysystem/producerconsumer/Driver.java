package binarybuddysystem.producerconsumer;

import binarybuddysystem.view.MainWindow;

/**
 * This class creates an automated allocator/deallocation
 * in the custom binarybuddysystem.MMU with GUI viewer
 * to test its bounds.
 * 
 * It takes command line inputs to read the:
 * 	1. Auto	(automated or manual)	[Default: Manual]
 * 	2. MMU Size				[default: 1024]	
 * 	3. Minimum Chunk Size	[default: 64  ]
 * 	4. Speed of Allocator/Deallocation (msec) [default: 30] (If not automated, will not be there)
 * 	5. Viewer chunk size 	[default: 1 (extends if smaller than default viewer size)]
 * 
 * The metric of the MMU size (bit, byte, KB) does not matter so long
 * as it correlates with the minimum Chunk size
 * @author John Bucknam
 *
 */
public class Driver
{
	public static void main(String[] args)
	{
		if(args.length < 3)
			System.exit(0);
		
		boolean auto = false;
		
		if(args[0].equals("automated") || args[0].equals("auto"))
				auto = true;
		
		int[] argConvert = testArgs(args);
		
		if(argConvert == null)
			System.exit(0);
		
		if(auto)
		{
			AllocatorDeallocator ad;
			if(args.length == 3)
				ad = new AllocatorDeallocator(argConvert[0], argConvert[1]);
			else if(args.length == 4)	//Memory Size | Chunk Size | Speed (msec)
				ad = new AllocatorDeallocator(argConvert[0], argConvert[1], argConvert[2]);
			else if(args.length == 5)	//Memory Size | Chunk Size | Speed (msec) | Color Size
				ad = new AllocatorDeallocator(argConvert[0], argConvert[1], argConvert[2], argConvert[3]);
			else
				ad = new AllocatorDeallocator(1024, 64);
		}
		else
		{
			MainWindow ad;
			if(args.length == 3)
				ad = new MainWindow(false, argConvert[0], argConvert[1]);
			else if(args.length == 4)	//Memory Size | Chunk Size | Color Size
				ad = new MainWindow(false, argConvert[0], argConvert[1], argConvert[2]);
			else
				ad = new MainWindow(false, 1024, 64);
			
			ad.show();
		}
	}
	
	/**
	 * This method tests the command line input to confirm it is
	 * usable for this program.
	 * @param args
	 * @return converted args if succeeded, false if it did not.
	 */
	public static int[] testArgs(String[] args)
	{
		//Will hold the converted values
		int[] argConvert = new int[args.length - 1];
		
		//For each arg
		for(int i = 1; i < args.length; i++)
		{
			//Try to convert the argument to an Integer
			try
			{
				argConvert[i-1] = Integer.parseInt(args[i]);
				if(argConvert[i-1] <= 0) //Return null if arg < 0
				{
					System.err.println("OutOfBoundsException: All arguments must be > 0.");
					return null;
				}
			}
			catch(NumberFormatException e) //Return null if it is not a number
			{
				System.err.println("NumberFormatException: All arguments must be numbers.");
				return null;
			}
		}
		
		//Test MMU size
		//Uses bitwise function to see if it the positive number is 2^n
		if((argConvert[0] & (argConvert[0]-1)) != 0)
		{
			System.err.println("InvalidSizeException: The MMU size must be 2^n.");
			return null;
		}
		//Test minimum chunk size
		//Must be 2^n and less than or equal to MMU size
		if((argConvert[1] & (argConvert[1]-1)) != 0 || argConvert[1] > argConvert[0])
		{
			System.err.println("InvalidSizeException: The mininum chunk size must be 2^n\n"
					+ "and less than or equal to the MMU size.");
			return null;
		}
		
		return argConvert;
	}
}
