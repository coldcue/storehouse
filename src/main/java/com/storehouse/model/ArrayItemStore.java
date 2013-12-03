package com.storehouse.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is an {@link ItemStore} implementation
 */
public class ArrayItemStore implements ItemStore {
    private ArrayList<Item> items = new ArrayList<Item>();

    /**
     * @see com.storehouse.model.ItemStore#addItem(Item)
     */
    @Override
    public int addItem(Item item) {
        if (items.contains(item)) items.remove(item);
        items.add(item);
        int id = items.indexOf(item);
        item.setId(id);
        return id;
    }

    /**
     * @see com.storehouse.model.ItemStore#getItems()
     */
    @Override
    public Collection<Item> getItems() {
        return items;
    }

    /**
     * @see com.storehouse.model.ItemStore#getItem(String)
     */
    @Override
    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) return item;
        }
        return null;
    }

    /**
     * @see com.storehouse.model.ItemStore#getItem(int)
     */
    public Item getItem(int id) {
        return items.get(id);
    }

    /**
     * @see com.storehouse.model.ItemStore#removeItem(Item)
     */
    @Override
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * @see com.storehouse.model.ItemStore#updateItem(Item)
     */
    @Override
    public void updateItem(Item item) {
        items.set(item.getId(), item);
    }

    /**
     * @see com.storehouse.model.ItemStore#save(java.io.File)
     */
    @Override
    public void save(File file) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(items);
        objectOutputStream.close();
    }

    /**
     * @see com.storehouse.model.ItemStore#load(java.io.File)
     */
    @Override
    public void load(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        //noinspection unchecked
        items = (ArrayList<Item>) objectInputStream.readObject();
        objectInputStream.close();
    }

    /**
     * @see ItemStore#getCount()
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * @see ItemStore#purge()
     */
    @Override
    public void purge() {
        items = new ArrayList<>();
    }
}
