package ru.students.lab;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ru.students.lab.exceptions.NullValueException;

import java.io.*;
import java.util.HashMap;

public class FileManager {

    private XStream xmlParser;
    private BufferedInputStream bufferedInputStream;
    private File xmlDragons;

    FileManager(String dataFilePath) {

        try {
            if (dataFilePath == null || !(new File(dataFilePath).exists()))
                throw new FileNotFoundException();
            else
                this.xmlDragons = new File(dataFilePath);
        } catch (FileNotFoundException ex) {
            System.out.println("There is not such file!");
            System.exit(1);
        }

        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(this.getXmlDragons()));
        } catch (Exception e) {
            System.out.println("This is a strange error!: " + e.getMessage());
            System.exit(1);
        }

        xmlParser = new XStream(new DomDriver());
        xmlParser.alias("HashMap", java.util.HashMap.class);
    }


    public void SaveCollectionInXML(HashMap<Integer, Dragon> collection) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.getXmlDragons())))) {
            String xml = this.getXmlParser().toXML(collection);
            writer.write(xml);
        } catch (Exception e) {
            System.out.println("Can not save the data, some kind of problem happened");
            e.printStackTrace();
        }
    }


    public HashMap<Integer, Dragon> getCollectionFromFile() {
        HashMap<Integer, Dragon> collection = new HashMap<Integer, Dragon>();
        String dataStr = this.getStrFromFile();

        try {
            if (!dataStr.equals(""))
                collection = (HashMap<Integer,Dragon>) xmlParser.fromXML(dataStr);
        } catch (Exception e) {
            System.out.println("sorry, couldn't make it...");
            e.printStackTrace();
            System.exit(1);
        }

        return collection;
    }

    public String getStrFromFile() {
        String dataStr = "";
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try {
            if (this.getXmlDragons().length() == 0)
                throw new Exception("File is empty!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            if (!this.getXmlDragons().canRead() || !this.getXmlDragons().canWrite())
                throw new SecurityException("The file has read and/or write protection.");
        } catch (SecurityException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }

        try {
            int result;
            while((result = bufferedInputStream.read()) != -1)
                buf.write((byte) result);

            dataStr = buf.toString();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        return dataStr;
    }


    public XStream getXmlParser() {
        return xmlParser;
    }
    public File getXmlDragons() {
        return xmlDragons;
    }
}
