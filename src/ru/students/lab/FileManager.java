package ru.students.lab;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.util.HashMap;

public class FileManager {

    XStream xmlParser;
    BufferedInputStream bufferedInputStream;
    FileOutputStream fileOutputStream;

    FileManager(String dataFilePath) throws IOException {
        try {
            if (dataFilePath == null || !(new File(dataFilePath).exists()))
                throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("THere is not such file!");
            System.exit(1);
        }

        try {
            fileOutputStream = new FileOutputStream(dataFilePath);
            bufferedInputStream = new BufferedInputStream(new FileInputStream(dataFilePath));
        } catch (Exception e) {
            System.out.println("This is a strange error!: " + e.getMessage());
        }

        xmlParser = new XStream(new DomDriver());
        xmlParser.alias("HashMap", java.util.HashMap.class);
    }


    public void SaveCollectionInXML(HashMap<Integer, Dragon> collection) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            String xml = this.getXmlParser().toXML(collection);
            writer.write(xml);
        } catch (Exception e) {
            System.out.println("Can not save the data, some kind of problem happened");
            e.printStackTrace();
        }
    }


    public HashMap<Integer, Dragon> getCollectionFromXML() throws IOException {
        HashMap<Integer, Dragon> collection = new HashMap<>();
        String dataStr = "";
        ByteArrayOutputStream buf = new ByteArrayOutputStream();

        try {
            int result = bufferedInputStream.read();
            while(result != -1) {
                buf.write((byte) result);
                result = bufferedInputStream.read();
            }
            dataStr = buf.toString();

            System.out.println(dataStr);

            collection = (HashMap<Integer,Dragon>) xmlParser.fromXML(dataStr);
        } catch (Exception e) {
            System.out.println("sorry couldn't make it...");
            e.printStackTrace();
            System.exit(1);
        }

        return collection;
    }


    public XStream getXmlParser() {
        return xmlParser;
    }
}
