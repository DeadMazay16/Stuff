package ru.mikheev.kirill.gen;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableGenerator<T> {

    private Class<T> dataType;

    public TableGenerator(Class<T> dataType) {
        this.dataType = dataType;
    }

    public String generate(List<T> data) {
        StringBuilder result = new StringBuilder();
        buildHeader(result);
        try {
            buildBody(result, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private void buildHeader(StringBuilder result) {
        for(var field : dataType.getDeclaredFields()) {
            if(field.isAnnotationPresent(HTMLColumn.class)) {
                result.append(field.getAnnotation(HTMLColumn.class).name());
                result.append(" | ");
            }
        }
        result.setLength(result.length() - 3);
        result.append("\n");
    }

    private void buildBody(StringBuilder result, List<T> data) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Map<Field, Method> fieldsToProcess = new HashMap<>();
        for(var field : dataType.getDeclaredFields()) {
            if(field.isAnnotationPresent(HTMLColumn.class)) {

                fieldsToProcess.put(field, findConverterMethod(field));
            }
        }
        for(var element : data) {
            for(var fieldConverterPair : fieldsToProcess.entrySet()) {
                fieldConverterPair.getKey().setAccessible(true);
                var converter = fieldConverterPair.getValue().getDeclaringClass().newInstance();
                result.append(fieldConverterPair.getValue().invoke(converter, fieldConverterPair.getKey().get(element)));
                result.append(" | ");
            }
            result.setLength(result.length() - 3);
            result.append("\n");
        }
    }

    private Method findConverterMethod(Field field) {
        Method result = null;
        for(var method : field.getAnnotation(HTMLColumn.class).converter().getMethods()) {
            if (method.getReturnType().equals(String.class) &&
                    method.getParameterCount() == 1 &&
                    method.getParameterTypes()[0].equals(field.getType())) {
                if(result == null)
                    result = method;
                else
                    throw new RuntimeException("ТЕСТ");
            }
        }
        return result;
    }
}
