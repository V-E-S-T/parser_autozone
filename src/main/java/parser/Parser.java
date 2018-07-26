package parser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Parser {

    private String adr= "https://www.autozone.com/";
    private URL url = null;
    private HttpsURLConnection conn = null;

    private BufferedReader br = null;
    private String str;

    public void getPageCode(){
        try {
            url = new URL(adr); //создаем URL
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try{
            conn = (HttpsURLConnection)url.openConnection(); //открываем соединение
            System.out.println(conn.getResponseCode());
            br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream())
            ); // используем объект класса BufferedReader для работы со строками
        }
     catch (IOException e) {
        e.printStackTrace();
        System.out.println();
    }

        try {
            while((str = br.readLine()) != null){ // пока не достигнут конец, считываем страницу построчно
                System.out.println(str);
            }
            br.close(); //закрываем поток
            conn.disconnect();//закрываем соединение

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("жопа2");
        }
    }

    public static void main(String[] args) {

        Parser parser = new Parser();
        parser.getPageCode();
    }
}
