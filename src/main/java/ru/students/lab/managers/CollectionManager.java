package ru.students.lab.managers;

import ru.students.lab.models.Dragon;

import java.util.*;
import java.util.stream.Collectors;
/** 
 * Класс, редактирующий и сортирующий коллекцию с переменной <b>nextIDToAdd</b>
 * @autor Хосе Ортис
 * @version 1.0
*/
public class CollectionManager {
    private Integer nextIDToAdd = 1;
    private HashMap<Integer, Dragon> collection;
    private Date collectionCreationDate;
    /** 
     * Конструктор - создание нового объекта
     * @see CollectionManager#CollectionManager(HashMap<Integer, Dragon>, Date)
     */
    public CollectionManager() {
        this.collection = new HashMap<>();
        this.collectionCreationDate = new Date();
    }
    /** 
     * Конструктор - создание нового объекта c определенными значениями
     * param collection - Хэшмэп, представляющую коллекцию экземпляров класса Dragon
     * param collectionCreationDate - дата инициализации коллекции
     * param nextIDToAdd - следующий свободный ID {@link Dragon#ID}
     * @see CollectionManager#CollectionManager(HashMap<Integer, Dragon>, Date)
     */
    public CollectionManager(HashMap<Integer, Dragon> collection) {
        this.collection = collection;
        this.collectionCreationDate = new Date();
        this.nextIDToAdd = collection.size() + 1;
    }
    /**
     * Функция удаления всех элементов коллекции 
     */
    public void clear() {
        this.getCollection().clear();
    }
    /**
     * Функция сортировки коллекции по ключу
     * @return возвращает коллекцию
     */
    public List<Map.Entry<Integer, Dragon>> sortByKey()
    {
        return this.getCollection()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());
    }
        }
    /**
     * Функция сортировки коллекции
     * @return возвращает отсортированную по ID {@link Dragon#ID} коллекцию
     */
    public List<Map.Entry<Integer, Dragon>> sortById()
    {

        return this.getCollection()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(x -> x.getValue().getId()))
                .collect(Collectors.toList());
    }
    /**
     * Функция сортировки коллекции
     * @return возвращает отсортированную по имени {@link Dragon#name} коллекцию
     */
    public List<Map.Entry<Integer, Dragon>> sortByName() {

        return this.getCollection()
                .entrySet()
                .stream()
                .sorted((x, y) -> x.getValue().getName().compareTo(y.getValue().getName()))
                .collect(Collectors.toList());
    }
    /**
     * Функция сортировки коллекции
     * @return возвращает отсортированную по дате создания элемента {@link Dragon#creationDate} коллекцию
     */
    public List<Map.Entry<Integer, Dragon>> sortByCreationDate() {

        return this.getCollection()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
    }
    /**
     * Функция изменения коллекции 
     * param key - ключ для добавления экземпляра класса Dragon
     * param newDragon - экземпляр класса Dragon
     * @return возвращает коллекцию с добавлением нового элемента
     */
    public Object insert(Integer key, Dragon newDragon) {
        newDragon.setId(nextIDToAdd);
        nextIDToAdd += 1;
        return this.getCollection().putIfAbsent(key, newDragon);
    }
    /**
     * Функция изменения коллекции {@link CollectionManager#collection}
     * param id - номер обновляемого экземпляра класса Dragon
     * param newDragon - новый экземпляр класса Dragon с номером id
     * @return возвращает коллекцию с измененным элементом 
     */
    public Object update(Integer id, Dragon newDragon)
    {
        Optional<Map.Entry<Integer, Dragon>> oldDragonKey =
                this.getCollection()
                .entrySet()
                .stream()
                .filter(dragonEntry -> dragonEntry.getValue().getId().equals(id))
                .findFirst();

        newDragon.setId(nextIDToAdd);
        nextIDToAdd += 1;

        return oldDragonKey.map(integerDragonEntry ->
                this.getCollection().replace(integerDragonEntry.getKey(), newDragon)).orElse(null);
    }
    /**
     * Функция изменения коллекции - удаление элемента по ключу
     * param key - ключ для удаления экземпляра класса Dragon
     * @return возвращает измененную коллекцию 
     */

    public Object removeKey(Integer key)
    {
        return this.getCollection().remove(key);
    }

    /**
     * Функция изменения коллекции - изменение элемента коллекции в случае если ключ экземпляра меньше заданного 
     * param key - ключ для обновления экземпляра класса Dragon
     * param newDragon - экземпляр класса Dragon
     * @return возвращает измененную коллекцию 
     */
    public Object replaceIfLower(Integer key, Dragon newDragon)
    {
        if (!this.getCollection().containsKey(key))
            return null;

        //is newer
        if (newDragon.compareTo(this.getCollection().get(key)) > 0) {
            newDragon.setId(nextIDToAdd);
            nextIDToAdd += 1;
            return this.getCollection().replace(key, newDragon);
        }
        return null;
    }
    /**
     * Функция изменения коллекции - удаление элементов коллекции, ключ которых больше заданного 
     * param key - ключ коллекции
     * @return возвращает измененную коллекцию 
     */
    public void removeGreaterKey(Integer key)
    {
        this.getCollection()
                .entrySet()
                .removeIf(dragonEntry -> dragonEntry.getKey() > key);
    }
    /**
     * Функция изменения коллекции - удаление элементов коллекции , ключ которых меньше заданного 
     * param key - ключ коллекции
     * @return возвращает измененную коллекцию 
     */
    public void removeLowerKey(Integer key)
    {
        this.getCollection()
                .entrySet()
                .removeIf(dragonEntry -> key > dragonEntry.getKey());
    }
    /**
     * Функция фильтрации коллекции -  поиск элементов, с именем {@link Dragon#name}, содержащим данную подстроку
     * param name - строка для поиска экземпляров класса Dragon
     * @return возвращает измененную коллекцию 
     */

    public List<Map.Entry<Integer, Dragon>> filterContainsName(String name)
    {
        return this.getCollection()
                .entrySet()
                .stream()
                .filter(dragon -> dragon.getValue().getName().equals(name))
                .collect(Collectors.toList());
    }
    /**
     * Функция фильтрации коллекции - поиск элементов, с именем {@link Dragon#name}, начинающимся с данной подстроки
     * param name - строка для поиска экземпляров класса Dragon
     * @return возвращает измененную коллекцию 
     */

    public List<Map.Entry<Integer, Dragon>> filterStartsWithName(String name)
    {
        String regex = "^("+name+").*$";
        return this.getCollection()
                .entrySet()
                .stream()
                .filter(dragon -> dragon.getValue().getName().matches(regex))
                .collect(Collectors.toList());
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
     * @return строку с информацией о коллекции
     */ 
    @Override
    public String toString() {
        return "Type of Collection: " + this.getCollection().getClass() +
                "\nCreation Date: " + collectionCreationDate.toString() +
                "\nAmount of elements: " + this.getCollection().size();
    }

}
