package binarybuddysystem.test;

import static org.junit.Assert.*;
import binarybuddysystem.MMU;
import binarybuddysystem.Process;

import org.junit.Before;
import org.junit.Test;

//Edited allocate method, need to rework with integer values

public class MMUTest
{
	MMU testMMU;
	Process p0, p1, p2, p3, p4, p5, p6;
	
	@Before
	public void setUp()
	{
		testMMU = new MMU(1024, 64);
		p0 = new Process("Process 0", 32);	// 32B  (Fills 64 or 1 chunk)
		p1 = new Process("Process 1", 2);	//  2B  (Fills 64 or 1 chunk)
		p2 = new Process("Process 2", 65);	// 65B  (Fills 128 or 2 chunk)
		p3 = new Process("Process 3", 129);	// 129B (Fills 256 or 4 chunks)
		p4 = new Process("Process 4", 768);	// 768B	(Fills 1024 or 16 chunks)
		p5 = new Process("Process 5", 257); // 257B (Fills 512 or 8 chunks)
		p6 = new Process("Process 6", 358); // 358B (Fills 512 or 8 chunks)
	}
	
	/**
	 * This test is made to see whether the MMU actually correctly allocates the
	 * right processes. It does not test positions, only whether it is allocated in
	 * general.
	 */
	@Test
	public void AllocTest1()
	{
		assertNotEquals(testMMU.allocate(p0.getName(), p0.size()), null);
		assertNotEquals(testMMU.allocate(p1.getName(), p1.size()), null);
		assertNotEquals(testMMU.allocate(p2.getName(), p2.size()), null);
		assertNotEquals(testMMU.allocate(p3.getName(), p3.size()), null);
		//Because it requires the whole MMU, this can not be filled
		assertEquals(testMMU.allocate(p4.getName(), p4.size()), null);
		
		assertNotEquals(testMMU.allocate(p5.getName(), p5.size()), null);
		
		//The MMU should be filled at this point, meaning that this cannot be filled.
		assertEquals(testMMU.allocate(p0.getName(), p0.size()), null);
	}
	
	/**
	 * This test is to confirm that when the MMU is filled, no other processes
	 * can be allocated. This does not test the location of the process, only
	 * whether they are allocated or not.
	 */
	@Test
	public void AllocTest2()
	{
		assertNotEquals(testMMU.allocate(p4.getName(), p4.size()), null);
		assertEquals(testMMU.allocate(p1.getName(), p1.size()), null);
	}
	
	/**
	 * This test takes what we know from the other two test, that the MMU
	 * is correct with its allocations, and includes basic deallocation to
	 * confirm that it can correctly deallocate processes.
	 */
	@Test
	public void AllocDeallocTest1()
	{
		//This process will take up the entire MMU, meaning no other processes
		//Should be able to be allocated
		assertNotEquals(testMMU.allocate(p4.getName(), p4.size()), null);
		assertEquals(testMMU.allocate(p1.getName(), p1.size()), null);
		
		//The process that maxed out the MMU is now deallocated, leaving
		//all the space it allocated for the other processes
		assertTrue(testMMU.deallocate("Process 4"));
		
		//These processes should now be able to allocate to the MMU
		assertNotEquals(testMMU.allocate(p1.getName(), p1.size()), null);
		assertNotEquals(testMMU.allocate(p3.getName(), p3.size()), null);
		assertNotEquals(testMMU.allocate(p5.getName(), p5.size()), null);
		
		//Since the previous processes were able to be allocated, they should
		//be able to be deallocated
		assertTrue(testMMU.deallocate("Process 1"));
		assertTrue(testMMU.deallocate("Process 3"));
		assertTrue(testMMU.deallocate("Process 5"));
		
		//Because the MMU is empty again, any split chunks should be merged together
		//and the maxed process should be able to take up the entire MMU.
		assertNotEquals(testMMU.allocate(p4.getName(), p4.size()), null);
	}
	
