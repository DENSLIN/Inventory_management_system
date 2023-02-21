package com.project.pos_mms;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class login {

    public Key getkey() throws IOException {
        FileInputStream keyFis = new FileInputStream("key.txt");
        byte[] encKey = new byte[keyFis.available()];
        keyFis.read(encKey);
        keyFis.close();
        return new SecretKeySpec(encKey, "AES");
    }
    public static void clearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter("log.txt", false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }
    public ArrayList<String[]> getdata(Key key) throws IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        FileInputStream encryptedTextFis = new FileInputStream("log.txt");
        byte[] encText = new byte[encryptedTextFis.available()];
        encryptedTextFis.read(encText);
        encryptedTextFis.close();
        Cipher decrypter = Cipher.getInstance("AES");
        decrypter.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedText = decrypter.doFinal(encText);
        String data = new String(decryptedText);
        String[] ofdata = data.split("\n");
        ArrayList<String> clndata = new ArrayList<String>();
        for (String ofdatum : ofdata) {
            clndata.add(ofdatum.replaceAll("[^\\x20-\\x7e]", ""));
        }
        ArrayList<String[]> reqdata = new ArrayList<>();
        for (String s: clndata){
            String[] t = s.split(",");
            for (int i = 0; i < t.length; i++) {
                String r = t[i];
                t[i] = r.trim();
            }
            reqdata.add(t);
        }
        return reqdata;
    }
    public void addData(Key key, String datatoadd) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] s = datatoadd.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(s);
        Base64.getEncoder().encodeToString(s);
        try{
            FileOutputStream fs=new FileOutputStream("log.txt", true);
            fs.write(cipherText);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void removeData(String usrname) throws IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Key k = getkey();
        ArrayList<String[]> s = getdata(k);
        for (int i=0;i<s.size();i++) {

            if (s.get(i)[0].equals(usrname)) {
                s.remove(i);
                break;
            }
        }
        clearTheFile();
        for (String[] strings : s) {
            String dataToAdd = String.format("%s, %s, %s\n", strings[0], strings[1], strings[2]);
            addData(k, dataToAdd);
        }

    }

    public static void main(String[] args) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String a = "test1, test1, admin\n";
        login obj = new login();
        Key akey = obj.getkey();
//        obj.addData(akey, a);
        ArrayList<String[]> word = obj.getdata(akey);
        for (String[] ofdatum : word) {
            for (String s: ofdatum){
                System.out.println(s);
            }
        }
        System.out.println(Arrays.toString(word.get(1)));


    }
}
