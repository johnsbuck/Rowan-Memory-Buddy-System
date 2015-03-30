package binarybuddysystem.test;

import static org.junit.Assert.*;
import binarybuddysystem.MMU;
import binarybuddysystem.Process;

import org.junit.Before;
import org.junit.Test;

public class MMUTest
{
	MMU testMMU;
	Process p1;
	Process p2;
	Process p3;
	Process p4;
	
	@Before
	public void setUp()
	{
		testMMU = new MMU(1024, 64);
		p1 = new Process("Process 1", 2);	//  2B  (Fills 64)
		p2 = new Process("Process 2", 63);	// 63B  (Fills 64)
		p3 = new Process("Process 3", 129);	// 129B (Fills 256)
		p4 = new Process("Process 4", 768);	// 768B	(Fills 1024)
	}
	
	@Test
	public void AllocTest1()
	{
		assertTrue(testMMU.allocate(p1));
		assertTrue(testMMU.allocate(p2));
		assertTrue(testMMU.allocate(p3));
		assertFalse(testMMU.allocate(p4));
	}
	
	@Test
	public void AllocTest2()
	{
		assertTrue(testMMU.allocate(p4));
		assertFalse(testMMU.allocate(p1));
	}
	
	@Test
	public void AllocDeallocTest1()
	{
		assertTrue(testMMU.allocate(p4));
		assertFalse(testMMU.allocate(p1));
		
		assertTrue(testMMU.deallocate("Process 4"));
		
		assertTrue(testMMU.allocate(p1));
	}
	
	@Test
	public void AllocDeallocTest2()
	{
		assertTrue(testMMU.allocate(p3));
		
		assertTrue(testMMU.deallocate("Process 3"));
		
		assertTrue(testMMU.allocate(p1));
		
		assertTrue(testMMU.deallocate("Process 1"));
	}

}
