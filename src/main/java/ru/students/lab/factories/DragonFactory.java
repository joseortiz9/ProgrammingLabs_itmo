package ru.students.lab.factories;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.models.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class DragonFactory {

    private IHandlerInput inputHandler;

    public DragonFactory(IHandlerInput inputHandler) {
        this.inputHandler = inputHandler;
    }

    public Dragon generateDragonFromConsole() {
        Dragon newDragon = new Dragon();
        try {
            Class<?> dClass = newDragon.getClass();

            Method mToRun = dClass.getMethod("setName", String.class);
            this.saveAttr(mToRun, newDragon, "name");

            newDragon.setCoordinates(new Coordinates());
            Class<?> cClass = newDragon.getCoordinates().getClass();
            mToRun = cClass.getMethod("setX", Long.class);
            this.saveAttr(mToRun, newDragon.getCoordinates(), "coordinate{X}");
            mToRun = cClass.getMethod("setY", Float.class);
            this.saveAttr(mToRun, newDragon.getCoordinates(), "coordinate{Y}");

            mToRun = dClass.getMethod("setAge", Long.class);
            this.saveAttr(mToRun, newDragon, "age");

            mToRun = dClass.getMethod("setColor", Color.class);
            this.saveAttr(mToRun, newDragon, "color");

            mToRun = dClass.getMethod("setCharacter", DragonCharacter.class);
            this.saveAttr(mToRun, newDragon, "DragonCharacter");

            mToRun = dClass.getMethod("setType", DragonType.class);
            this.saveAttr(mToRun, newDragon, "DragonType");

            newDragon.setHead(new DragonHead());
            Class<?> hClass = newDragon.getHead().getClass();
            mToRun = hClass.getMethod("setEyesCount", Double.class);
            this.saveAttr(mToRun, newDragon.getHead(), "DragonHead{NumberEyes}");

        } catch (NoSuchMethodException ex) {
            System.out.println(ex.getMessage());
        }

        return newDragon;
    }

    private void saveAttr(Method mToRun, Object obj, String fType) {
        String result = "running...";
        while (!result.equals("")) {
            try {
                if (mToRun.getParameterTypes()[0].isEnum()) {
                    Arrays.asList(getValues((Class<? extends Enum>) mToRun.getParameterTypes()[0]))
                            .forEach(c -> System.out.print(c.toString()+ ","));
                    System.out.println();
                }
                String attrStr = "";//inputHandler.readDragonAttr(fType);
                try {
                    Object objFromInput = AttrDragonFactory.getObjectOfAttr(fType, attrStr);
                    result = (String) mToRun.invoke(obj, objFromInput);
                } catch (IllegalArgumentException ex) {
                    result = ex.getMessage();
                }
                System.out.println(result);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static Object[] getValues(Class<? extends Enum> enu) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = enu.getMethod("values");
        return (Object[]) method.invoke(null);
    }
}
