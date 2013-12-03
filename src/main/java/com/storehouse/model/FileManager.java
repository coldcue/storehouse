package com.storehouse.model;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 12/3/13
 * Time: 11:51 AM
 */
public class FileManager {

    private File file;
    private ItemStore itemStore;

    public FileManager(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public void save() throws IOException {
        if (!isOpened()) throw new IOException();
        itemStore.save(file);
    }

    public void save(File file) throws IOException {
        itemStore.save(file);
        this.file = file;
    }

    public void open(File file) throws IOException, ClassNotFoundException {
        itemStore.load(file);
        this.file = file;
    }

    public boolean isOpened() {
        return file != null;
    }

    public void close() {
        file = null;
    }

}
