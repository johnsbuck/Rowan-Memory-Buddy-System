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
	Process p0, p1, p2, p3, p4, p5;
	
	@Before
	public void setUp()
	{
		testMMU = new MMU(1024, 64);
		p0 = new Process("Process 0", 32);	// 32B  (Fills 64)
		p1 = new Process("Process 1", 2);	//  2B  (Fills 64)
		p2 = new Process("Process 2", 63);	// 65B  (Fills 128)
		p3 = new Process("Process 3", 129);	// 129B (Fills 256)
		p4 = new Process("Process 4", 768);	// 768B	(Fills 1024)
		p5 = new Process("Process 5", 257); // 257B (Fills 512)
	}
	
	@Test
	public void AllocTest1()
	{
		assertTrue(testMMU.allocate(p1));
		assertTrue(testMMU.allocate(p2));
		assertTrue(testMMU.allocate(p3));
		assertFalse(testMMU.allocate(p4));
		assertTrue(testMMU.allocate(p5));
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
		assertTrue(testMMU.allocate(p3));
		assertTrue(testMMU.allocate(p5));
		
		assertTrue(testMMU.deallocate("Process 1"));
		assertTrue(testMMU.deallocate("Process 3"));
		assertTrue(testMMU.deallocate("Process 5"));
		
		assertTrue(testMMU.allocate(p4));
	}
	
	@Test
	public void AllocDeallocTest2()
	{
		assertTrue(testMMU.allocate(p3));
		
		assertTrue(testMMU.allocate(p5));
		
		assertTrue(testMMU.allocate(p1));
		
		assertTrue(testMMU.deallocate("Process 3"));
		
		assertTrue(testMMU.allocate(p0));
	}

}
