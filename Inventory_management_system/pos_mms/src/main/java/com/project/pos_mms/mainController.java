package com.project.pos_mms;

import com.opencsv.exceptions.CsvException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


public class mainController implements Initializable{
    @FXML
    private AnchorPane LogIn;
    @FXML
    private TextField id;

    @FXML
    private TextField privilege;

    @FXML
    private PasswordField pass;

    @FXML
    private Label warning;

    @FXML
    private Button LogInButton;

    @FXML
    private AnchorPane Layout;

    @FXML
    private AnchorPane dbp;

    @FXML
    private AnchorPane in;

    @FXML
    private Button reloadinv;

    @FXML
    private Button inv_add;
    @FXML
    private TableView<orderTable> newOrderTable;

    @FXML
    private TableColumn<orderTable, String> Item;

    @FXML
    private TableColumn<orderTable, Integer> orQunatity;

    @FXML
    private TableColumn<orderTable, Integer> oprice;

    @FXML
    private AnchorPane setin;

    @FXML
    private Button addemployee;

    @FXML
    private Button removeemployee;


    @FXML
    private AnchorPane no;

    @FXML
    private TableView<InvTable> inventoryTable;

    @FXML
    private TableColumn<InvTable, String> itemName;

    @FXML
    private TableColumn<InvTable, Integer> quantity;

    @FXML
    private TableColumn<InvTable, String> availability;

    @FXML
    private TableColumn<InvTable, String> mod;

    @FXML
    private Button dashboard;

    @FXML
    private Button neworders;

    @FXML
    private Button inventory;

    @FXML
    private Button settings;

    @FXML
    private Button min;

    @FXML
    private Button rsz;

    @FXML
    private Button x;

    @FXML
    private Label nme;

    @FXML
    private Label pri;

    @FXML
    private Button logout;

    @FXML
    private TextField so;

    @FXML
    private TextField ts;

    @FXML
    private TextField tp;

    @FXML
    private TextField ic;

    @FXML
    private TextField as;

    @FXML
    private TextField opd;


    @FXML
    private LineChart<String,Integer> salesgraph;



//    final NumberAxis xAxis = new NumberAxis();
//    final NumberAxis yAxis = new NumberAxis();


    private ObservableList<orderTable> otvObservableList = FXCollections.observableArrayList();
    private ObservableList<InvTable> itvObservableList = FXCollections.observableArrayList();
    public String privilegeStat = "";
    public String user = "";
    String[][] orderdata;
    String setPrivilege = "";

    @FXML
    private void handleNavActions(ActionEvent event) throws IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        if (event.getSource() == dashboard){
            dbp.toFront();
        }

