package ru.students.lab.factories;

import ru.students.lab.client.ConsoleHandler;
import ru.students.lab.models.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class DragonFactory {

    private ConsoleHandler inputHandler;

    public DragonFactory() {
        inputHandler = new ConsoleHandler();
    }

    public Dragon generateDragonFromConsole() {
        Dragon newDragon = new Dragon();
        Class<?> dClass = newDragon.getClass();

        String result = "running...";
        while (!result.equals("")) {
            String attrStr = inputHandler.readDragonAttr("Name");
            result = (String) newDragon.setName(attrStr);
            System.out.println(result);
        }

        newDragon.setCoordinates(new Coordinates());
        result = "running...";
        while (!result.equals("")) {
            String attrStr = inputHandler.readDragonAttr("Coordinate{X}");
            result = (String) newDragon.getCoordinates().setX(Long.valueOf(attrStr));
            System.out.println(result);
        }

        result = "running...";
        while (!result.equals("")) {
            String attrStr = inputHandler.readDragonAttr("Coordinate{Y}");
            result = (String) newDragon.getCoordinates().setY(Float.valueOf(attrStr));
            System.out.println(result);
        }

        result = "running...";
        while (!result.equals("")) {
            String attrStr = inputHandler.readDragonAttr("Age");
            result = (String) newDragon.setAge(Long.valueOf(attrStr));
            System.out.println(result);
        }

        result = "running...";
        while (!result.equals("")) {
            Arrays.asList(Color.values()).forEach(c -> System.out.print(c.toString()+ ","));
            String attrStr = inputHandler.readDragonAttr("Color");
            result = (String) newDragon.setColor(Color.valueOf(attrStr));
            System.out.println(result);
        }

        result = "running...";
        while (!result.equals("")) {
            String attrStr = inputHandler.readDragonAttr("Dragon head [Number Eyes]");
            result = (String) newDragon.setHead(new DragonHead(Double.valueOf(attrStr)));
            System.out.println(result);
        }

        result = "running...";
        while (!result.equals("")) {
            Arrays.asList(DragonCharacter.values()).forEach(c -> System.out.print(c.toString()+ ","));
            String attrStr = inputHandler.readDragonAttr("Character");
            result = (String) newDragon.setCharacter(DragonCharacter.valueOf(attrStr));
            System.out.println(result);
        }

        result = "running...";
        while (!result.equals("")) {
            Arrays.asList(DragonType.values()).forEach(c -> System.out.print(c.toString()+ ","));
            String attrStr = inputHandler.readDragonAttr("Type");
            result = (String) newDragon.setType(DragonType.valueOf(attrStr));
            System.out.println(result);
        }

        /*Field[] attrs = dClass.getDeclaredFields();
        for (Field field: attrs) {
            try {
                Field actualfield = field;
                if (actualfield.getName().equals("id") || actualfield.getName().equals("creationDate"))
                    continue;

                //if (field.getType().equals(Coordinates.class))
                    //actualfield =

                String fType = actualfield.getName().substring(0, 1).toUpperCase() + actualfield.getName().substring(1);
                String result = "running...";

                while (!result.equals("")) {
                    Method mToRun = dClass.getMethod("set"+fType, actualfield.getType());
                    String attr = inputHandler.readDragonAttr(fType);
                    result = (String) mToRun.invoke(newDragon, attr);
                    System.out.println(result);
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }*/

        return newDragon;
    }

    /*public Object getInput(Method mToRun, Object fType, Object newDragon) throws InvocationTargetException, IllegalAccessException {
        String result = "running...";
        while (!result.equals("")) {
            String attrStr = inputHandler.readDragonAttr(fType.getClass().toString());
            result = (String) mToRun.invoke(newDragon, attr);
            System.out.println(result);
        }
        return null;
    }*/
}