	/**
	 * This test is meant to see whether the chunks being split apart accurately
	 * as well as merging together after deallocating.
	 */
	@Test
	public void AllocDeallocTest2()
	{
		//This process should split the process of 16 chunks to 8 | 8 chunks
		// and then to 4 | 4 | 8 chunks, then allocating itself to one of the 4 chunks
		assertNotEquals(testMMU.allocate(p3.getName(), p3.size()), null);
		
		//This process should simply allocate itself to the 8 chunks in the MMU.
		assertNotEquals(testMMU.allocate(p5.getName(), p5.size()), null);
		
		//This process should split the empty 4 chunks to 2 | 2 chunks and then split one
		//of the 2 chunks to 1 | 1 | 2 chunks and then allocating itself to one of the
		//1 chunks.
		assertNotEquals(testMMU.allocate(p1.getName(), p1.size()), null);
		
		//This deallocation of process 3 should not merge any chunks since it's buddy
		//has been split by the previous allocation.
		assertTrue(testMMU.deallocate("Process 3"));
		
		//To show that this is true, we will try putting in another chunk of the
		//same size as p5, which takes up half of the MMU.
		assertEquals(testMMU.allocate(p6.getName(), p6.size()), null);
		
		//A process of 2 chunks can fit in however, because of the split from p1.
		assertNotEquals(testMMU.allocate(p2.getName(), p2.size()), null);
		
		//Now, lets remove the 2 chunk and 1 chunk (p2 and p1) from the MMU.
		//This will create a 4 chunk slot. Note: The order of removal shouldn't matter.
		assertTrue(testMMU.deallocate("Process 2"));
		assertTrue(testMMU.deallocate("Process 1"));
		
		//Adding a 4-chunk process to the MMU.
		assertNotEquals(testMMU.allocate(p6.getName(), p6.size()), null);
		
		//Now, the processes in the MMU will be deallocated and a process that
		//needs to take up the entire MMU will be in placed to show that basic merging
		//exists within the MMU.
		assertTrue(testMMU.deallocate("Process 5"));
		assertTrue(testMMU.deallocate("Process 6"));
		
		assertNotEquals(testMMU.allocate(p4.getName(), p4.size()), null);
	}
	
	/**
	 * This test will show that more advanced merging works within the MMU and that 
	 * this MMU can merge any set necessary.
	 */
	@Test
	public void AllocDeallocTest3()
	{
		//Allocating 64 bytes (1 chunk) processes to deallocate
		for(int i = 0; i < 16; i++)
		{
			assertNotEquals(testMMU.allocate(Integer.toString(i), 64), null);
		}
		
		//Deallocating neighboring chunks that are not buddies to show
		//That only buddies can merge.
		
		//Takes 1, 2 then skips 2
		for(int i = 1; i < 16; i++)
		{
			assertTrue(testMMU.deallocate(Integer.toString(i)));
			i++;
			assertTrue(testMMU.deallocate(Integer.toString(i)));
			i += 2;
		}
		
		//Tries to add a 2 chunk process, but should fail,
		//despite having two 1 chunk slots.
		assertEquals(testMMU.allocate(p2.getName(),p2.size()), null);
		
		//Will now remove one 1 chunk to have it and its buddy merge to form one 2 chunk
		assertTrue(testMMU.deallocate(Integer.toString(0)));
		
		//Allocating to 2 chunk
		assertNotEquals(testMMU.allocate(p2.getName(),p2.size()), null);
		//Succeeded
		assertTrue(testMMU.deallocate(p2.getName()));
		
		//Now removing distant 1 chunk which will form another 2 chunk that won't merge
		//with the other 2 chunk currently existing.
		assertTrue(testMMU.deallocate(Integer.toString(15)));
		
		//Shouldn't allocate a 4 chunk process
		assertEquals(testMMU.allocate(p3.getName(), p3.size()), null);
		
		//Now, we will remove any remaining chunks in a systematic order to test that
		//we will only have chunks of size 2. Note: We will not remove 12 because we removed 15.
		for(int i = 4; i < 12; i+= 4)
		{
			assertTrue(testMMU.deallocate(Integer.toString(i)));
		}
		
		//Showing that there are no chunks of size 4
		assertEquals(testMMU.allocate(p3.getName(), p3.size()), null);
		
		//We will now reallocate process 0 and remove all others to show that
		//Chunks will stay split until it's partner is found.
		assertNotEquals(testMMU.allocate(Integer.toString(0),64), null);
		
		for(int i = 3; i < 15; i += 4)
		{
			assertTrue(testMMU.deallocate(Integer.toString(i)));
		}
		
		//Removing outlier from for loop
		assertTrue(testMMU.deallocate(Integer.toString(12)));
		
		//We will now try to add a process of size 8 chunks to show that
		//it properly merged half of the chunks.
		assertNotEquals(testMMU.allocate(p5.getName(),p5.size()), null);
		
		//Attempting to add another process of same size, which should fail for:
		// 1. Over the MMU's limit
		// 2. No remaining chunk slots of size 8.
		assertEquals(testMMU.allocate(p6.getName(), p6.size()), null);
		
		//Removing processes to show that MMU can merge recursively.
		assertTrue(testMMU.deallocate(Integer.toString(0)));
		assertTrue(testMMU.deallocate(p5.getName()));
		
		//Allocating to maxed-out process which should be successful if
		//the MMU merged all of the empty chunks recursively.
		assertNotEquals(testMMU.allocate(p4.getName(), p4.size()), null);
	}

}
