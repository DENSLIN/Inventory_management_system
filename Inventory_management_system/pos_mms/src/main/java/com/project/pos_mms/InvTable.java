package com.project.pos_mms;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;
public class InvTable {
    SimpleStringProperty ItemName;
    SimpleIntegerProperty Quantity;
    SimpleIntegerProperty Expenditure;
    SimpleStringProperty Modified;

    public InvTable(String ItemName, int Quantity, int Expenditure, String Modified) {
        this.ItemName = new SimpleStringProperty(ItemName);
        this.Quantity = new SimpleIntegerProperty(Quantity);
        this.Expenditure = new SimpleIntegerProperty(Expenditure);
        this.Modified = new SimpleStringProperty(Modified);

    }

    public String getItemName() {
        return ItemName.get();
    }

    public Integer getQuantity() {
        return Quantity.get();
    }

    public Integer getExpenditure() {
        return Expenditure.get();
    }

    public String getModified() {
        return Modified.get();
    }

    public void setItemName(String itemName) {
        this.ItemName.set(itemName);
    }

    public void setQuantity(int quantity) {
        this.Quantity.set(quantity);
    }

    public void setModified(String modified) {
        this.Modified.set(modified);
    }

    public void setExpenditure(int expenditure) {
        this.Expenditure.set(expenditure);
    }
}
