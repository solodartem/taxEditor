package com.taxeditor.xml.formatting;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Pretty-prints xml, supplied as a string.
 * <p/>
 * eg.
 * <code>
 * String formattedXml = new XmlFormatter().format("<tag><nested>hello</nested></tag>");
 * </code>
 */
public class XmlFormatter {

    public static String prettyFormat(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return "<?xml version=\"1.0\" encoding=\"windows-1251\"?>" + System.getProperty("line.separator") + xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e); // simple exception handling, please review it
        }
    }

    public static String prettyFormat(String input) {
        return prettyFormat(input, 2);
    }

    static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    static void writeFile(String path, String content) throws IOException
    {
        Files.write(Paths.get(path), content.getBytes(Charset.forName("windows-1251")));
    }

    static void formatFile(String fileName) throws IOException {
        String[] formattedLines = prettyFormat(readFile(fileName, Charset.forName("windows-1251"))).split(System.getProperty("line.separator"));

        String outputContent ="";
        for (String line : formattedLines) {
            if (line.indexOf("RXXXXG4S")!=-1) {
                int idx = AddLines.getNumberFromLine(line);
                line += System.getProperty("line.separator") + "<RXXXXG105_2S ROWNUM=\""+idx+"\">2009</RXXXXG105_2S>";
            }

            outputContent+=line;
        }

        writeFile(fileName, prettyFormat(outputContent));

    }

    public static void main(String[] args) throws IOException {
        formatFile("./sources/before_fomatted.xml");
    }
}
