package ru.students.lab.managers;

import ru.students.lab.exceptions.EmptyFileException;
import ru.students.lab.models.Dragon;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.HashMap;

 /** 
 * Класс для работы с файлами
 * @autor Хосе Ортис
 * @version 1.0
*/
public class FileManager {

    private JAXBContext xmlContext;
    private Marshaller jaxbMarshaller;
    private Unmarshaller jaxbUnmarshaller;
    private File xmlDragons;

    /** 
     * Конструктор - создает объект класса FileManager
     * @param dataFilePath - строка, содержащая путь до файла с данными
     * @throws FileNotFoundException В случае если файл нельзя найти.
     */
    public FileManager(String dataFilePath) throws FileNotFoundException, JAXBException {
        if (dataFilePath == null || !(new File(dataFilePath).exists()))
            throw new FileNotFoundException("There is not such file!");
        else
            this.xmlDragons = new File(dataFilePath);

        xmlContext = JAXBContext.newInstance(CollectionMapper.class);
        jaxbMarshaller = xmlContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbUnmarshaller = xmlContext.createUnmarshaller();
    }

    /**
     * Функция сохранения коллекции в формате xml в файл
     * @param collection - Хэшмэп, содержащий коллекцию экземпляров класса Dragon
     * @throws IOException
     * @throws JAXBException
     */
    public void SaveCollectionInXML(HashMap<Integer, Dragon> collection) throws IOException, JAXBException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.getXmlDragons())))) {
            CollectionMapper dragonsMap = new CollectionMapper();
            dragonsMap.setCollection(collection);
            jaxbMarshaller.marshal(dragonsMap, writer);
        }
    }

    /**
     * Функция получения коллекции из файла
     * @return - возвращает collection - Хэшмэп, содержащий коллекцию экземпляров класса Dragon
     */
    public HashMap<Integer, Dragon> getCollectionFromFile() throws IOException, JAXBException {
        HashMap<Integer, Dragon> collection = new HashMap<Integer, Dragon>();
        String dataStr = this.getStrFromFile("");

        if (!dataStr.equals("")) {
            StringReader reader = new StringReader(dataStr);
            collection = ((CollectionMapper) jaxbUnmarshaller.unmarshal(reader)).getCollection();
        }
        return collection;
    }

    /**
     * Функция получения строки из файла
     * @param filePath - путь к файлу с данными
     * @return - возвращает dataStr - строку с данными
     */
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


    public JAXBContext getJABXmlContext() {
        return xmlContext;
    }
    public File getXmlDragons() {
        return xmlDragons;
    }

     @XmlRootElement(name="dragons_map")
     @XmlAccessorType(XmlAccessType.FIELD)
     static class CollectionMapper {
         private HashMap<Integer, Dragon> dragons = new HashMap<>();

         public HashMap<Integer, Dragon> getCollection() {
             return dragons;
         }

         public void setCollection(HashMap<Integer, Dragon> collection) {
             this.dragons = collection;
         }
     }
}


