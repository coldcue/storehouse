package com.storehouse.model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 12/3/13
 * Time: 4:30 PM
 */
public class FileManagerTest {
    private ItemStore itemStore;
    private FileManager fileManager;

    @Before
    public void setUp() throws Exception {
        itemStore = new ArrayItemStore();
        fileManager = new FileManager(itemStore);

        itemStore.addItem(new Item("test1", "test1f", 10));
        itemStore.addItem(new Item("test2", "test2f", 20));
    }

    @Test
    public void test() throws Exception {
        assertFalse(fileManager.isOpened());
        fileManager.save(new File("test.db"));
        assertTrue(fileManager.isOpened());

        itemStore.purge();

        fileManager.open(new File("test.db"));
        fileManager.close();
        assertFalse(fileManager.isOpened());

        assertTrue(itemStore.getCount() == 2);
    }

}
