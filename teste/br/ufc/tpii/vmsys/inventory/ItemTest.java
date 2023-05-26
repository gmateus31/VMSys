package br.ufc.tpii.vmsys.inventory;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ItemTest {
	@Test
	public void testDecCount() {
		Item item = new Item("Pipe", 1.0, 7);
		item.decCount();
		assertEquals(6, item.getCount());
	}
	
	@Test
	public void testIncCount() {
		Item item = new Item("Pipe", 1.0, 7);
		item.incCount();
		assertEquals(8, item.getCount());
	}
	
	@Test
	public void testSetPrice() {
		Item item = new Item("Pipe", 1.0, 7);
		item.setPrice(10);
		assertEquals(10, item.getPrice());
	}
}
