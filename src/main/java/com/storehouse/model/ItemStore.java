package com.storehouse.model;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * This store is used to store {@link Item}s
 */
public interface ItemStore {
    /**
     * Add a new {@link Item}
     *
     * @param item the {@link Item}
     * @return the index
     */
    public int addItem(Item item);

    /**
     * Gets all elements of this {@link ItemStore}
     *
     * @return a collection of {@link Item}s
     */
    public Collection<Item> getItems();

    /**
     * Get an {@link Item} by its name
     *
     * @param name name
     * @return the {@link Item}
     */
    public Item getItem(String name);

    /**
     * Get an {@link Item} by its ID
     *
     * @param id id
     * @return the {@link Item}
     */
    public Item getItem(int id);

    /**
     * Removes an {@link Item}
     *
     * @param item the {@link Item}
     */
    public void removeItem(Item item);

    /**
     * Updates an  {@link Item}
     *
     * @param item the {@link Item}
     */
    public void updateItem(Item item);

    /**
     * Saves this store to a {@link File}
     *
     * @param file the file
     * @throws IOException
     */
    public void save(File file) throws IOException;

    /**
     * Loads every item from a {@link File}
     *
     * @param file the file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void load(File file) throws IOException, ClassNotFoundException;

    /**
     * Gets how many items are in this store
     *
     * @return the count of the items
     */
    public int getCount();

    /**
     * Purges every element in it
     */
    public void purge();
}
