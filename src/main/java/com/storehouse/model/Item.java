package com.storehouse.model;

import java.io.Serializable;

public class Item implements Serializable {
    protected String name;
    protected String fullName;
    protected long quantity;
    protected int id;


    public enum Field {
        ID("ID", "getId", "setId", int.class), NAME("Name", "getName", "setName", String.class), FULLNAME("Full name", "getFullName", "setFullName", String.class), QUANTITY("Quantity", "getQuantity", "setQuantity", long.class);

        private String name;
        private String getMethodName;
        private String setMethodName;
        private Class clazz;

        private Field(String name, String getMethodName, String setMethodName, Class clazz) {
            this.name = name;
            this.getMethodName = getMethodName;
            this.setMethodName = setMethodName;
            this.clazz = clazz;
        }

        public String getName() {
            return name;
        }

        public String getGetMethodName() {
            return getMethodName;
        }

        public String getSetMethodName() {
            return setMethodName;
        }

        public Class getClazz() {
            return clazz;
        }
    }


    public Item(String name, String fullName, long quantity) {
        this.name = name;
        this.fullName = fullName;
        this.quantity = quantity;
    }

    public Item() {
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!name.equals(item.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
