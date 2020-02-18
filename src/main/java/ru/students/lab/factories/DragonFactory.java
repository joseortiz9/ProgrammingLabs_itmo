package ru.students.lab.factories;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.models.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class DragonFactory {

    private IHandlerInput inputHandler;

    public DragonFactory() {
    }

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

    private Object validateDragonProp(String fType, String desc, boolean nullable, Class<?> toClass, int min) {
        Object obj = null;
        do {
            obj = validateDragonProp(fType, desc, nullable, toClass);
        } while (!checkValidNumber((toClass.equals(Long.class)) ? (Long) obj : (Double) obj, min));
        return obj;
    }

    public boolean checkValidNumber(Long num, int min) {
        return num > min;
    }
    public boolean checkValidNumber(Double num, int min) {
        return num > min;
    }

    public static Object getValueOf(Class<?> toClass, String s) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (toClass.equals(String.class) || s.equals("")) return s;
        Method method = toClass.getMethod("valueOf", String.class);
        return method.invoke(null, s);
    }

}
