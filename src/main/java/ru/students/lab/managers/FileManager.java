package ru.students.lab.managers;

import com.sun.org.apache.bcel.internal.generic.ATHROW;
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

    public FileManager(String dataFilePath) throws FileNotFoundException {
        if (dataFilePath == null || !(new File(dataFilePath).exists()))
            throw new FileNotFoundException("There is not such file!");
        else
            this.xmlDragons = new File(dataFilePath);

        xmlParser = new XStream(new DomDriver());
        xmlParser.alias("root", java.util.Map.class);
        xmlParser.alias("dragon", Dragon.class);
    }


    public void SaveCollectionInXML(HashMap<Integer, Dragon> collection) throws XStreamException, IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.getXmlDragons())))) {
            String xml = this.getXmlParser().toXML(collection);
            writer.write(xml);
        }
    }


    public HashMap<Integer, Dragon> getCollectionFromFile() throws XStreamException, IOException {
        HashMap<Integer, Dragon> collection = new HashMap<Integer, Dragon>();
        String dataStr = this.getStrFromFile("");

        if (!dataStr.equals(""))
            collection = (HashMap<Integer,Dragon>) xmlParser.fromXML(dataStr);

        return collection;
    }

    public String getStrFromFile(String filePath) throws IOException {
        File fileToRetrieve;
        if (filePath.equals(""))
            fileToRetrieve = this.getXmlDragons();
        else
            fileToRetrieve = new File(filePath);

        if (!fileToRetrieve.exists())
            throw new FileNotFoundException("There is not such file!");
        else if (fileToRetrieve.length() == 0)
            throw new EmptyFileException("File is empty!");

        if (!fileToRetrieve.canRead() || !fileToRetrieve.canWrite())
            throw new SecurityException();

        String dataStr = "";
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileToRetrieve));
             ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
            int result;
            while((result = bufferedInputStream.read()) != -1)
                buf.write((byte) result);

            dataStr = buf.toString();
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
