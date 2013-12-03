package com.storehouse.view;

import com.storehouse.model.Item;
import com.storehouse.model.ItemStore;

import javax.swing.table.AbstractTableModel;
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
        return Item.Field.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //Get the item by count
        Item item = itemStore.getItem(rowIndex);
        try {
            return getGetMethodAtColumn(columnIndex).invoke(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Item.Field getFieldAtColumn(int column) throws Exception {
        //Search for the field
        for (Item.Field field : Item.Field.values()) {
            if (field.ordinal() == column) return field;
        }
        throw new Exception();
    }

    private Method getGetMethodAtColumn(int column) throws Exception {
        //Search for the field
        return Item.class.getMethod(getFieldAtColumn(column).getGetMethodName());
    }

    @Override
    public String getColumnName(int column) {
        try {
            return getFieldAtColumn(column).getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "UNKNOWN";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        try {
            return getFieldAtColumn(columnIndex).getClazz();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        try {
            Item.Field fieldAtColumn = getFieldAtColumn(columnIndex);
            return (fieldAtColumn != Item.Field.ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        try {
            Item item = itemStore.getItem(rowIndex);
            Item.Field fieldAtColumn = getFieldAtColumn(columnIndex);
            Method method = Item.class.getMethod(fieldAtColumn.getSetMethodName(), fieldAtColumn.getClazz());
            method.invoke(item, aValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
