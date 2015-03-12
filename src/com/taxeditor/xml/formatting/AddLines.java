package com.taxeditor.xml.formatting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Артем on 12.03.2015.
 */
public class AddLines {

    static public void readFileByLines(String fileName) throws IOException {

        System.setProperty("file.encoding", "windows-1251");

        try(BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
            for(String line; (line = br.readLine()) != null; ) {


                System.out.println(line);
            }
            // line is not visible here.
        }
    }

    static public int getNumberFromLine(String line){
        Pattern pattern = Pattern.compile("\"([0-9]+)\"+");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find())
        {
            return Integer.parseInt(matcher.group(1));
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {

        readFileByLines("./sources/before_fomatted.xml");

       // System.out.println(getNumberFromLine("<RXXXXG4S ROWNUM=\"11\">шт</RXXXXG4S>"));
    }
}
