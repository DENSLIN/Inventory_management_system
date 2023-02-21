package com.project.pos_mms;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class orderTable {
    SimpleStringProperty Item;
    SimpleIntegerProperty Quantity;
    SimpleIntegerProperty Price;

    public orderTable(){
    }

    public orderTable(String Item, int Quantity, int Price) {
        this.Item = new SimpleStringProperty(Item);
        this.Quantity = new SimpleIntegerProperty(Quantity);
        this.Price = new SimpleIntegerProperty(Price);
    }



    public String getItem() {
        return Item.get();
    }

    public Integer getQuantity() {
        return Quantity.get();
    }

    public Integer getPrice() {
        return Price.get();
    }

    public void setItem(String item) {
        this.Item.set(item);
    }

    public void setQuantity(int quantity) {
        this.Quantity.set(quantity);
    }

    public void setPrice(int price) {
        this.Price.set(price);
    }

    @Override
    public String toString() {
        return "item: " + Item.get() + " - " + "quantity: " + Quantity.get()+ "Amount: "+ Price.get();
    }
}
