package se.lexicon.teresia.thegolfgame;


import static org.junit.Assert.*;

import org.junit.Test;

public class GolfGame_test {

	@Test
	public void test_distance()
	{
		double theDistance = TheGolfGame.calculateDistance((double)45,(double) 56);
		assertEquals(320,theDistance,0);	
		
	}
	@Test
	public void test_un()
	{
		double theDistance = TheGolfGame.calculateDistance((double)45,(double) 56);
		assertEquals(320,theDistance,0);	
		
	}

}
