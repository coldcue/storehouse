package com.storehouse.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 11/1/13
 * Time: 6:14 PM
 */
public class ArrayItemStore implements ItemStore {
    private ArrayList<Item> items = new ArrayList<Item>();

    @Override
    public int addItem(Item item) {
        if (items.contains(item)) items.remove(item);
        items.add(item);
        int id = items.indexOf(item);
        item.setId(id);
        return id;
    }

    @Override
    public Collection<Item> getItems() {
        return items;
    }

    @Override
    public Item getItem(int id) {
        return items.get(id);
    }

    @Override
    public void removeItem(Item item) {
        items.remove(item);
    }

    @Override
    public void updateItem(Item item) {
        items.set(item.getId(), item);
    }

    @Override
    public void save(File file) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(items);
        objectOutputStream.close();
    }

    @Override
    public void load(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        //noinspection unchecked
        items = (ArrayList<Item>) objectInputStream.readObject();
        objectInputStream.close();
    }
}
