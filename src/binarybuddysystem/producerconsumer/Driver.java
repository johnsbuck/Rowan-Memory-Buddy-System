package binarybuddysystem.producerconsumer;

/**
 * This class creates an automated allocator/deallocation
 * in the custom binarybuddysystem.MMU with GUI viewer
 * to test its bounds.
 * 
 * It takes command line inputs to read the:
 * 	1. MMU Size				[default: 1024]	
 * 	2. Minimum Chunk Size	[default: 64  ]
 * 	3. Speed of Allocator/Deallocation (msec) [default: 30]
 * 	4. Viewer chunk size 	[default: 1 (extends if smaller than default viewer size)]
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
		int[] argConvert = testArgs(args);
		
		if(argConvert == null)
			System.exit(0);
		
		AllocatorDeallocator ad;
		if(args.length == 2)
			ad = new AllocatorDeallocator(argConvert[0], argConvert[1]);
		else if(args.length == 3)	//Memory Size | Chunk Size | Speed (msec)
			ad = new AllocatorDeallocator(argConvert[0], argConvert[1], argConvert[2]);
		else if(args.length == 4)	//Memory Size | Chunk Size | Color Size | Speed (msec)
			ad = new AllocatorDeallocator(argConvert[0], argConvert[1], argConvert[2], argConvert[3]);
		else
			ad = new AllocatorDeallocator(1024, 64);
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
		int[] argConvert = new int[args.length];
		
		//For each arg
		for(int i = 0; i < args.length; i++)
		{
			//Try to convert the argument to an Integer
			try
			{
				argConvert[i] = Integer.parseInt(args[i]);
				if(argConvert[i] <= 0) //Return null if arg < 0
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
