package ru.mikheev.kirill.utilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class ClassInformationHolder {

    private Class<?> heldClass;

    public ClassInformationHolder(){}

    public ClassInformationHolder(Class<?> heldClass){
        this.heldClass = heldClass;
    }

    public ClassInformationHolder(String className){
        try {
            this.heldClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.out.println("Class is not found");
        }
    }

    public void setHeldClass(Class<?> heldClass) {
        this.heldClass = heldClass;
    }

    public void setHeldClass(String className) {
        try {
            this.heldClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.out.println("Class is not found");
        }
    }

    public void getAllInformation(){
        if(heldClass == null){
            return;
        }
        Field[] fields = heldClass.getFields();
        Method[] methods = heldClass.getMethods();
        Constructor[] constructors = heldClass.getConstructors();
        Pattern pattern = Pattern.compile("\\w+\\.");
        System.out.println("Fields:");

        for (Field tmp : fields){
            System.out.println(
                    "\t" +  pattern.matcher(tmp.toString()).replaceAll(""));
        }
        System.out.println("Constructors:");
        for (Constructor tmp : constructors){
            System.out.println(
                    "\t" +  pattern.matcher(tmp.toString()).replaceAll(""));
        }
        System.out.println("Methods:");
        for (Method tmp : methods){
            System.out.println(
                    "\t" + pattern.matcher(tmp.toString()).replaceAll(""));
        }
    }
}
