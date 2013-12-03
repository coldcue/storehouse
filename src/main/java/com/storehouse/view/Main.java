package com.storehouse.view;

import com.storehouse.model.ArrayItemStore;
import com.storehouse.model.FileManager;
import com.storehouse.model.ItemStore;

import java.io.IOException;

public class Main {

    public static MainFrame mainFrame;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        /**
         * Empty itemstore
         */
        ItemStore itemStore = new ArrayItemStore();
        FileManager fileManager = new FileManager(itemStore);
        //itemStore.load(new File("test.db"));

        mainFrame = new MainFrame(itemStore, fileManager);
        mainFrame.init();
    }
}
