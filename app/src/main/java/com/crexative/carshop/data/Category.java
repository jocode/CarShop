package com.crexative.carshop.data;

import com.orm.SugarRecord;

public class Category extends SugarRecord {

    String category;
    boolean isDefault;

    public Category() {
    }

    public Category(String category, boolean isDefault) {
        this.category = category;
        this.isDefault = isDefault;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
