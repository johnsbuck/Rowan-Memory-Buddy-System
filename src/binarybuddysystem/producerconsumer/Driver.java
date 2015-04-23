package binarybuddysystem.producerconsumer;

public class Driver
{
	public static void main(String[] args)
	{
		AllocatorDeallocator ad;
		if(args.length == 2)
			ad = new AllocatorDeallocator(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		else if(args.length == 3)	//Memory Size | Chunk Size | Speed (msec)
			ad = new AllocatorDeallocator(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		else if(args.length == 4)	//Memory Size | Chunk Size | Color Size | Speed (msec)
			ad = new AllocatorDeallocator(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		else
			ad = new AllocatorDeallocator(1024, 64);
	}
}