        else if (event.getSource() == neworders){
            no.toFront();
        }
        else if (event.getSource() == inventory){
            in.toFront();
        }
        else if (event.getSource() == settings){
            if (Objects.equals(privilegeStat, "admin") && Objects.equals(privilegeStat, setPrivilege)){
                nme.setText(user);
                pri.setText("Admin");
                addemployee.setVisible(true);
                removeemployee.setVisible(true);
            }
            else if (Objects.equals(privilegeStat, "owner") && Objects.equals(privilegeStat, setPrivilege)){
                nme.setText(user);
                pri.setText("Owner");
            }
            else if (Objects.equals(privilegeStat, "employee") && Objects.equals(privilegeStat, setPrivilege)){
                nme.setText(user);
                pri.setText("Employee");
                addemployee.setVisible(false);
                removeemployee.setVisible(false);
            }
            setin.toFront();
        }

    }
    @FXML
    private void popStageInventory(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("invent.fxml"));
        Parent parent = fxmlLoader.load();
        invpopupController dialogController = fxmlLoader.<invpopupController>getController();
        dialogController.setinventTableObservableList(itvObservableList);
        Stage dialogue = new Stage();
        Scene scene = new Scene(parent, 600, 300);
        dialogue.setTitle("Inventory");
        dialogue.initModality(Modality.APPLICATION_MODAL);
        dialogue.setScene(scene);
        dialogue.showAndWait();

    }

    @FXML
    private void popStageOrders(ActionEvent event) throws IOException, CsvException {

        Stage dialogue = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orders.fxml"));
        Parent parent = fxmlLoader.load();
        NopopupController dialogController = fxmlLoader.<NopopupController>getController();
        dialogController.setorderTableObservableList(otvObservableList);
        Scene scene = new Scene(parent, 600, 300);
        dialogue.setTitle("orders");
        dialogue.initModality(Modality.APPLICATION_MODAL);
        dialogue.setScene(scene);
        dialogue.showAndWait();
        reload();

    }


    @FXML
    private void newItem(ActionEvent event) throws  IOException, CsvException{
        cancel();
    }
    public void cancel() throws IOException, CsvException {
        otvObservableList.clear();
        DataAppoint dp = new DataAppoint();
        String[][] starr = dp.getinventory("orderData.csv");
        int i = 0;
        while(i < starr.length){
            dp.remove_line("orderData.csv",i);
        }
    }

    @FXML
    private void Reloadinv (ActionEvent event) throws IOException, CsvException {
        reload();
    }




    @FXML
    private void generatebill(ActionEvent event) throws IOException, CsvException {
        DataAppoint data = new DataAppoint();
        String[][] ord = data.getinventory("orderData.csv");
        String[][] inv = data.getinventory("invData.csv");
        //cancel();
        LocalDate ld = LocalDate.now();
        String date = String.valueOf(ld);
        for (int i = 0; i < inv.length; i++) {
            for (int j = 0; j < ord.length; j++) {
                if (Objects.equals(ord[j][0], inv[i][0])) {
                    int qty = Integer.parseInt(ord[j][1]);
                    int qt = Integer.parseInt(inv[i][1]);
                    int sprice = Integer.parseInt(ord[j][2]);
                    int bprice = Integer.parseInt(inv[i][2]);

                    String[] data1 = new String[]{inv[i][0], Integer.toString(qt - qty), inv[i][2], date};
                    String[] data2 = new String[]{ord[j][0], Integer.toString(qty),Integer.toString(sprice-bprice), date};
                    data.remove_line("invData.csv",i);
                    data.add_data(data1, "invData.csv");
                    data.add_data(data2,"orderHistory.csv");
                    break;
                }
            }
        }
        billtxt(110);
        cancel();
        reload();
    }
    public void billtxt(int billno){
        try {
            LocalDateTime myDateObj = LocalDateTime.now();
            System.out.println("Before formatting: " + myDateObj);
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");

            String formattedDate = myDateObj.format(myFormatObj);
            String path = "C:Users/DENSLIN/Desktop/bill/"+formattedDate+".txt";
            System.out.println(path);
            FileWriter myWriter = new FileWriter(path);

            myWriter.write("=============Bill================"+
                               "\n"+formattedDate+"\n"+
                               "\n=================================\n"+
                                "name\tQuantity\tprice\n");
            DataAppoint data = new DataAppoint();
            String[][] ord = data.getinventory("orderData.csv");
            float total = 0;
            for (int i = 0; i < ord.length; i++) {
                myWriter.write(ord[i][0]+"\t"+ord[i][1]+"\t\t"+ord[i][2]+"\t\n");
                int qty = Integer.parseInt(ord[i][1]);
                int price = Integer.parseInt(ord[i][2]);
                total += (qty*price);
            }
            myWriter.write("=================================\n");
            myWriter.write("Total\t\t"+total);
            myWriter.close();
        }
        catch (IOException | CsvException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void reload() throws IOException, CsvException {
        itvObservableList.clear();
        DataAppoint obj1 = new DataAppoint();
        String[][] starr = obj1.getinventory("invData.csv");
        int i =0;
        while(i < starr.length) {
            if ((Integer.parseInt(starr[i][1])) <= 0){
                obj1.remove_line("invData.csv",i);
                continue;
            }
            InvTable y = new InvTable(starr[i][0], Integer.parseInt(starr[i][1]), Integer.parseInt(starr[i][2]), starr[i][3]);
            itvObservableList.add(y);
            i++;
        }
    }

    @FXML
    private void addemp(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addemployee.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent, 600, 300);
        dialogue.setTitle("Add Employee");
        dialogue.initModality(Modality.APPLICATION_MODAL);
        dialogue.setScene(scene);
        dialogue.showAndWait();
    }
    @FXML
    private void rememp(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rememployee.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent, 600, 200);
        dialogue.setTitle("Add Employee");
        dialogue.initModality(Modality.APPLICATION_MODAL);
        dialogue.setScene(scene);
        dialogue.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // line chart
        try {
            DataCalc o = new DataCalc();
            XYChart.Series<String,Integer> series = o.getsalesdata();
            salesgraph.getData().add(series);
            float cost = o.invcost();
            ic.setText(String.format("%.2f",cost));
            float prft = o.getprofit();
            tp.setText(String.format("%.2f", prft));
        } catch (IOException | CsvException  e) {
            e.printStackTrace();
        }



        // inventory table settings
        itemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        availability.setCellValueFactory(new PropertyValueFactory<>("expenditure"));
        mod.setCellValueFactory(new PropertyValueFactory<>("modified"));
        inventoryTable.setItems(itvObservableList);


        //orders table settings
        Item.setCellValueFactory(new PropertyValueFactory<>("Item"));
        orQunatity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        oprice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        newOrderTable.setItems(otvObservableList);

        x.setOnMouseClicked(mouseEvent -> {
            System.exit(0);
        });
        min.setOnMouseClicked(mouseEvent -> {
            Stage obj = (Stage) min.getScene().getWindow();
            obj.setIconified(true);
        });
        rsz.setOnMouseClicked(mouseEvent -> {
            Stage obj = (Stage) min.getScene().getWindow();
            obj.setMaximized(true);
        });
        LogInButton.setOnMouseClicked(mouseEvent -> {      // id: root; pass: root
            String usrid = id.getText().trim();
            user = usrid;
            privilegeStat = privilege.getText().trim();
            login obj = new login();
            ArrayList<String[]> logdata;
            boolean b = false;
            try {
                Key k = obj.getkey();
                logdata = obj.getdata(k);
                for (int i=0;i<logdata.size();i++) {

                    if (logdata.get(i)[0].equals(usrid)) {
                        String username = logdata.get(i)[0];
                        setPrivilege = logdata.get(i)[2];
                        b = pass.getText().equals(logdata.get(i)[1]) && Objects.equals(privilegeStat, setPrivilege) && Objects.equals(username, user);

                        break;
                    }
                }

                if (b) {
                    LogIn.toBack();
                    id.clear();
                    privilege.clear();
                    pass.clear();
                    warning.setVisible(false);
                }
                else {
                    warning.setVisible(true);
                    warning.setText("Your credentials are incorrect!");
                    warning.setTextFill(Color.web("red"));
                }

            } catch (IOException | IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });
        logout.setOnMouseClicked(mouseEvent -> {
            LogIn.toFront();
            dbp.toFront();
        });
    }
}
