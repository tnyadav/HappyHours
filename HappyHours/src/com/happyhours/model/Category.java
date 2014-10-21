package com.happyhours.model;

import com.google.gson.annotations.Expose;

public class Category {

@Expose
private String categoryID;
@Expose
private String categoryName;

public String getCategoryID() {
return categoryID;
}

public void setCategoryID(String categoryID) {
this.categoryID = categoryID;
}

public String getCategoryName() {
return categoryName;
}

public void setCategoryName(String categoryName) {
this.categoryName = categoryName;
}

@Override
public String toString() {
	return categoryName; 
}

}
