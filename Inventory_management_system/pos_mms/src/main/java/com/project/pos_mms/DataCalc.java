package com.project.pos_mms;


import com.opencsv.exceptions.CsvException;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class DataCalc {

    private final DataAppoint obj = new DataAppoint();
    private final String[][] reqdata = obj.getorders("orderHistory.csv");

    public DataCalc() throws IOException, CsvException {
    }

    public float getprofit() throws IOException, CsvException {

        DataAppoint data = new DataAppoint();
        String[][] his = data.getinventory("orderHistory.csv");
        int i = 0;
        float prft = 0;
        while (i<his.length){
            LocalDate ld = LocalDate.now();
            /*if(his[i][3]== Integer.toString(ld)){
                int qty = Integer.parseInt(his[i][1]);
                int pft = Integer.parseInt((his[i][2]));
                prft += (qty * pft);*/
                System.out.println(ld);
            //}
            i++;
        }

        return prft;
    }
    public void getNoSales(){

    }
    public float invcost() throws IOException, CsvException {
        DataAppoint data = new DataAppoint();
        String[][] inv = data.getinventory("invData.csv");
        int i = 0;
        float cost = 0;
        while(i<inv.length){
            int qty = Integer.parseInt(inv[i][1]);
            int prs = Integer.parseInt((inv[i][2]));
            cost += (qty * prs);
            i++;
        }
        return  cost;


    }
    public void avgsales(){

    }
    public void totalsale(){

    }
    public void orderperday(){

    }
    public XYChart.Series<String, Integer> getsalesdata() throws IOException, CsvException {
        XYChart.Series<String, Integer> ser = new XYChart.Series<String, Integer>();
        ArrayList<String> s = new ArrayList<>();
        for (int i= 0; i< reqdata.length; i++) {
            s.add(reqdata[i][3]);
        }
        HashSet<String> hset = new HashSet<String>(s);
        String[] req_string =  hset.toArray(new String[0]);
        System.out.println(Arrays.deepToString(req_string));

        for(int dvc = 0;dvc<req_string.length;dvc++) {
            int sum = 0;
            for (int i = 0; i < reqdata.length; i++) {
                boolean result = Arrays.asList(reqdata[i]).contains(req_string[dvc]);
                if (result) {
                    sum += Integer.parseInt(reqdata[i][1]);
                }
            }
            ser.getData().add(new XYChart.Data<String,Integer>(req_string[dvc], sum));
        }
        ser.setName("Sales");
        return ser;
    }

    public static void main(String[] args) throws IOException, CsvException {
        DataCalc m = new DataCalc();
        //XYChart.Series<String, Integer> a = m.getsalesdata();
        float cost = m.getprofit();
        System.out.println(cost);
    }
}

/*
- add attributes in the details section
- give functionality to graph
- give functionality to login page
- give functionality to remove and edit buttons

encrypted file will have login passwords of the users.


when generate bill is clicked clear the table and subtract the data from inventory.

profit: total orderprice - inventory expendature

average sale: sum of sale per day for 30 days / 30

inventory cost: total expendature on inventory or total worth of items in inventory
total sale: sum of all the sale done till now

successful orders: increment to value  */