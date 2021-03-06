package parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class JavaScriptForms {

    /** Advance to the specified HTML tag.
     * @param parse The HTML parse object to use.
     * @param tag The HTML tag.
     * @param count How many tags like this to find.
     * @return True if found, false otherwise.
     * @throws IOException If an exception occurs while reading.
     */
    private boolean advance(ParseHTML parse, String tag, int count)

throws IOException
    {
        int ch;
        while ((ch = parse.read()) != -1)
        {
            if (ch == 0)
            {
                if (parse.getTag().getName().equalsIgnoreCase(tag))
                {
                    count--;
                    if (count <= 0)
                        return true;
                }
            }
        }
        return false;
    }
    /**
     * This method is called once for each table row located, it
     * contains a list of all columns in that row. The method
     * provided simply prints the columns to the console.
     * @param list Columns that were found on this row.
     */
    private void processTableRow(List<String> list)
    {
        StringBuilder result = new StringBuilder();
        for (String item : list)
        {
            if (result.length() > 0)
                result.append(",");
            result.append('\"');
            result.append(item);
            result.append('\"');
        }
        System.out.println(result.toString());
    }

    /**
     * This method will download an amortization table for the
     * specified parameters.
     * @param interest The interest rate for the loan.
     * @param term The term(in months) of the loan.
     * @param principle The principle amount of the loan.
     * @throws IOException Thrown if a communication error occurs.
     */
    public void process(double interest,int term,int principle)
            throws IOException
    {
        URL url = new URL("http://www.httprecipes.com/1/9/loan.php");
        URLConnection http = url.openConnection();
        http.setDoOutput(true);
        OutputStream os = http.getOutputStream();
        FormUtility form = new FormUtility(os,null);
        form.add("interest", ""+interest);
        form.add("term", ""+term);
        form.add("principle", ""+principle);
        form.complete();
        InputStream is = http.getInputStream();
        ParseHTML parse = new ParseHTML(is);
        StringBuilder buffer = new StringBuilder();
        List<String> list = new ArrayList<String>();
        boolean capture = false;
        advance(parse,"table",3);
        int ch;
        while ((ch = parse.read()) != -1)
        {
            if (ch == 0)
            {
                HTMLTag tag = parse.getTag();
                if (tag.getName().equalsIgnoreCase("tr"))
                {
                    list.clear();
                    capture = false;
                    buffer.setLength(0);
                } else if (tag.getName().equalsIgnoreCase("/tr"))
                {
                    if (list.size() > 0)
                    {
                        processTableRow(list);
                        list.clear();
                    }
                } else if (tag.getName().equalsIgnoreCase("td"))
                {
                    if (buffer.length() > 0)
                        list.add(buffer.toString());
                    buffer.setLength(0);
                    capture = true;
                } else if (tag.getName().equalsIgnoreCase("/td"))
                {
                    list.add(buffer.toString());
                    buffer.setLength(0);
                    capture = false;
                } else if (tag.getName().equalsIgnoreCase("/table"))
                {
                    break;
                }
            } else
            {
                if (capture)
                    buffer.append((char) ch);
            }
        }
    }
    /**
     * The main method, create a new instance of the object and call
     * process.
     * @param args not used.
     */
    public static void main(String args[])
    {
        try
        {
            JavaScriptForms parse = new JavaScriptForms();
            parse.process(7.5,12,10000);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
