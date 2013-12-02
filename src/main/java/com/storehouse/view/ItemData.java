package com.storehouse.view;

import com.storehouse.model.Item;
import com.storehouse.model.ItemStore;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 11/30/13
 * Time: 12:36 AM
 */
public class ItemData extends AbstractTableModel {
    private ItemStore itemStore;

    public ItemData(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    @Override
    public int getRowCount() {
        return itemStore.getCount();
    }

    @Override
    public int getColumnCount() {
        return Item.Fields.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //Get the item by count
        Item item = itemStore.getItem(rowIndex);
        try {
            return getMethodAtColumn(columnIndex).invoke(item);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Item.Fields getFieldAtColumn(int column) throws NoSuchFieldError {
        //Search for the field
        for (Item.Fields field : Item.Fields.values()) {
            if (field.ordinal() == column) return field;
        }
        throw new NoSuchFieldError();
    }

    private Method getMethodAtColumn(int column) throws NoSuchMethodException {
        //Search for the field
        return Item.class.getMethod(getFieldAtColumn(column).getMethodName());
    }

    @Override
    public String getColumnName(int column) {
        return getFieldAtColumn(column).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        try {
            return getMethodAtColumn(columnIndex).getReturnType();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (getFieldAtColumn(columnIndex) != Item.Fields.ID);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);    //To change body of overridden methods use File | Settings | File Templates.
    }


}
