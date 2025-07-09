package org.example;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;

public class MainTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @Before
    public void setUp() {
        Main.storeInventory.clear();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testAddProduct_NewProductValidQuantity() {
        Main.addProduct("Banana", 30);
        assertTrue(Main.storeInventory.containsKey("Banana"));
        assertEquals(30, (int) Main.storeInventory.get("Banana"));
        assertTrue( outContent.toString().contains("Product Banana x30 has been successfully added."));
    }

    @Test
    public void testAddProduct_QuantityZero() {
        Main.addProduct("Mango", 0);
        assertTrue( Main.storeInventory.containsKey("Mango"));
        assertEquals(0, (int) Main.storeInventory.get("Mango"));
        assertTrue(outContent.toString().contains("Product Mango x0 has been successfully added."));
    }

    @Test
    public void testAddProduct_ExistingProductOverwrites() {
        Main.addProduct("Milk", 20);
        outContent.reset();

        Main.addProduct("Milk", 50);
        assertTrue(Main.storeInventory.containsKey("Milk"));
        assertEquals(50, (int) Main.storeInventory.get("Milk"));
        assertTrue(outContent.toString().contains("Product Milk x50 has been successfully added."));
    }

    @Test
    public void testCheckProduct_Existing() {
        Main.addProduct("Milk", 20);
        outContent.reset();

        Main.checkProduct("Milk");
        assertTrue(outContent.toString().contains("Milk is in stock: 20"));
    }

    @Test
    public void testCheckProduct_NonExistent() {
        Main.checkProduct("Ice Cream");
        assertTrue(outContent.toString().contains("Product not found."));
        assertFalse( Main.storeInventory.containsKey("Ice Cream"));
    }

    @Test
    public void testUpdateProduct_ExistingValidQuantity() {
        Main.addProduct("Bread", 10);
        outContent.reset();

        Main.updateProduct("Bread", 25);
        assertTrue(Main.storeInventory.containsKey("Bread"));
        assertEquals(25, (int) Main.storeInventory.get("Bread"));
        assertTrue(outContent.toString().contains("Product Bread has been successfully updated to 25."));
    }

    @Test
    public void testUpdateProduct_NonExisting() {
        Main.updateProduct("Tofu", 5);
        assertFalse(Main.storeInventory.containsKey("Tofu"));
        assertTrue(outContent.toString().contains("There is not such product that exists."));
    }

    @Test
    public void testRemoveProduct_Existing() {
        Main.addProduct("Eggs", 12);
        outContent.reset();

        Main.removeProduct("Eggs");
        assertFalse(Main.storeInventory.containsKey("Eggs"));
        assertTrue(outContent.toString().contains("Product Eggs has been successfully removed."));
    }

    @Test
    public void testRemoveProduct_NonExisting() {
        Main.removeProduct("Pizza");
        assertFalse(Main.storeInventory.containsKey("Pizza"));
        assertTrue(outContent.toString().contains("There is not such product that exists."));
    }

    @Test
    public void testViewInventory_Empty() {
        Main.viewInventory();
        assertEquals("Inventory is empty.", outContent.toString().trim());
    }

    @Test
    public void testViewInventory_WithItems() {
        Main.addProduct("Cereal", 1);
        outContent.reset();
        Main.addProduct("Eggs", 12);
        outContent.reset();
        Main.addProduct("Oranges", 3);
        outContent.reset();
        Main.addProduct("Milk", 2);
        outContent.reset();
        Main.addProduct("Protein powder", 141);
        outContent.reset();

        Main.viewInventory();

        String output = outContent.toString();
        assertTrue(output.contains("Oranges x3"));
        assertTrue(output.contains("Milk x2"));
        assertTrue(output.contains("Protein powder x141"));
        assertTrue(output.contains("Cereal x1"));
        assertTrue(output.contains("Eggs x12"));
    }
}