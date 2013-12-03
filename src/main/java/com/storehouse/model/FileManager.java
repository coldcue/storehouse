package com.storehouse.model;

import java.io.File;
import java.io.IOException;

/**
 * This manages the saving and opening tasks of an itemstore
 */
public class FileManager {

    private File file;
    private ItemStore itemStore;

    public FileManager(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    /**
     * Save the store to the previous file
     *
     * @throws IOException
     */
    public void save() throws IOException {
        if (!isOpened()) throw new IOException();
        itemStore.save(file);
    }

    /**
     * Save the store to the specified file
     *
     * @param file the file
     * @throws IOException
     */
    public void save(File file) throws IOException {
        itemStore.save(file);
        this.file = file;
    }

    /**
     * Open the specified file and load into the store
     *
     * @param file the file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void open(File file) throws IOException, ClassNotFoundException {
        itemStore.load(file);
        this.file = file;
    }

    /**
     * Checks if the file is opened or not
     *
     * @return the result
     */
    public boolean isOpened() {
        return file != null;
    }

    /**
     * Closes the previous file
     */
    public void close() {
        file = null;
    }

}
