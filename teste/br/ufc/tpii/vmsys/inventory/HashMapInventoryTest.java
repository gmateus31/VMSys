package br.ufc.tpii.vmsys.inventory;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import br.ufc.tpii.vmsys.inventory.exceptions.ItemAlreadyAdded;
import br.ufc.tpii.vmsys.inventory.exceptions.ItemNotFound;

public class HashMapInventoryTest {
	@Test
	public void testAddItem() {
		Item item = new Item("Pipe", 1.0, 7);
		Inventory inventory = new HashMapInventory();
		
		try {
			inventory.addItem(item);
		} catch (ItemAlreadyAdded e) {
			fail("Error in adding item! " + e.getClass());
		}
		
		try {
			assertEquals(item, inventory.getItem("Pipe"));
		} catch (ItemNotFound e) {
			fail("Error in get item! " + e.getClass());
		}
		assertEquals(1, inventory.numberOfItems());
	}
	
	@Test
	public void testAddItemItemAlreadyAdded() {
		Item item = new Item("Pipe", 1.0, 7);
		Inventory inventory = new HashMapInventory();
		
		try {
			inventory.addItem(item);
		} catch (ItemAlreadyAdded e) {
			fail("Error in adding item! " + e.getClass());
		}
		
		Exception exception = assertThrows(ItemAlreadyAdded.class, () -> {
			inventory.addItem(new Item("Pipe", 1.0, 7));
		});
		
		assertEquals("Item already added: Pipe", exception.getMessage());
		assertEquals(1, inventory.numberOfItems());
	}
	
	@Test
	public void testRemoveItem() {
		Item item = new Item("Pipe", 1.0, 7);
		Inventory inventory = new HashMapInventory();
		
		try {
			inventory.addItem(item);
		} catch (ItemAlreadyAdded e) {
			fail("Error in adding item! " + e.getClass());
		}
		
		try {
			inventory.removeItem("Pipe");
		} catch (ItemNotFound e) {
			fail("Error in remove item! " + e.getClass());
		}
		
		assertEquals(0, inventory.numberOfItems());
	}
	
	@Test
	public void testRemoveItemItemNotFound() {
		Item item = new Item("Pipe", 1.0, 7);
		Inventory inventory = new HashMapInventory();
		
		try {
			inventory.addItem(item);
		} catch (ItemAlreadyAdded e) {
			fail("Error in adding item! " + e.getClass());
		}
		
		Exception exception = assertThrows(ItemNotFound.class, () -> {
			inventory.removeItem("Fini");
		});
		
		assertEquals("Item not found: Fini", exception.getMessage());
		assertEquals(1, inventory.numberOfItems());
	}
	
	@Test
	public void testGetItem() {
		Item item = new Item("Pipe", 1.0, 7);
		Inventory inventory = new HashMapInventory();
		
		try {
			inventory.addItem(item);
		} catch (ItemAlreadyAdded e) {
			fail("Error in adding item! " + e.getClass());
		}
		
		try {
			assertEquals(item, inventory.getItem("Pipe"));
		} catch (ItemNotFound e) {
			fail("Error in get item! " + e.getClass());;
		}
	}
	
	@Test
	public void testGetItemItemNotFound() {
		Item item = new Item("Pipe", 1.0, 7);
		Inventory inventory = new HashMapInventory();
		
		try {
			inventory.addItem(item);
		} catch (ItemAlreadyAdded e) {
			fail("Error in adding item! " + e.getClass());
		}
		
		Exception exception = assertThrows(ItemNotFound.class, () -> {
			inventory.getItem("Fini");
		});
		
		
		assertEquals("Item not found: Fini", exception.getMessage());
		assertEquals(1, inventory.numberOfItems());
		
	}
	
	//testes para o construtor sem parametro das exceções
	@Test
	public void testItemAlreadyAdded() {
		assertThrows(ItemAlreadyAdded.class, () -> {
			throw new ItemAlreadyAdded();
		});
	}
	
	@Test
	public void testItemNotFound() {
		assertThrows(ItemNotFound.class, () -> {
			throw new ItemNotFound();
		});
	}
}
