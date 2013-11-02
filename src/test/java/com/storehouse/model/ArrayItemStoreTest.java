package com.storehouse.model;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 11/1/13
 * Time: 8:57 PM
 */
public class ArrayItemStoreTest {

    @Test
    public void testAddItem() throws Exception {
        ArrayItemStore itemStore = new ArrayItemStore();

        Item borsodi = new Item("borsodi", "Borsodi beer", 10);
        int id = itemStore.addItem(borsodi);
        assertTrue(itemStore.getItems().contains(borsodi));
        assertEquals(borsodi, itemStore.getItem(id));
    }

    @Test
    public void testGetItem() throws Exception {
        ArrayItemStore itemStore = new ArrayItemStore();

        Item borsodi = new Item("borsodi", "Borsodi beer", 10);
        itemStore.addItem(borsodi);
        assertEquals(borsodi, itemStore.getItem("borsodi"));
    }

    @Test
    public void testRemoveItem() throws Exception {
        ArrayItemStore itemStore = new ArrayItemStore();

        Item borsodi = new Item("borsodi", "Borsodi beer", 10);
        itemStore.addItem(borsodi);
        assertTrue(itemStore.getItems().contains(borsodi));
        itemStore.removeItem(borsodi);
        assertFalse(itemStore.getItems().contains(borsodi));
    }

    @Test
    public void testUpdateItem() throws Exception {
        ArrayItemStore itemStore = new ArrayItemStore();

        Item borsodi = new Item("borsodi", "Borsodi beer", 10);
        itemStore.addItem(borsodi);
        borsodi.setFullName("Borsodi sor");
        itemStore.updateItem(borsodi);
        assertEquals(itemStore.getItem("borsodi").getFullName(), "Borsodi sor");
    }

    @Test
    public void testSaveLoad() throws Exception {
        ArrayItemStore itemStore = new ArrayItemStore();

        itemStore.addItem(new Item("borsodi", "Borsodi beer", 10));
        itemStore.addItem(new Item("heineken", "Heineken beer", 5));

        File file = new File("test.db");
        itemStore.save(file);

        itemStore = new ArrayItemStore();
        assertEquals(itemStore.getItems().size(), 0);
        itemStore.load(file);
        assertEquals(itemStore.getItems().size(), 2);
    }
}
