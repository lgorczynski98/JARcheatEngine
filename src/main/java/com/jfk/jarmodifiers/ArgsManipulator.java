package com.jfk.jarmodifiers;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.bytecode.Descriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArgsManipulator {
    protected String createFieldSrc(String[] scriptLineArr, int beginIndex){        //uzywane w przypadku zbudowania stringa z deklaracji metody lub pola -- public static void init(); || int x = 0;
        StringBuilder fieldSrc = new StringBuilder();
        for (int i = beginIndex; i < scriptLineArr.length; i++) {
            fieldSrc.append(scriptLineArr[i] + " ");
        }
        fieldSrc.deleteCharAt(fieldSrc.length() - 1);
        return fieldSrc.toString();
    }

    protected String createFieldSrc(String[] scriptLineArr, int beginIndex, int endIndex){
        StringBuilder fieldSrc = new StringBuilder();
        for (int i = beginIndex; i < endIndex; i++) {
            fieldSrc.append(scriptLineArr[i] + " ");
        }
        fieldSrc.deleteCharAt(fieldSrc.length() - 1);
        return fieldSrc.toString();
    }

    protected CtClass[] getArguments(String[] scriptLineArr){
        try {
            String line = String.join(" ", scriptLineArr);
            int startIndex = line.indexOf('(') + 1;
            int endIndex = line.indexOf(')');
            line = line.substring(startIndex, endIndex);
            String[] argsAndNames = line.split(" ");
            List<String> x = Arrays.asList(argsAndNames);
            List<String> y = new ArrayList<>();
            for (int i = 0; i < x.size(); i++) {
                if(i%2 == 0)
                    y.add(x.get(i));
            }
            if(y.get(0).equals(""))
                return null;
            ClassPool classPool = ClassPool.getDefault();
            List<CtClass> classes = new ArrayList<>();
            for (int i = 0; i < y.size(); i++) {
                try {
                    classes.add(classPool.makeClass(y.get(i)));
                }
                catch(Exception e) {
                    createPrimitiveType(y.get(i), classes);
                }
            }
            if(classes.size() == 0) return null;
            return classes.toArray(CtClass[]::new);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createPrimitiveType(String typeName, List<CtClass> classes){
        switch(typeName){
            case "boolean":{
                classes.add(CtPrimitiveType.booleanType);
                break;
            }
            case "byte":{
                classes.add(CtPrimitiveType.byteType);
                break;
            }
            case "char":{
                classes.add(CtPrimitiveType.charType);
                break;
            }
            case "double":{
                classes.add(CtPrimitiveType.doubleType);
                break;
            }
            case "float":{
                classes.add(CtPrimitiveType.floatType);
                break;
            }
            case "int":{
                classes.add(CtPrimitiveType.intType);
                break;
            }
            case "long":{
                classes.add(CtPrimitiveType.longType);
                break;
            }
            case "short":{
                classes.add(CtPrimitiveType.shortType);
                break;
            }
        }
    }
}
