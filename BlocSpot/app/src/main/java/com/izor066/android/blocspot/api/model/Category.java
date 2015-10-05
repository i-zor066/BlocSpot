package com.izor066.android.blocspot.api.model;

/**
 * Created by igor on 4/10/15.
 */
public class Category {

    private final String categoryName;
    private final int color;

    public Category (String categoryName, int color) {
        this.categoryName = categoryName;
        this.color = color;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getColor() {
        return color;
    }
}
