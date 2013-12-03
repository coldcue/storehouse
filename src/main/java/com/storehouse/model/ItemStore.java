package com.storehouse.model;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 11/1/13
 * Time: 5:09 PM
 */
public interface ItemStore {
    public int addItem(Item item);

    public Collection<Item> getItems();

    public Item getItem(String name);

    public Item getItem(int count);

    public void removeItem(Item item);

    public void updateItem(Item item);

    public void save(File file) throws IOException;

    public void load(File file) throws IOException, ClassNotFoundException;

    public int getCount();

    public void purge();
}
