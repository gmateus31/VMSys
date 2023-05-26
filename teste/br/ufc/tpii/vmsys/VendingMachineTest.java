package br.ufc.tpii.vmsys;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import br.ufc.tpii.vmsys.exceptions.InsufficientFunds;
import br.ufc.tpii.vmsys.exceptions.InvalidSelection;
import br.ufc.tpii.vmsys.exceptions.OutOfStock;
import br.ufc.tpii.vmsys.inventory.*;
import br.ufc.tpii.vmsys.inventory.exceptions.ItemAlreadyAdded;
import br.ufc.tpii.vmsys.inventory.exceptions.ItemNotFound;

public class VendingMachineTest {
	@Test
	public void testAddCoins() {
		Inventory inventory = new HashMapInventory();
		VendingMachine vm = new VendingMachine(inventory);
		vm.addCoins(5.0);
		assertEquals(5.0, vm.howManyCoinsLeft());
	}
	
	@Test
	public void testWithdrawRemainingCoins() {
		Inventory inventory = new HashMapInventory();
		VendingMachine vm = new VendingMachine(inventory);
		vm.addCoins(5.0);
		assertEquals(5.0, vm.withdrawRemainingCoins());
		assertEquals(0.0, vm.howManyCoinsLeft());
	}
	
	
	@Test
	public void testVend() {
		Inventory inventory = new HashMapInventory();
		try {
			inventory.addItem(new Item("Fini", 1.0, 10));
		} catch (ItemAlreadyAdded e) {
			fail("Error in adding item! " + e.getClass());
		}
		
		VendingMachine vm = new VendingMachine(inventory);
		vm.addCoins(5.0);
		
		try {
			vm.vend("Fini");
		} catch (InvalidSelection | OutOfStock | InsufficientFunds e) {
			fail("Error in perform vend! " + e.getClass());
		}
		
		assertEquals(4.0, vm.howManyCoinsLeft());
	
		try {
			assertEquals(9, inventory.getItem("Fini").getCount());
		} catch (ItemNotFound e) {
			fail("Error in get item! " + e.getClass());
		}
	}
	
	@Test
	public void testVendInvalidSelection() {
		Inventory inventory = new HashMapInventory();
		try {
			inventory.addItem(new Item("Fini", 1.0, 10));
		} catch (ItemAlreadyAdded e) {
			fail("Error in adding item! " + e.getClass());
		}
		
		VendingMachine vm = new VendingMachine(inventory);
		vm.addCoins(5.0);
		
		Exception exception = assertThrows(InvalidSelection.class, () -> {
			vm.vend("Not a item");
		});
		
		
		assertEquals("Invalid item selection: Not a item", exception.getMessage());
		assertEquals(5.0, vm.howManyCoinsLeft());
	}
	
	@Test
	public void testVendOutOfStock() {
		Inventory inventory = new HashMapInventory();
		try {
			inventory.addItem(new Item("Fini", 1.0, 0));
		} catch (ItemAlreadyAdded e) {
			fail("Error in adding item! " + e.getClass());
		}
		
		VendingMachine vm = new VendingMachine(inventory);
		vm.addCoins(5.0);
		
		Exception exception = assertThrows(OutOfStock.class, () -> {
			vm.vend("Fini");
		});
		
		assertEquals("Item out of stock: Fini", exception.getMessage());
		assertEquals(5.0, vm.howManyCoinsLeft());
	}
	
	@Test
	public void testVendInsufficientFunds() {
		Inventory inventory = new HashMapInventory();
		try {
			inventory.addItem(new Item("Fini", 1.0, 10));
			inventory.addItem(new Item("Pipe", 1.0, 10));
		} catch (ItemAlreadyAdded e) {
			fail("Error in adding item! " + e.getClass());
		}
		
		VendingMachine vm = new VendingMachine(inventory);
		vm.addCoins(1.0);
		
		try {
			vm.vend("Fini");
		} catch (InvalidSelection | OutOfStock | InsufficientFunds e) {
			fail("Error in perform vend! " + e.getClass());
		}
		
		Exception exception = assertThrows(InsufficientFunds.class, () -> {
			vm.vend("Pipe");
		});
		
		assertEquals("Insufficient coins to by Pipe: 0.0", exception.getMessage());
		assertEquals(0.0, vm.howManyCoinsLeft());
	}
	
	//testes para o construtor sem parametro das exceções
	@Test
	public void testInsufficientFunds() {
		assertThrows(InsufficientFunds.class, () -> {
			throw new InsufficientFunds();
		});
	}
	
	@Test
	public void testOutOfStock() {
		assertThrows(OutOfStock.class, () -> {
			throw new OutOfStock();
		});
	}
	
	@Test
	public void testInvalidSelection() {
		assertThrows(InvalidSelection.class, () -> {
			throw new InvalidSelection();
		});
	}
}
