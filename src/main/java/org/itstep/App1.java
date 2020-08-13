package org.itstep;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App1
{

    public static void main( String[] args ) throws InvocationTargetException, IllegalAccessException {
        Object obj = new Test();
        Class<?> clazz = obj.getClass();

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method: methods) {
            if (method.isAnnotationPresent(RunIt.class)) {
                RunIt runIt = method.getAnnotation(RunIt.class);
                method.invoke(clazz, runIt.a(), runIt.b());
            }
        }
    }
}
