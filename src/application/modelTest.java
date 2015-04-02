package application;

import static org.junit.Assert.*;

import org.junit.Test;

public class modelTest {
	Model model = new Model();

	@Test
	public void assureEmpty() {
		assertEquals(0, model.getZipCodes().size());
		assertEquals(0, model.getDates().size());
		assertEquals(0, model.getTemps().size());
		assertEquals(0, model.getHighs().size());
		assertEquals(0, model.getLows().size());
		assertEquals(0, model.getHumidity().size());
		assertEquals(0, model.getWindTemps().size());
		assertEquals(0, model.getRainPCTS().size());
	}
	
	@Test
	public void testAdd() {
		assertEquals(0, model.getZipCodes().size());
		assertEquals(0, model.getDates().size());
		assertEquals(0, model.getTemps().size());
		assertEquals(0, model.getHighs().size());
		assertEquals(0, model.getLows().size());
		assertEquals(0, model.getHumidity().size());
		assertEquals(0, model.getWindTemps().size());
		assertEquals(0, model.getRainPCTS().size());
		model.add("test", "test", "test", "test", "test", "test", "test", "test");
		assertEquals(1, model.getZipCodes().size());
		assertEquals(1, model.getDates().size());
		assertEquals(1, model.getTemps().size());
		assertEquals(1, model.getHighs().size());
		assertEquals(1, model.getLows().size());
		assertEquals(1, model.getHumidity().size());
		assertEquals(1, model.getWindTemps().size());
		assertEquals(1, model.getRainPCTS().size());
		assertEquals("test", model.getZipCodes().get(0));
		assertEquals("test", model.getDates().get(0));
		assertEquals("test", model.getTemps().get(0));
		assertEquals("test", model.getHighs().get(0));
		assertEquals("test", model.getLows().get(0));
		assertEquals("test", model.getHumidity().get(0));
		assertEquals("test", model.getWindTemps().get(0));
		assertEquals("test", model.getRainPCTS().get(0));
	}
	
	@Test
	public void testRemoveAll() {
		model.add("test", "test", "test", "test", "test", "test", "test", "test");
		assertEquals(1, model.getZipCodes().size());
		assertEquals(1, model.getDates().size());
		assertEquals(1, model.getTemps().size());
		assertEquals(1, model.getHighs().size());
		assertEquals(1, model.getLows().size());
		assertEquals(1, model.getHumidity().size());
		assertEquals(1, model.getWindTemps().size());
		assertEquals(1, model.getRainPCTS().size());
		model.removeAll();
		assertEquals(0, model.getZipCodes().size());
		assertEquals(0, model.getDates().size());
		assertEquals(0, model.getTemps().size());
		assertEquals(0, model.getHighs().size());
		assertEquals(0, model.getLows().size());
		assertEquals(0, model.getHumidity().size());
		assertEquals(0, model.getWindTemps().size());
		assertEquals(0, model.getRainPCTS().size());
	}

}
