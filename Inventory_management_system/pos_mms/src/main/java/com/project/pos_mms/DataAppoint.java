package com.project.pos_mms;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class DataAppoint {
    public String[][] getinventory(String filename) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(filename));
        List<String[]> list = reader.readAll();
        Iterator<String[]> it = list.iterator();
        String[][] req_data = new String[list.size()][4];
        int i = 0;
        while(it.hasNext()) {
            String[] str = (String[]) it.next();
            req_data[i] = str;
            i++;

            }
        return req_data;
    }
    public String[][] getorders(String filename) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(filename));
        List<String[]> list = reader.readAll();
        Iterator<String[]> it = list.iterator();
        String[][] req_data = new String[list.size()][3];
        int i = 0;
        while(it.hasNext()) {
            String[] str = (String[]) it.next();
            req_data[i] = str;
            i++;

        }
        return req_data;
    }
    public void remove_line(String filelocation,int rowNumber) throws IOException, CsvException {
        CSVReader reader2 = new CSVReader(new FileReader(filelocation));
        List<String[]> allElements = reader2.readAll();
        allElements.remove(rowNumber);
        FileWriter sw = new FileWriter(filelocation);
        CSVWriter writer = new CSVWriter(sw);
        writer.writeAll(allElements);
        writer.close();
    }
    public void add_data(String [] values, String filename) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(filename, true));
        writer.writeNext(values, false);
        writer.close();

    }

    // for testing
//    public static void main(String[] args) throws IOException, CsvException {
//        DataAppoint obj = new DataAppoint();
//        String[] newLine = { "abc","def","ghi","jkl"};
//        obj.add_data(newLine, "orderData.csv");
//        String[][] a = obj.getData("orderData.csv");
//        System.out.println(Arrays.deepToString(a));
//    }
}
