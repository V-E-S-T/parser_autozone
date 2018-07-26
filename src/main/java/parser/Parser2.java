package parser;

import java.io.*;
import java.net.*;
import java.util.*;

public class Parser2 {

    public static void main(String[] args) {

        try{
            URL url = new URL("https://www.autozone.com/repairinfo/specifications/specificationsMain.jsp");
            URLConnection connection = url.openConnection();
            //connection.setDoOutput(true);
            //OutputStream out = connection.getOutputStream();
            //FormUtility form =
            connection.connect();
            Map<String, List<String>> headers = connection.getHeaderFields();
            headers.forEach((key, value) -> {
                value.forEach((valueVALUE) -> System.out.println(key + ": " + valueVALUE));
            });

            System.out.println("---------------------------------------");

//            String encoding = connection.getContentEncoding();
//            if (encoding == null)
//                encoding = "UTF-8";
//
//            try(Scanner in = new Scanner(connection.getInputStream(), encoding))
//            {
//                for (int n  = 1; in.hasNextLine() && n <= 10; n++)
//                {
//                    System.out.println(in.nextLine());
//                }
//                if (in.hasNextLine())
//                    System.out.println(". . .");
//            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
