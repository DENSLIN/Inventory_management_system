package com.project.pos_mms;

import com.opencsv.exceptions.CsvException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class NopopupController implements Initializable {

    private ObservableList<orderTable> orderTableObservableList;

    @FXML
    private TextField orderqty;

    @FXML
    private TextField orderprc;

    @FXML
    private ChoiceBox<String> orderchoice;

    @FXML
    private Text availinv;

    private ObservableList<String> sample = FXCollections.observableArrayList();

    private String setit = "";

    @FXML
    private void getit(){
        try {
            avail();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        availinv.setText(setit);
    }

    @FXML
    private void getandAdd(ActionEvent event) throws IOException, CsvException {
        String no_item = orderchoice.getValue();
        int qty = Integer.parseInt(orderqty.getText().trim());
        int prc = Integer.parseInt(orderprc.getText().trim());
        String[] data = new String[]{no_item, Integer.toString(qty), Integer.toString(prc)};
        DataAppoint obj = new DataAppoint();
        obj.add_data(data, "orderData.csv");
        orderTable x = new orderTable(no_item, qty, prc);

        String[][] starr = obj.getinventory("invData.csv");
        for(int i=0;i<starr.length;i++){
            if(Objects.equals(starr[i][0], no_item)){
                int qt = Integer.parseInt(starr[i][1]);
                if (qty<=qt) {/*
                    LocalDate ld = LocalDate.now();
                    String date = String.valueOf(ld);
                    String[] data1 = new String[]{starr[i][0], Integer.toString(qt - qty), starr[i][2], date};
                    obj.remove_line("pos_mms/InvData.csv", i);
                    HelloController Hl = new HelloController();
                    obj.add_data(data1, "pos_mms/InvData.csv");*/
                    orderTableObservableList.add(x);
                    JOptionPane.showMessageDialog(null," Item Add ");
                }
                else{
                    JOptionPane.showMessageDialog(null,"Insufficient Availability","Error Message Box",JOptionPane.ERROR_MESSAGE);

                }
                break;
            }
        }


        closeStage(event);

    }


    public void prompter() throws IOException, CsvException {
        DataAppoint obj = new DataAppoint();
        String[][] starr = obj.getinventory("invData.csv");
        for(int i=0;i<=starr.length-1;i++){
            String s = starr[i][0];
            sample.add(s);
        }

    }


    public void avail() throws IOException, CsvException {
        String no_item = orderchoice.getValue();
        System.out.println(no_item);
        DataAppoint obj = new DataAppoint();
        String[][] starr = obj.getinventory("invData.csv");
        for(int i=0;i<=starr.length-1;i++){
            if(Objects.equals(starr[i][0], no_item)){
                setit = starr[i][1];
            }
        }
    }

    public void setorderTableObservableList(ObservableList<orderTable> tvObservableList) {
        this.orderTableObservableList = tvObservableList;

    }
    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources){

        try {
            prompter();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        orderchoice.setItems(sample);

    }

}
