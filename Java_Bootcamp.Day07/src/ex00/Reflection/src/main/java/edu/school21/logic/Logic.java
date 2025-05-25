package edu.school21.logic;

import java.lang.reflect.*;
import java.util.*;


public class Logic {
    private final Map<String, Class<?>> classMap;
    private String className;
    private Object object;
    private Scanner scanner = new Scanner(System.in);
    public Logic(Class<?>... classes) {
        this.classMap = new HashMap<>();
        for (Class<?> cl : classes) {
            this.classMap.put(cl.getSimpleName(), cl);
        }
    }
    public void start() {
        printClasses();
        chooseClass();
        printFields();
        printMethods();
        try {
            createObject();
            changeFieldValue();
            callMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void printClasses() {
        System.out.println("Classes:");
        for (String className : classMap.keySet()) {
            System.out.println("\t- " + className);
        }
        System.out.println("---------------------");
    }
    private void chooseClass() {
        System.out.println("Enter class name:");
        System.out.print("-> ");
        String className = scanner.next();
        while (!classMap.containsKey(className)) {
            System.out.print("-> ");
            className = scanner.next();
        }
        this.className = className;
        System.out.println("---------------------");
    }

    private void printFields() {
        System.out.println("fields:");
        for (Field field : classMap.get(className).getDeclaredFields()) {
            System.out.println("\t\t- " + field.getName());
        }
        System.out.println("---------------------");
    }
    private void printMethods() {
        System.out.println("methods:");
        for (Method method : classMap.get(className).getDeclaredMethods()) {
            String returnType = method.getReturnType().getSimpleName();
            String name = method.getName();
            Class<?>[] parameters = method.getParameterTypes();
            StringBuilder parameterTypes = new StringBuilder();
            for (int i = 0; i < parameters.length; i++) {
                parameterTypes.append(parameters[i].getSimpleName());
                if (i < parameters.length - 1) parameterTypes.append(", ");
            }
            System.out.println("\t\t" + (!returnType.equals("void") ? returnType + " " : "") + name + "(" + parameterTypes + ")");
        }
        System.out.println("---------------------");
    }
    private void createObject() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = classMap.get(className);
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Constructor<?> constructor = getParameterConstructor(constructors);
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("Let’s create an object.");
        Object[] newParameterValues = getNewParameterValue(fields);
        object = constructor.newInstance(newParameterValues);
        System.out.println("Object created: " + object);
        System.out.println("---------------------");
    }

    private void changeFieldValue() throws NoSuchFieldException, IllegalAccessException {
        System.out.println("Enter name of the field for changing:");
        System.out.print("-> ");
        String fieldName = scanner.next();
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        System.out.println("Enter " + field.getType().getSimpleName() + " value:");
        System.out.print("-> ");
        switch (field.getType().getSimpleName()) {
            case "int", "Integer":
                field.set(object, scanner.nextInt());
                break;
            case "double", "Double":
                field.set(object, scanner.nextDouble());
                break;
            case "boolean", "Boolean":
                field.set(object, scanner.nextBoolean());
                break;
            case "long", "Long":
                field.set(object, scanner.nextLong());
                break;
            case "String":
                field.set(object, scanner.next());
                break;
            default:
                System.out.println("Error: incorrect parameter type");
        }
        System.out.println("Object updated: " + object);
        System.out.println("---------------------");
    }

    private void callMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("Enter name of the method for call:");
        System.out.print("-> ");
        String[] methodName = checkMethodName();
        if (methodName == null) throw new NoSuchMethodException();
        else if (methodName.length < 2) callMethodWithoutParameters(methodName[0]);
        else callMethodWithParameters(methodName);
    }
        private void callMethodWithoutParameters(String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = object.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        Object result = method.invoke(object);
        if (method.getReturnType().getSimpleName() != "void") {
            System.out.println("Method returned:");
            System.out.print(result);
        }
    }
    private void callMethodWithParameters(String[] methodName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class<?>[] parameterTypes = new Class[methodName.length - 1];
        for (int i = 1; i < methodName.length; i++) {
            parameterTypes[i - 1] = convertTypesNames(methodName[i]);
        }
        Method method = object.getClass().getDeclaredMethod(methodName[0], parameterTypes);
        method.setAccessible(true);
        Object result = invokeMethod(method, parameterTypes);
        if (method.getReturnType().getSimpleName() != "void") {
            System.out.println("Method returned:");
            System.out.print(result);
        }
    }
    private Object invokeMethod(Method method, Class<?>[] parameterTypes) throws InvocationTargetException, IllegalAccessException {
        Object[] arguments = new Object[parameterTypes.length];
        for (int i = 0; i < arguments.length; i++) {
            System.out.println("Enter " + parameterTypes[i] + " value:");
            System.out.print("-> ");
            switch (parameterTypes[i].getSimpleName()) {
                case "int", "Integer":
                    arguments[i] = scanner.nextInt();
                    break;
                case "double", "Double":
                    arguments[i] = scanner.nextDouble();
                    break;
                case "boolean", "Boolean":
                    arguments[i] = scanner.nextBoolean();
                    break;
                case "long", "Long":
                    arguments[i] = scanner.nextLong();
                    break;
                case "String":
                    arguments[i] = scanner.next();
                    break;
                default:
                    System.out.println("Error: incorrect parameter type");
            }
        }
        return method.invoke(object, arguments);
    }
private Class<?> convertTypesNames(String typeName) {
    switch (typeName) {
        case "int":
            return int.class;
        case "Integer":
            return Integer.class;
        case "Double":
            return Double.class;
        case "double":
            return double.class;
        case "Boolean":
            return Boolean.class;
        case "boolean":
            return boolean.class;
        case "Long":
            return Long.class;
        case "long":
            return long.class;
        case "String":
            return String.class;
        default:
            throw new IllegalArgumentException("Unknown type: " + typeName);
    }
}
    private String[] checkMethodName() {
        String methodName = scanner.next();
        methodName += scanner.nextLine();
        if (methodName == null) return null;
        methodName = methodName.replaceAll(" ", "");
        int indexOfOpenBracket = methodName.indexOf('(');
        int indexOfCloseBracket = methodName.indexOf(')');
        if (indexOfOpenBracket == -1 || indexOfCloseBracket == -1 || indexOfOpenBracket > indexOfCloseBracket) return null;

        String name = methodName.substring(0, indexOfOpenBracket);
        String[] parameterTypes = methodName.substring(indexOfOpenBracket+1, indexOfCloseBracket).split(",");
        int countParameters = 0;
        if (parameterTypes.length > 0 && !parameterTypes[0].isEmpty()) countParameters = parameterTypes.length;
        String[] result = new String[1 + countParameters];
        result[0] = name;
        for (int i = 0; i < countParameters; i++) {
            result[i + 1] = parameterTypes[i].trim();
        }
        return result;
    }

    private Constructor<?> getParameterConstructor(Constructor<?>[] constructors) {
        Set<Constructor<?>> setConstructors = new HashSet<>();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() > 0)
                setConstructors.add(constructor);
        }
        if (setConstructors.isEmpty()) throw new IllegalArgumentException("The class hasn't a parameter constructor!");
        Constructor<?> constructor = setConstructors.iterator().next();
        return constructor;
    }

    private Object[] getNewParameterValue(Field[] fields) {
        Object[] newParameterValues = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getName() + ":");
            System.out.print("-> ");
            switch (fields[i].getType().getSimpleName()) {
                case "int", "Integer":
                    newParameterValues[i] = scanner.nextInt();
                    break;
                case "double", "Double":
                    newParameterValues[i] = scanner.nextDouble();
                    break;
                case "boolean", "Boolean":
                    newParameterValues[i] = scanner.nextBoolean();
                    break;
                case "long", "Long":
                    newParameterValues[i] = scanner.nextLong();
                    break;
                case "String":
                    newParameterValues[i] = scanner.next();
                    break;
                default:
                    System.out.println("Error: incorrect parameter type");
            }
        }
        return newParameterValues;
    }
}
