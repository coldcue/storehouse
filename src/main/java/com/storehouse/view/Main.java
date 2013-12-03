package com.storehouse.view;

import com.storehouse.model.ArrayItemStore;
import com.storehouse.model.FileManager;
import com.storehouse.model.ItemStore;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 11/30/13
 * Time: 12:28 AM
 */
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
