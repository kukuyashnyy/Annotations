package org.itstep.Task2;


import java.lang.reflect.Field;

public class App2 {
    public static void main(String[] args) {
        Service service = new Service("My class service");

        Class<?> clazz = service.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            if(field.isAnnotationPresent(Value.class)) {
                Value value = field.getAnnotation(Value.class);
                service.setProperties(value.value());
            }
        }

        System.out.println(service.toString());
    }
}
