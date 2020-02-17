package ru.students.lab.factories;

import ru.students.lab.models.Color;
import ru.students.lab.models.DragonCharacter;
import ru.students.lab.models.DragonHead;
import ru.students.lab.models.DragonType;

public class AttrDragonFactory {
    public static Object getObjectOfAttr(String fType, String attr){
        if(attr == null){
            return null;
        }
        else if(fType.equalsIgnoreCase("NAME")){
            return attr;

        } else if(fType.equalsIgnoreCase("AGE")) {
            return new Long(attr);

        } else if(fType.equalsIgnoreCase("Coordinate{X}")) {
            return new Long(attr);

        } else if(fType.equalsIgnoreCase("Coordinate{Y}")) {
            return new Float(attr);
        }
        else if(fType.equalsIgnoreCase("COLOR")){
            return Color.valueOf(attr);
        }
        else if(fType.equalsIgnoreCase("DRAGONCHARACTER")){
            return DragonCharacter.valueOf(attr);
        }
        else if(fType.equalsIgnoreCase("DRAGONTYPE")){
            return DragonType.valueOf(attr);
        }
        else if(fType.equalsIgnoreCase("DRAGONHEAD{NUMBEREYES}")){
            return new Double(attr);
        }

        return null;
    }
}
