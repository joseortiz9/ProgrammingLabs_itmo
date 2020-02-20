package ru.students.lab.factories;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.models.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Класс, Creator of Dragon instances
 * @autor Хосе Ортис
 * @version 1.0
 */
public class DragonFactory {

    private IHandlerInput inputHandler;

    /**
     * Конструктор - создает пустой экземпляр класса of a Dragon factory
     * @see Dragon#Dragon()
     */
    public DragonFactory() {
    }

    /**
     *
     * Read what the user writes on, validates it and create the instance if
     * everything is successfull
     *
     * @param inputHandler manages all related with the IO
     * @return instance of a Dragon with the input entered
     */
    public Dragon generateDragonByInput(IHandlerInput inputHandler)
    {
        this.inputHandler = inputHandler;

        String name = (String) validateDragonProp("name", "", false, String.class);

        Long x = (Long) this.validateDragonProp("coordinate{X}", " [should be more than -328]", false, Long.class, -328);
        Float y = (Float) this.validateDragonProp("coordinate{Y}", "", false, Float.class);
        Coordinates coordinates = new Coordinates(x,y);

        Long age = (Long) validateDragonProp("age"," [should be more than 0]", false, Long.class, 0);
        Color dColor = (Color) validateDragonProp("color", Arrays.asList(Color.values()).toString(), false, Color.class);
        DragonType dType = (DragonType) validateDragonProp("dragonType", Arrays.asList(DragonType.values()).toString(), false, DragonType.class);
        DragonCharacter dCharacter = (DragonCharacter) validateDragonProp("dragonCharacter", Arrays.asList(DragonCharacter.values()).toString(), false, DragonCharacter.class);

        Double numEyes = (Double) this.validateDragonProp("dragonHead{NumberEyes}", "[empty or more than 0]", true, Double.class, 0);
        DragonHead dHead = new DragonHead(numEyes);

        return new Dragon(name, coordinates, age, dColor, dType, dCharacter, dHead);
    }

    /**
     *
     * Reads the clients input and validates if is nullable or the input is in a incorrect
     * format till it is successfully
     *
     * @param fType type of the input, just a String for printing
     * @param desc description of the validation rules, just for printing
     * @param nullable boolean if the value could be nullable
     * @param toClass class of the Object that we want to validate
     * @return Object that represents the validated wanted value
     */
    private Object validateDragonProp(String fType, String desc, boolean nullable, Class<?> toClass) {
        Object obj = null;
        String input = "";
        boolean errorHappened;
        do {
            errorHappened = false;
            input = inputHandler.readWithMessage("Dragon's " +fType+desc+": ");

            if (nullable && input.isEmpty())
                return null;

            try {
                obj = getValueOf(toClass, input);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                inputHandler.printLn(1, "Invalid value passed!");
                errorHappened = true;
            }
            input = input.isEmpty() ? null : input;
        } while ((!nullable && input == null) || errorHappened);
        return obj;
    }

    /**
     *
     * Validates if the input comply with the validation rules till it is correct.
     *
     * @param fType type of the input, just a String for printing
     * @param desc description of the validation rules, just for printing
     * @param nullable boolean if the value could be nullable
     * @param toClass class of the Object that we want to validate
     * @param min validation rule for numbers
     * @return Object that represents the validated wanted value
     */
    private Object validateDragonProp(String fType, String desc, boolean nullable, Class<?> toClass, int min) {
        Object obj = null;
        do {
            obj = validateDragonProp(fType, desc, nullable, toClass);
        } while (!checkValidNumber((toClass.equals(Long.class)) ? (Long) obj : (Double) obj, min));
        return obj;
    }

    /**
     *
     * check if the number comply the validation rule
     *
     * @param num Long representing the number to validate
     * @param min int validation rule
     * @return is bigger than the validation rule
     */
    public boolean checkValidNumber(Long num, int min) {
        return num > min;
    }
    /**
     *
     * check if the number comply the validation rule
     *
     * @param num DOuble representing the number to validate
     * @param min int validation rule
     * @return is bigger than the validation rule
     */
    public boolean checkValidNumber(Double num, int min) {
        return num > min;
    }

    /**
     *
     * gets the Object resulted from the valueOf method inside the passed class
     *
     * @param toClass class from which we want to get the method
     * @param s parameter of the method
     * @return the Object got after running the valueOf()
     * @throws InvocationTargetException happens when the invoke method has a error
     * @throws IllegalAccessException when the method invoked has no access to the specified class
     * @throws NoSuchMethodException when the method is not found in the passed class
     */
    public static Object getValueOf(Class<?> toClass, String s) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (toClass.equals(String.class) || s.equals("")) return s;
        Method method = toClass.getMethod("valueOf", String.class);
        return method.invoke(null, s);
    }

}
