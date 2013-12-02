package com.storehouse.model;

import java.io.Serializable;

public class Item implements Serializable {
    protected String name;
    protected String fullName;
    protected long quantity;
    protected int id;

    public enum Fields {
        ID("ID", "getId"), NAME("Name", "getName"), FULLNAME("Full name", "getFullName"), QUANTITY("Qtty", "getQuantity");

        private String name;
        private String methodName;

        private Fields(String name, String methodName) {
            this.name = name;
            this.methodName = methodName;
        }

        public String getName() {
            return name;
        }

        public String getMethodName() {
            return methodName;
        }

    }


    public Item(String name, String fullName, long quantity) {
        this.name = name;
        this.fullName = fullName;
        this.quantity = quantity;
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
