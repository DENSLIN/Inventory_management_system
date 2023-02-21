package com.project.pos_mms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Addemployeepop {


    @FXML
    private TextField givenpass;

    @FXML
    private TextField privilegegiven;

    @FXML
    private TextField employeename;

    @FXML
    private void addemployee(ActionEvent event) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String empname = employeename.getText().trim();
        String password = givenpass.getText().trim();
        String privilege = privilegegiven.getText().trim();
        String dataToAdd = String.format("%s, %s, %s\n", empname,password,privilege);
        login obj = new login();
        Key k = obj.getkey();
        obj.addData(k,dataToAdd);
        closeStage(event);

    }
    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
