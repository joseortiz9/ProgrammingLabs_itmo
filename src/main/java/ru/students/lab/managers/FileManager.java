package ru.students.lab.managers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ru.students.lab.exceptions.EmptyFileException;
import ru.students.lab.models.Dragon;

import java.io.*;
import java.util.HashMap;

public class FileManager {

    private XStream xmlParser;
    private File xmlDragons;

    public FileManager(String dataFilePath) {
        try {
            if (dataFilePath == null || !(new File(dataFilePath).exists()))
                throw new FileNotFoundException();
            else
                this.xmlDragons = new File(dataFilePath);
        } catch (FileNotFoundException ex) {
            System.out.println("There is not such file!");
            System.exit(1);
        }

        xmlParser = new XStream(new DomDriver());
        xmlParser.alias("root", java.util.Map.class);
        xmlParser.alias("dragon", Dragon.class);
    }


    public void SaveCollectionInXML(HashMap<Integer, Dragon> collection) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.getXmlDragons())))) {
            String xml = this.getXmlParser().toXML(collection);
            writer.write(xml);
        } catch (XStreamException | IOException e) {
            System.out.println("Can not save the data, some kind of problem happened");
            System.out.println(e.getMessage());
        }
    }


    public Dragon getDragonFromStr(String dragonStr) {
        return (Dragon) this.xmlParser.fromXML(dragonStr);
    }


    public HashMap<Integer, Dragon> getCollectionFromFile() {
        HashMap<Integer, Dragon> collection = new HashMap<Integer, Dragon>();
        String dataStr = this.getStrFromFile("");

        try {
            if (!dataStr.equals(""))
                collection = (HashMap<Integer,Dragon>) xmlParser.fromXML(dataStr);
        } catch (XStreamException e) {
            System.out.println("sorry, couldn't make it...");
            e.printStackTrace();
            System.exit(1);
        }

        return collection;
    }

    public String getStrFromFile(String filePath) {
        File fileToRetrieve;
        try {
            if (filePath.equals(""))
                fileToRetrieve = this.getXmlDragons();
            else
                fileToRetrieve = new File(filePath);

            if (!fileToRetrieve.exists())
                throw new FileNotFoundException("There is not such file!");
            else if (fileToRetrieve.length() == 0)
                throw new EmptyFileException("File is empty!");
        } catch (FileNotFoundException | EmptyFileException ex) {
            System.out.println(ex.getMessage());
            return "";
        }

        try {
            if (!fileToRetrieve.canRead() || !fileToRetrieve.canWrite())
                throw new SecurityException("The file has read and/or write protection.");
        } catch (SecurityException ex) {
            System.out.println(ex.getMessage());
            return "";
        }

        String dataStr = "";
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileToRetrieve));
             ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
            int result;
            while((result = bufferedInputStream.read()) != -1)
                buf.write((byte) result);

            dataStr = buf.toString();
        }catch(IOException e){
            System.out.println(e.getMessage());;
            return "";
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
