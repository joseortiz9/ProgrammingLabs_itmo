package ru.students.lab.managers;

import ru.students.lab.models.Dragon;
import ru.students.lab.util.DragonEntrySerializable;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/** 
 * Класс, редактирующий и сортирующий коллекцию
 * @autor Хосе Ортис
 * @version 1.0
*/
public class CollectionManager {
    private HashMap<Integer, Dragon> collection;
    private final Date collectionCreationDate;
    private final Lock mainLock;

    /** 
     * Конструктор - создает объект класса CollectionManager для работы с коллекцией, создает пустую коллекцию с его датой создания
     */
    public CollectionManager() {
        this(new HashMap<>());
    }

    /** 
     * Конструктор - создает объект класса CollectionManager для работы с коллекцией, создает непустую коллекцию с его датой создания и следующим свободным номером
     * @param collection - Хэшмэп, представляющая коллекцию экземпляров класса Dragon
     */
    public CollectionManager(HashMap<Integer, Dragon> collection) {
        this.collection = collection;
        this.collectionCreationDate = new Date();
        this.mainLock = new ReentrantLock();
    }

    /**
     * Функция удаления всех элементов коллекции 
     */
    public void clear() {
        mainLock.lock();
        try {
            this.getCollection().clear();
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * Функция изменения коллекции
     * @param key - ключ, представляющий экземпляр класса Dragon внутри коллекции
     * @param newDragon - экземпляр класса Dragon
     * @return возвращает коллекцию с добавлением нового элемента
     */
    public Object insert(Integer key, Dragon newDragon) {
        mainLock.lock();
        try {
            return this.getCollection().putIfAbsent(key, newDragon);
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * Функция изменения коллекции
     * @param id - номер обновляемого экземпляра класса Dragon
     * @param newDragon - новый экземпляр класса Dragon с номером id
     * @return возвращает коллекцию с измененным элементом
     */
    public Object update(Integer id, Dragon newDragon)
    {
        mainLock.lock();
        try {
            Optional<Map.Entry<Integer, Dragon>> oldDragonKey =
                    this.getCollection()
                            .entrySet()
                            .stream()
                            .filter(dragonEntry -> dragonEntry.getValue().getId().equals(id))
                            .findFirst();

            if (oldDragonKey.isPresent())
                newDragon.setId(id);

            return oldDragonKey.map(integerDragonEntry ->
                    this.getCollection().replace(integerDragonEntry.getKey(), newDragon)).orElse(null);
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * Функция изменения коллекции - удаление элемента по ключу
     * @param key - ключ, представляющий экземпляр класса Dragon внутри коллекции
     * @return возвращает измененную коллекцию
     */
    public Object removeKey(Integer key) {
        mainLock.lock();
        try {
            return this.getCollection().remove(key);
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * в случае если ключ экземпляра меньше заданного (Using creation_date)
     * @param key - ключ, представляющий экземпляр класса Dragon внутри коллекции
     * @param newDragon - экземпляр класса Dragon
     * @return value of the ID to replace if is found
     */
    public int isLowerAndGetID(Integer key, Dragon newDragon) {
        if (!this.getCollection().containsKey(key))
            return -1;

        return (newDragon.compareTo(this.getCollection().get(key)) > 0)
                ? this.getCollection().get(key).getId()
                : -1;
    }

    /**
     * Функция изменения коллекции - изменение элемента коллекции
     * @param key - ключ, представляющий экземпляр класса Dragon внутри коллекции
     * @param newDragon - экземпляр класса Dragon
     */
    public void replaceIfLower(Integer key, Dragon newDragon) {
        mainLock.lock();
        try {
            newDragon.setId(getCollection().get(key).getId());
            this.getCollection().replace(key, newDragon);
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * Функция изменения коллекции - удаление элементов коллекции, ключ которых больше заданного
     * @param keys - ключи, представляющий экземпляр класса Dragon внутри коллекции
     */
    public void removeOnKey(int[] keys)
    {
        mainLock.lock();
        try {
            for (int key: keys)
                this.getCollection().remove(key);
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * Функция сортировки коллекции по ключу
     * @return возвращает коллекцию
     */
    public List<DragonEntrySerializable> sortByKey()
    {
        return this.getCollection()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new DragonEntrySerializable(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Функция сортировки коллекции
     * @return возвращает отсортированную по ID коллекцию
     */
    public List<DragonEntrySerializable> sortById()
    {
        return this.getCollection()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(x -> x.getValue().getId()))
                .map(e -> new DragonEntrySerializable(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Функция сортировки коллекции
     * @return возвращает отсортированную по имени коллекцию
     */
    public List<DragonEntrySerializable> sortByName() {

        return this.getCollection()
                .entrySet()
                .stream()
                .sorted((x, y) -> x.getValue().getName().compareTo(y.getValue().getName()))
                .map(e -> new DragonEntrySerializable(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Функция сортировки коллекции
     * @return возвращает отсортированную по дате создания элемента коллекцию
     */
    public List<DragonEntrySerializable> sortByCreationDate() {

        return this.getCollection()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(e -> new DragonEntrySerializable(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
    * Функция фильтрации коллекции -  поиск элементов, с именем, содержащим данную подстроку
    * @param name - строка для поиска экземпляров класса Dragon по имени
    * @return возвращает измененную коллекцию
    */
    public List<DragonEntrySerializable> filterContainsName(String name)
    {
        return this.getCollection()
                .entrySet()
                .stream()
                .filter(dragon -> dragon.getValue().getName().contains(name))
                .map(e -> new DragonEntrySerializable(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Функция фильтрации коллекции - поиск элементов, с именем, начинающимся с данной подстроки
     * @param name - строка для поиска экземпляров класса Dragon по имени
     * @return возвращает измененную коллекцию 
     */
    public List<DragonEntrySerializable> filterStartsWithName(String name)
    {
        String regex = "^("+name+").*$";
        return this.getCollection()
                .entrySet()
                .stream()
                .filter(dragon -> dragon.getValue().getName().matches(regex))
                .map(e -> new DragonEntrySerializable(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public List<DragonEntrySerializable> getSerializableList() {
        return this.getCollection()
                .entrySet()
                .stream()
                .map(e -> new DragonEntrySerializable(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public void setCollection(HashMap<Integer, Dragon> collection) {
        this.collection = collection;
    }

    /**
     * Функция получения коллекции
     * @return возвращает коллекцию
     */ 
    public HashMap<Integer, Dragon> getCollection() {
        return this.collection;
    }

    /**
     * Функция получения значения поля 
     * @return возвращает дату инициализации коллекции
     */ 
    public Date getColCreationDate() {
        return collectionCreationDate;
    }

    /**
     * Функция получения значения хэшкода экземпляров класса 
     * @return возвращает хэшкод 
     */ 
    @Override
    public int hashCode() {
        int result = 25;
        result += this.getColCreationDate().hashCode();
        result >>= 4;
        result += (this.getCollection().hashCode());
        return result;
    }

    /**
     * Функция сравнения экземпляров класса 
     * @return возвращает ЛОЖЬ, если экземпляры не равны, и ПРАВДА, если экземпляры равны
     */ 
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CollectionManager)) return false;
        if (obj == this) return true;
        CollectionManager objCManager = (CollectionManager) obj;
        return this.getCollection().equals(objCManager.getCollection()) &&
                this.getColCreationDate().equals(objCManager.getColCreationDate());
    }

    /**
     * Функция получения информации о коллекции
     * @return возвращает строку с информацией о коллекции
     */ 
    @Override
    public String toString() {
        return "Type of Collection: " + this.getCollection().getClass() +
                "\nCreation Date: " + collectionCreationDate.toString() +
                "\nAmount of elements: " + this.getCollection().size();
    }

}
