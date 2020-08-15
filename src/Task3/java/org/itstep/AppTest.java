package org.itstep;

import org.itstep.Task3.Fraction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.*;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public static final String CLASS_NAME = "org.itstep.Task3.Fraction";

    public static final Class<?> FRACTION_CLAZZ;

    static {
        Class<?> clazz;
        try {
            clazz = Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            clazz = null;
        }
        FRACTION_CLAZZ = clazz;
    }

    public static void hasPrivateField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        Assertions.assertTrue(Modifier.isPrivate(field.getModifiers()));
    }

    public static void hasPublicMethods(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
        Assertions.assertTrue(Modifier.isPublic(method.getModifiers()));
    }


    @Test
    @Order(1)
    @DisplayName("Проверка существования класса Fraction")
    public void classExists()
    {
        Assertions.assertNotNull(FRACTION_CLAZZ, "Класс" + CLASS_NAME + " не найден");
    }

    @Order(2)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка наличия полей")
    @ValueSource(strings = {"numerator", "denominator"})
    public void privateFieldExist(String fieldName) throws NoSuchFieldException {
        hasPrivateField(FRACTION_CLAZZ, fieldName);
    }

    @Order(3)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка наличия геттеров")
    @ValueSource(strings = {"getNumerator", "getDenominator"})
    public void publicGettersExist(String methodName) throws NoSuchMethodException {
        hasPublicMethods(FRACTION_CLAZZ, methodName);
    }

    @Order(4)
    @ParameterizedTest(name = "{1}")
    @DisplayName("Проверка наличия сеттеров")
    @CsvSource({"int,setNumerator", "int,setDenominator"})
    public void publicSettersExist(Class<?> clazz, String methodName) throws NoSuchMethodException {
        hasPublicMethods(FRACTION_CLAZZ, methodName, clazz);
    }

    @Order(5)
    @ParameterizedTest(name = "{1}")
    @DisplayName("Проверка геттеров/сеттеров")
    @CsvSource({"int,numerator", "int,denominator"})
    public void setterGetterWork(Class<?> clazz, String fieldName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //почему конструктор?
        Object obj = FRACTION_CLAZZ.getDeclaredConstructor().newInstance();

        String setterName = String.format("set%c%s", Character.toUpperCase(fieldName.charAt(0))
        ,fieldName.substring(1));

        String getterName = String.format("get%c%s", Character.toUpperCase(fieldName.charAt(0))
                ,fieldName.substring(1));

        Method setter = FRACTION_CLAZZ.getDeclaredMethod(setterName, clazz);
        Method getter = FRACTION_CLAZZ.getDeclaredMethod(getterName);

        int expected = 1;
        setter.invoke(obj, expected);

        Object actual = getter.invoke(obj);
        Assertions.assertEquals(expected, actual);
    }

    @Order(6)
    @Test
    @DisplayName("Проверка конструкторов")
    public void constructors() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] constructors = FRACTION_CLAZZ.getDeclaredConstructors();
        Assertions.assertEquals(2, constructors.length);

        Constructor<?> testConstructor = FRACTION_CLAZZ.getDeclaredConstructor(int.class, int.class);

        Object numerator = 2;
        Object denominator = 5;

        Object obj = testConstructor.newInstance(numerator, denominator);

        Method getNumerator = FRACTION_CLAZZ.getDeclaredMethod("getNumerator");
        Assertions.assertEquals(numerator, getNumerator.invoke(obj));

        Method getDenominator = FRACTION_CLAZZ.getDeclaredMethod("getDenominator");
        Assertions.assertEquals(denominator, getDenominator.invoke(obj));
    }

    Object getFraction(Object num, Object den) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> tConstructor = FRACTION_CLAZZ.getDeclaredConstructor(int.class, int.class);
        return tConstructor.newInstance(num, den);
    }

    void mathCheck(int[] args, String methodName) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object first = getFraction(args[0], args[1]);
        Object second = getFraction(args[2], args[3]);

        Method testMethod = FRACTION_CLAZZ.getDeclaredMethod(methodName, FRACTION_CLAZZ);
        Object result = testMethod.invoke(first, second);
        Assertions.assertEquals(FRACTION_CLAZZ, result.getClass(), "Метод " + methodName
        + "должен возвращать Fraction");

        Method getNumerator = FRACTION_CLAZZ.getDeclaredMethod("getNumerator");
        Method getDenominator = FRACTION_CLAZZ.getDeclaredMethod("getDenominator");

        Assertions.assertEquals(args[4], getNumerator.invoke(result), "Не верный числитель");
        Assertions.assertEquals(args[5], getDenominator.invoke(result), "Не верный знаменатель");
    }

    @Order(7)
    @DisplayName("Проверка сложения")
    @ParameterizedTest(name = "{5}")
    @CsvSource("1,4,2,4,3,4")
    public void additional(int num1, int den1, int num2, int den2, int rnum, int rden) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        mathCheck(new int[] {num1, den1, num2, den2, rnum, rden}, "addition");
    }

    @Order(8)
    @DisplayName("Проверка вычитания")
    @ParameterizedTest(name = "{5}")
    @CsvSource("3,4,2,4,1,4")
    public void subtraction(int num1, int den1, int num2, int den2, int rnum, int rden) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        mathCheck(new int[] {num1, den1, num2, den2, rnum, rden}, "subtraction");
    }

    @Order(8)
    @DisplayName("Проверка умножения")
    @ParameterizedTest(name = "{5}")
    @CsvSource("2,3,1,3,2,9")
    public void multiplication(int num1, int den1, int num2, int den2, int rnum, int rden) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        mathCheck(new int[] {num1, den1, num2, den2, rnum, rden}, "multiplication");
    }

    @Order(9)
    @DisplayName("Проверка деления")
    @ParameterizedTest(name = "{5}")
    @CsvSource("2,3,1,3,2,1")
    public void division(int num1, int den1, int num2, int den2, int rnum, int rden) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        mathCheck(new int[] {num1, den1, num2, den2, rnum, rden}, "division");
    }
}
