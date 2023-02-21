package com.project.pos_mms;

import com.opencsv.exceptions.CsvException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class invpopupController{

    int i = 0;
    private ObservableList<InvTable> invTableObservableList;

    @FXML
    private TextField invQty;

    @FXML
    private TextField expendature;

    @FXML
    private TextField invdate;

    @FXML
    private TextField invItem;


    @FXML
    void getandAddInv(ActionEvent event) throws IOException, CsvException {
        String inv_item = invItem.getText().trim();
        LocalDate ld = LocalDate.now();
        String date = String.valueOf(ld);
        //String date = invdate.getText();
        int qty = Integer.parseInt(invQty.getText().trim());
        int expense = Integer.parseInt(expendature.getText().trim());
        String[] data = new String[]{inv_item, Integer.toString(qty), Integer.toString(expense),date};
        DataAppoint obj = new DataAppoint();
        obj.add_data(data, "invData.csv");
        mainController hl = new mainController();
        hl.reload();
        //String[][] starr = obj.getinventory("pos_mms/invData.csv");
        InvTable x = new InvTable(inv_item, qty, expense,date);
        invTableObservableList.add(x);

        closeStage(event);
    }

    public void setinventTableObservableList(ObservableList<InvTable> tvObservableList) {
        this.invTableObservableList = tvObservableList;

    }
    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
